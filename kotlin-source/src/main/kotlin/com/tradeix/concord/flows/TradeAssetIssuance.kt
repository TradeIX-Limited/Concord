package com.tradeix.concord.flows

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.contracts.TradeAssetContract
import com.tradeix.concord.contracts.TradeAssetContract.Companion.TRADE_ASSET_CONTRACT_ID
import com.tradeix.concord.models.TradeAsset
import com.tradeix.concord.states.TradeAssetState
import net.corda.core.contracts.*
import net.corda.core.flows.*
import net.corda.core.identity.Party
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker
import net.corda.core.utilities.ProgressTracker.Step
import java.util.*

object TradeAssetIssuance {
    @InitiatingFlow
    @StartableByRPC
    class BuyerFlow(
            private val linearId: UniqueIdentifier,
            private val assetId: String,
            private val amount: Amount<Currency>,
            private val buyer: Party,
            private val supplier: Party,
            private val conductor: Party) : FlowLogic<SignedTransaction>() {

        companion object {
            object GENERATING_TRANSACTION : Step("Generating transaction based on new trade asset.")
            object VERIFYING_TRANSACTION : Step("Verifying contracts constraints.")
            object SIGNING_TRANSACTION : Step("Signing transaction with our private key.")
            object GATHERING_SIGNATURES : Step("Gathering the counterparty's signature.") {
                override fun childProgressTracker() = CollectSignaturesFlow.tracker()
            }

            object FINALISING_TRANSACTION : Step("Obtaining notary signature and recording transaction.") {
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
            val outputState = TradeAssetState(
                    linearId = linearId,
                    tradeAsset = TradeAsset(
                            assetId = assetId,
                            status = TradeAsset.STATE_ISSUED, // TODO: Check with Matt that this is hard coded - TradeAsset.PO,
                            amount = amount),
                    owner = supplier,
                    buyer = buyer,
                    supplier = supplier,
                    conductor = conductor)

            val command = Command(
                    value = TradeAssetContract.Commands.Issue(),
                    signers = outputState.participants.map { it.owningKey })

            val transactionBuilder = TransactionBuilder(notary)
                    .addOutputState(outputState, TRADE_ASSET_CONTRACT_ID)
                    .addCommand(command)

            // Stage 2 - Verify transaction
            progressTracker.currentStep = VERIFYING_TRANSACTION
            transactionBuilder.verify(serviceHub)

            // Stage 3 - Sign the transaction
            progressTracker.currentStep = SIGNING_TRANSACTION
            val partiallySignedTransaction = serviceHub.signInitialTransaction(transactionBuilder)

            // Stage X - Conductor sub flow
            //subFlow(ConductorFlow(initiateFlow(conductor)))
            // TODO : ???

            // Stage X - Conductor sub flow
            //subFlow(SupplierFlow(initiateFlow(supplier)))
            // TODO : ???

            // Stage 4 - Gather counterparty signatures
            progressTracker.currentStep = GATHERING_SIGNATURES
            val requiredSignatureFlowSessions = listOf(
                    outputState.owner,
                    outputState.buyer,
                    outputState.supplier,
                    outputState.conductor)
                    .filter { !serviceHub.myInfo.legalIdentities.contains(it) }
                    .distinct()
                    .map { initiateFlow(it) }

            val fullySignedTransaction = subFlow(CollectSignaturesFlow(
                    partiallySignedTransaction,
                    requiredSignatureFlowSessions,
                    GATHERING_SIGNATURES.childProgressTracker()))

            // Stage 5 - Finalize transaction
            progressTracker.currentStep = FINALISING_TRANSACTION
            return subFlow(FinalityFlow(
                    transaction = fullySignedTransaction,
                    progressTracker = FINALISING_TRANSACTION.childProgressTracker()))
        }
    }

//    @InitiatedBy(BuyerFlow::class)
//    class ConductorFlow(val otherPartyFlow: FlowSession) : FlowLogic<SignedTransaction>() {
//        @Suspendable
//        override fun call(): SignedTransaction {
//            val signTransactionFlow = object : SignTransactionFlow(otherPartyFlow) {
//                override fun checkTransaction(stx: SignedTransaction) = requireThat {
//                    // TODO : Verification?
//                    // TODO : External validation?
//                    // TODO : Sign?
//                    System.out.println(">>>>>>>>>>>> CONDUCTOR VERIFY ${serviceHub.myInfo.legalIdentities[0].name}")
//                }
//            }
//
//            return subFlow(signTransactionFlow)
//        }
//    }
//
//    @InitiatedBy(ConductorFlow::class)
//    class SupplierFlow(val otherPartyFlow: FlowSession) : FlowLogic<SignedTransaction>() {
//        @Suspendable
//        override fun call(): SignedTransaction {
//            val signTransactionFlow = object : SignTransactionFlow(otherPartyFlow) {
//                override fun checkTransaction(stx: SignedTransaction) = requireThat {
//                    // TODO : Verification?
//                    // TODO : External validation?
//                    // TODO : Sign?
//                    System.out.println(">>>>>>>>>>>> SUPPLIER VERIFY ${serviceHub.myInfo.legalIdentities[0].name}")
//                }
//            }
//
//            return subFlow(signTransactionFlow)
//        }
//    }

    @InitiatedBy(BuyerFlow::class)
    class Acceptor(val otherPartyFlow: FlowSession) : FlowLogic<SignedTransaction>() {
        @Suspendable
        override fun call(): SignedTransaction {
            val signTransactionFlow = object : SignTransactionFlow(otherPartyFlow) {
                override fun checkTransaction(stx: SignedTransaction) = requireThat {
                    val output = stx.tx.outputs.single().data
                    "This must be a trade asset transaction." using (output is TradeAssetState)
                }
            }

            return subFlow(signTransactionFlow)
        }
    }
}
