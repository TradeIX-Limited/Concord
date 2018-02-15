package com.tradeix.concord.flows.invoice

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.contracts.InvoiceContract
import com.tradeix.concord.contracts.InvoiceContract.Companion.INVOICE_CONTRACT_ID
import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.exceptions.FlowVerificationException
import com.tradeix.concord.flowmodels.invoice.InvoiceOwnershipFlowModel
import com.tradeix.concord.helpers.FlowHelper
import com.tradeix.concord.helpers.VaultHelper
import com.tradeix.concord.states.InvoiceState
import com.tradeix.concord.validators.invoice.InvoiceOwnershipFlowModelValidator
import net.corda.core.contracts.Command
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.contracts.requireThat
import net.corda.core.flows.*
import net.corda.core.identity.Party
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker

object InvoiceOwnership {
    @InitiatingFlow
    @StartableByRPC
    class InitiatorFlow(private val model: InvoiceOwnershipFlowModel) : FlowLogic<SignedTransaction>() {

        companion object {
            private val EX_CANNOT_CHANGE_OWNER =
                    "Only the current owner or the conductor can change ownership of an invoice."

            object GENERATING_TRANSACTION : ProgressTracker.Step("Generating transaction.")
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

            val EX_INVALID_HASH_FOR_ATTACHMENT = "Invalid SecureHash for the Supporting Document"
        }

        override val progressTracker = tracker()

        @Suspendable
        override fun call(): SignedTransaction {
            val validator = InvoiceOwnershipFlowModelValidator(model)

            if (!validator.isValid) {
                throw FlowValidationException(validationErrors = validator.validationErrors)
            }

            val notary = FlowHelper.getNotary(serviceHub)

            val inputState = VaultHelper.getStateAndRefByLinearId(
                    serviceHub,
                    UniqueIdentifier(model.externalId!!),
                    InvoiceState::class.java)

            val outputState = inputState
                    .state
                    .data
                    .copy(owner = FlowHelper.getPeerByLegalNameOrThrow(serviceHub, model.newOwner))

            // Stage 1 - Create unsigned transaction
            progressTracker.currentStep = GENERATING_TRANSACTION

            val command = Command(
                    value = InvoiceContract.Commands.ChangeOwner(),
                    signers = outputState.participants.map { it.owningKey })

            val transactionBuilder = TransactionBuilder(notary)
                    .addInputState(inputState)
                    .addOutputState(outputState, INVOICE_CONTRACT_ID)
                    .addCommand(command)

            // Stage 2 - Verify transaction
            progressTracker.currentStep = VERIFYING_TRANSACTION
            verify(FlowHelper.getPeerByLegalNameOrMe(serviceHub, null), inputState.state.data)
            transactionBuilder.verify(serviceHub)

            // Stage 3 - Sign the transaction
            progressTracker.currentStep = SIGNING_TRANSACTION
            val partiallySignedTransaction = serviceHub.signInitialTransaction(transactionBuilder)

            // Stage 4 - Gather counterparty signatures
            progressTracker.currentStep = GATHERING_SIGNATURES
            val requiredSignatureFlowSessions = listOf(
                    outputState.owner,
                    outputState.buyer,
                    outputState.supplier,
                    outputState.conductor)
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

        private fun verify(initiator: Party, state: InvoiceState) {
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
                        "This must be an invoice transaction." using
                                (it.data is InvoiceState)
                    }

                }
            }

            return subFlow(signTransactionFlow)
        }
    }
}