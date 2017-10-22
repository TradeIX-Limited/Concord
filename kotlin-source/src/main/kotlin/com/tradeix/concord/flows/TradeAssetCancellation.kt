package com.tradeix.concord.flows

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.contracts.TradeAssetContract
import com.tradeix.concord.exceptions.ValidationException
import com.tradeix.concord.helpers.FlowHelper
import com.tradeix.concord.helpers.VaultHelper
import com.tradeix.concord.messages.TradeAssetCancellationRequestMessage
import com.tradeix.concord.models.TradeAsset
import com.tradeix.concord.states.TradeAssetState
import net.corda.core.contracts.Command
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.contracts.requireThat
import net.corda.core.flows.*
import net.corda.core.identity.Party
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker

object TradeAssetCancellation {
    @InitiatingFlow
    @StartableByRPC
    class InitiatorFlow(private val message: TradeAssetCancellationRequestMessage) : FlowLogic<SignedTransaction>() {

        companion object {

            private val EX_BUYER_CANNOT_CANCEL_MSG =
                    "Trade asset can only be cancelled by the buyer when the status of the trade asset is Purchase Order."

            private val EX_SUPPLIER_CANNOT_CANCEL_MSG =
                    "Trade asset can only be cancelled by the supplier when the status of the trade asset is Invoice."

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

            if(!message.isValid) {
                throw ValidationException(validationErrors = message.getValidationErrors())
            }

            val notary = FlowHelper.getNotary(serviceHub)

            val inputStateAndRef = VaultHelper.getStateAndRefByLinearId(
                    serviceHub = serviceHub,
                    linearId = UniqueIdentifier(id = message.linearId!!),
                    contractStateType = TradeAssetState::class.java)

            val inputState = inputStateAndRef.state.data

            // Stage 1 - Create unsigned transaction
            progressTracker.currentStep = GENERATING_TRANSACTION

            val command = Command(
                    value = TradeAssetContract.Commands.Cancel(),
                    signers = FlowHelper.getPublicKeysFromParticipants(inputState.participants))

            val transactionBuilder = TransactionBuilder(notary)
                    .addInputState(inputStateAndRef)
                    .addCommand(command)

            // Stage 2 - Verify transaction
            progressTracker.currentStep = VERIFYING_TRANSACTION
            verify(FlowHelper.getPeerByLegalNameOrMe(serviceHub, null), inputState)
            transactionBuilder.verify(serviceHub)


            // Stage 3 - Sign the transaction
            progressTracker.currentStep = SIGNING_TRANSACTION
            val partiallySignedTransaction = serviceHub.signInitialTransaction(transactionBuilder)

            // Stage 4 - Gather counterparty signatures
            progressTracker.currentStep = GATHERING_SIGNATURES
            val requiredSignatureFlowSessions = listOf(
                    inputState.owner,
                    inputState.buyer,
                    inputState.supplier,
                    inputState.conductor)
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
                    fullySignedTransaction,
                    FINALISING_TRANSACTION.childProgressTracker()))
        }

        private fun verify(initiator: Party, state: TradeAssetState) {
            val errors = ArrayList<String>()

            if(initiator == state.buyer && state.tradeAsset.status != TradeAsset.TradeAssetStatus.PURCHASE_ORDER) {
                errors.add(EX_BUYER_CANNOT_CANCEL_MSG)
            }

            if(initiator == state.supplier && state.tradeAsset.status != TradeAsset.TradeAssetStatus.INVOICE) {
                errors.add(EX_SUPPLIER_CANNOT_CANCEL_MSG)
            }

            if(!errors.isEmpty()) {
                throw ValidationException(validationErrors = errors)
            }
        }
    }

    @InitiatedBy(InitiatorFlow::class)
    class AcceptorFlow(val flowSession: FlowSession) : FlowLogic<SignedTransaction>() {
        @Suspendable
        override fun call(): SignedTransaction {
            val signTransactionFlow = object : SignTransactionFlow(flowSession) {
                override fun checkTransaction(stx: SignedTransaction) = requireThat {
//                    val output = stx.tx.outputs.single().data
//                    "This must be a trade asset transaction." using (output is TradeAssetState)
                }
            }

            return subFlow(signTransactionFlow)
        }
    }
}
