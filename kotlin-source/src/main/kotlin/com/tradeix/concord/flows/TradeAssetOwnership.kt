package com.tradeix.concord.flows

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.contracts.TradeAssetContract
import com.tradeix.concord.contracts.TradeAssetContract.Companion.TRADE_ASSET_CONTRACT_ID
import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.flowmodels.TradeAssetOwnershipFlowModel
import com.tradeix.concord.helpers.FlowHelper
import com.tradeix.concord.helpers.VaultHelper
import com.tradeix.concord.states.TradeAssetState
import com.tradeix.concord.validators.TradeAssetOwnershipFlowModelValidator
import net.corda.core.contracts.Command
import net.corda.core.contracts.requireThat
import net.corda.core.flows.*
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker

object TradeAssetOwnership {
    @InitiatingFlow
    @StartableByRPC
    class InitiatorFlow(private val model: TradeAssetOwnershipFlowModel) : FlowLogic<SignedTransaction>() {

        companion object {
            object GENERATING_TRANSACTION : ProgressTracker.Step("Generating transaction based on new trade asset.")
            object VERIFYING_TRANSACTION : ProgressTracker.Step("Verifying contracts constraints.")
            object SIGNING_TRANSACTION : ProgressTracker.Step("Signing transaction with our private key.")
            object GATHERING_SIGNATURES : ProgressTracker.Step("Gathering the counterparty's signature.") {
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

            val validator = TradeAssetOwnershipFlowModelValidator(model)

            if (!validator.isValid) {
                throw FlowValidationException(validationErrors = validator.validationErrors)
            }

            val notary = FlowHelper.getNotary(serviceHub)

            val inputStates = model.getLinearIds().map {
                VaultHelper.getStateAndRefByLinearId(
                        serviceHub,
                        it,
                        TradeAssetState::class.java)
            }

            val outputStates = if (inputStates.isEmpty()) {
                throw FlowException("No states found for ownership change.")
            } else {
                inputStates.map {
                    it.state.data.copy(owner = FlowHelper.getPeerByLegalNameOrThrow(serviceHub, model.newOwner))
                }
            }


            // Stage 1 - Create unsigned transaction
            progressTracker.currentStep = GENERATING_TRANSACTION

            val command = Command(
                    value = TradeAssetContract.Commands.ChangeOwner(),
                    signers = outputStates.first().participants.map { it.owningKey })

            val transactionBuilder = TransactionBuilder(notary)

            inputStates.map { transactionBuilder.addInputState(it) }
            outputStates.map { transactionBuilder.addOutputState(it, TRADE_ASSET_CONTRACT_ID) }

            transactionBuilder.addCommand(command)

            // Stage 2 - Verify transaction
            progressTracker.currentStep = VERIFYING_TRANSACTION
            transactionBuilder.verify(serviceHub)

            // Stage 3 - Sign the transaction
            progressTracker.currentStep = SIGNING_TRANSACTION
            val partiallySignedTransaction = serviceHub.signInitialTransaction(transactionBuilder)

            // Stage 4 - Gather counterparty signatures
            progressTracker.currentStep = GATHERING_SIGNATURES
            val requiredSignatureFlowSessions = listOf(
                    outputStates.first().owner,
                    outputStates.first().buyer,
                    outputStates.first().supplier,
                    outputStates.first().conductor)
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
    }

    @InitiatedBy(TradeAssetOwnership.InitiatorFlow::class)
    class AcceptorFlow(val otherPartyFlow: FlowSession) : FlowLogic<SignedTransaction>() {
        @Suspendable
        override fun call(): SignedTransaction {
            val signTransactionFlow = object : SignTransactionFlow(otherPartyFlow) {
                override fun checkTransaction(stx: SignedTransaction) = requireThat {
                    stx.tx.outputs.forEach {
                        "This must be a trade asset transaction." using
                                (it.data is TradeAssetState)
                    }

                }
            }

            return subFlow(signTransactionFlow)
        }
    }
}
