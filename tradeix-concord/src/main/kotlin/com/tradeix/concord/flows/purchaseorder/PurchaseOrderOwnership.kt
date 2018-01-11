package com.tradeix.concord.flows.purchaseorder

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.contracts.PurchaseOrderContract
import com.tradeix.concord.contracts.PurchaseOrderContract.Companion.PURCHASE_ORDER_CONTRACT_ID
import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.exceptions.FlowVerificationException
import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderOwnershipFlowModel
import com.tradeix.concord.helpers.FlowHelper
import com.tradeix.concord.helpers.VaultHelper
import com.tradeix.concord.states.PurchaseOrderState
import com.tradeix.concord.validators.purchaseorder.PurchaseOrderOwnershipFlowModelValidator
import net.corda.core.contracts.Command
import net.corda.core.contracts.requireThat
import net.corda.core.flows.*
import net.corda.core.identity.Party
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker
import net.corda.core.utilities.ProgressTracker.Step

object PurchaseOrderOwnership {
    @InitiatingFlow
    @StartableByRPC
    class InitiatorFlow(private val model: PurchaseOrderOwnershipFlowModel) : FlowLogic<SignedTransaction>() {

        companion object {
            private val EX_CANNOT_CHANGE_OWNER =
                    "Only the current owner or the conductor can change ownership of a purchase order."

            object GENERATING_TRANSACTION : Step("Generating transaction.")
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

            val EX_INVALID_HASH_FOR_ATTACHMENT = "Invalid SecureHash for the Supporting Document"
        }

        override val progressTracker = tracker()

        @Suspendable
        override fun call(): SignedTransaction {
            val validator = PurchaseOrderOwnershipFlowModelValidator(model)

            if (!validator.isValid) {
                throw FlowValidationException(validationErrors = validator.validationErrors)
            }

            val notary = FlowHelper.getNotary(serviceHub)

            val inputStates = model.getLinearIds().map {
                VaultHelper.getStateAndRefByLinearId(serviceHub, it, PurchaseOrderState::class.java)
            }

            val outputStates = if (inputStates.isEmpty()) {
                throw  FlowException("No states found for ownership change.")
            } else {
                inputStates.map {
                    it.state.data.copy(owner = FlowHelper.getPeerByLegalNameOrThrow(serviceHub, model.newOwner))
                }
            }

            // Stage 1 - Create unsigned transaction
            progressTracker.currentStep = GENERATING_TRANSACTION

            val command = Command(
                    value = PurchaseOrderContract.Commands.ChangeOwner(),
                    signers = outputStates.first().participants.map { it.owningKey })

            val transactionBuilder = TransactionBuilder(notary)

            inputStates.map { transactionBuilder.addInputState(it) }
            outputStates.map { transactionBuilder.addOutputState(it, PURCHASE_ORDER_CONTRACT_ID) }

            transactionBuilder.addCommand(command)

            // Stage 2 - Verify transaction
            progressTracker.currentStep = VERIFYING_TRANSACTION
            inputStates.forEach { verify(FlowHelper.getPeerByLegalNameOrMe(serviceHub, null), it.state.data) }
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
                    .map { initiateFlow(serviceHub.identityService.requireWellKnownPartyFromAnonymous(it)) }

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

        private fun verify(initiator: Party, state: PurchaseOrderState) {
            val errors = ArrayList<String>()

            if (initiator != state.conductor && initiator != state.owner) {
                errors.add(EX_CANNOT_CHANGE_OWNER)
            }

            if (errors.isNotEmpty()) {
                throw FlowVerificationException(verificationErrors = errors)
            }
        }
    }

    @InitiatedBy(InitiatorFlow::class)
    class AcceptorFlow(val otherPartyFlow: FlowSession) : FlowLogic<SignedTransaction>() {
        @Suspendable
        override fun call(): SignedTransaction {
            val signTransactionFlow = object : SignTransactionFlow(otherPartyFlow) {
                override fun checkTransaction(stx: SignedTransaction) = requireThat {
                    stx.tx.outputs.forEach {
                        "This must be a purchase order transaction." using
                                (it.data is PurchaseOrderState)
                    }

                }
            }

            return subFlow(signTransactionFlow)
        }
    }
}