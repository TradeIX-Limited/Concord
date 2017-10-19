package com.tradeix.concord.flows

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.contracts.TradeAssetContract
import com.tradeix.concord.states.TradeAssetState
import net.corda.core.contracts.Command
import net.corda.core.contracts.StateAndRef
import net.corda.core.contracts.requireThat
import net.corda.core.flows.*
import net.corda.core.identity.Party
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker

object TradeAssetCancellation {
    enum class AssetType(val desc: String) { PO("Purchase Order"), INVOICE("Invoice") }

    @InitiatingFlow
    @StartableByRPC
    class InitiatorFlow(private val inputState: StateAndRef<TradeAssetState>) : FlowLogic<SignedTransaction>() {

        companion object {
            object GENERATING_TRANSACTION : ProgressTracker.Step("Generating transaction based on the given trade asset.")
            object VERIFYING_TRANSACTION : ProgressTracker.Step("Verifying contracts constraints.")
            object SIGNING_TRANSACTION : ProgressTracker.Step("Signing transaction with our private key.")
            object GATHERING_SIGNATURES : ProgressTracker.Step("Gathering the signature from the concerned parties.") {
                override fun childProgressTracker() = CollectSignaturesFlow.tracker()
            }

            object FINALISING_TRANSACTION : ProgressTracker.Step("Obtaining notary signature and recording transaction.") {
                override fun childProgressTracker() = FinalityFlow.tracker()
            }

            fun tracker() = ProgressTracker(
                    GENERATING_TRANSACTION,
                    VERIFYING_TRANSACTION,
                    SIGNING_TRANSACTION,
                    GATHERING_SIGNATURES,
                    FINALISING_TRANSACTION
            )
        }

        override val progressTracker = tracker()



        @Suspendable
        override fun call(): SignedTransaction {
            val notary = serviceHub
                    .networkMapCache
                    .notaryIdentities[0]

            // Stage 1 - Create unsigned transaction
            progressTracker.currentStep = GENERATING_TRANSACTION


            val command = Command(
                    value = TradeAssetContract.Commands.Cancel(),
                    signers = inputState.state.data.participants.map { it.owningKey })

            val transactionBuilder = TransactionBuilder(notary)
                    .addInputState(inputState)
                    .addCommand(command)

            // Stage 2 - Verify transaction
            progressTracker.currentStep = VERIFYING_TRANSACTION
            transactionBuilder.verify(serviceHub)
            verifyCancellationRules()

            // Stage 3 - Sign the transaction
            progressTracker.currentStep = SIGNING_TRANSACTION
            val partiallySignedTransaction = serviceHub.signInitialTransaction(transactionBuilder)

            // Stage 4 - Gather counterparty signatures
            progressTracker.currentStep = GATHERING_SIGNATURES
            val requiredSignatureFlowSessions =
                    inputState.state.data.participants
                            .filter { !serviceHub.myInfo.legalIdentities.contains(it) }
                            .distinct()
                            .map { initiateFlow(it as Party) }

            val fullySignedTransaction = subFlow(CollectSignaturesFlow(
                    partiallySignedTransaction,
                    requiredSignatureFlowSessions,
                    GATHERING_SIGNATURES.childProgressTracker()))

            // Stage 5 - Finalize transaction
            progressTracker.currentStep = FINALISING_TRANSACTION
            return subFlow(FinalityFlow(
                    fullySignedTransaction,
                    FINALISING_TRANSACTION.childProgressTracker()))
        }

        fun verifyCancellationRules() : Unit {
            val tradeAssetState = inputState.state.data
            val assetStatus = tradeAssetState.tradeAsset.status
            val resultForInitiatorsRule = permissibleInitiatorsRule(tradeAssetState, assetStatus)
            if (resultForInitiatorsRule.isNotEmpty())
                throw FlowException(resultForInitiatorsRule)
        }

        private fun permissibleInitiatorsRule(input: TradeAssetState, assetStatus: String) : String   =
            if (ourIdentity == input.buyer)
                if ( assetStatus != AssetType.PO.desc) ValidationMessages.BUYER_CANT_PO else ValidationMessages.PASSED
            else if (ourIdentity == input.supplier)
                if ( assetStatus != AssetType.INVOICE.desc) ValidationMessages.SUPPLIER_CANT_INV else ValidationMessages.PASSED
            else if (ourIdentity == input.conductor)
                if ( (!listOf(AssetType.PO.desc, AssetType.INVOICE.desc).contains(assetStatus)))
                    ValidationMessages.CONDUCTOR_ONLY_PO_IV else ValidationMessages.PASSED
            else
                ValidationMessages.PASSED

        object ValidationMessages {
            val BUYER_CANT_PO = "Buyer cannot cancel when it's a purchase order"
            val SUPPLIER_CANT_INV = "Supplier cannot cancel when it's an invoice"
            val CONDUCTOR_ONLY_PO_IV = "Only a Conductor can cancel PO or Invoice on behalf of buyer or supplier"
            val PASSED = ""
        }
    }


    @InitiatedBy(TradeAssetCancellation.InitiatorFlow::class)
    class Acceptor(val otherPartyFlow: FlowSession) : FlowLogic<SignedTransaction>() {
        @Suspendable
        override fun call(): SignedTransaction {
            val signTransactionFlow = object : SignTransactionFlow(otherPartyFlow) {
                override fun checkTransaction(stx: SignedTransaction) = requireThat {
                    val ledgerTx = stx.toLedgerTransaction(serviceHub, false)
                    val input = ledgerTx.inputsOfType<TradeAssetState>().single()
                    "This must be a trade asset transaction." using (input is TradeAssetState)
                }
            }
            return subFlow(signTransactionFlow)
        }
    }
}
