package com.tradeix.concord.flows.invoice

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.contracts.InvoiceContract
import com.tradeix.concord.contracts.InvoiceContract.Companion.INVOICE_CONTRACT_ID
import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.flowmodels.invoice.InvoiceIPUFlowModel
import com.tradeix.concord.helpers.FlowHelper
import com.tradeix.concord.helpers.VaultHelper.VaultHelper.getStateAndRefByLinearId
import com.tradeix.concord.states.InvoiceState
import com.tradeix.concord.validators.invoice.InvoiceIPUFlowModelValidator
import net.corda.core.contracts.Command
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.contracts.requireThat
import net.corda.core.flows.*
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker

object InvoiceIPU {
    @InitiatingFlow
    @StartableByRPC
    class InitiatorFlow(private val model: InvoiceIPUFlowModel) : FlowLogic<SignedTransaction>() {

        companion object {
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
        }

        override val progressTracker = tracker()

        @Suspendable
        override fun call(): SignedTransaction {
            val validator = InvoiceIPUFlowModelValidator(model)

            if (!validator.isValid) {
                throw FlowValidationException(validationErrors = validator.validationErrors)
            }

            val notary = FlowHelper.getNotary(serviceHub)

            val inputState = getStateAndRefByLinearId(
                    serviceHub,
                    UniqueIdentifier(model.externalId!!),
                    InvoiceState::class.java
            )

            val outputState = inputState.state.data.copy(status = "IPU")

            // Stage 1 - Create unsigned transaction
            progressTracker.currentStep = GENERATING_TRANSACTION

            val command = Command(
                    value = InvoiceContract.Commands.IPU(),
                    signers = outputState
                            .participants
                            .map { it.owningKey }
                            .distinct()
            )

            val transactionBuilder = TransactionBuilder(notary)

            transactionBuilder.addInputState(inputState)
            transactionBuilder.addOutputState(outputState, INVOICE_CONTRACT_ID)
            transactionBuilder.addCommand(command)

            // Stage 2 - Verify transaction
            progressTracker.currentStep = VERIFYING_TRANSACTION
            transactionBuilder.verify(serviceHub)

            // Stage 3 - Sign the transaction
            progressTracker.currentStep = SIGNING_TRANSACTION
            val partiallySignedTransaction = serviceHub.signInitialTransaction(transactionBuilder)

            // Stage 4 - Gather counterparty signatures
            progressTracker.currentStep = GATHERING_SIGNATURES
            val requiredFlowSessions = outputState
                    .participants
                    .filter { !serviceHub.myInfo.legalIdentities.contains(it) }
                    .distinct()
                    .map { initiateFlow(serviceHub.identityService.requireWellKnownPartyFromAnonymous(it)) }

            val fullySignedTransaction = subFlow(CollectSignaturesFlow(
                    partiallySignedTransaction,
                    requiredFlowSessions,
                    GATHERING_SIGNATURES.childProgressTracker()
            ))

            // Stage 5 - Finalize transaction
            progressTracker.currentStep = FINALISING_TRANSACTION
            return subFlow(FinalityFlow(
                    fullySignedTransaction,
                    FINALISING_TRANSACTION.childProgressTracker()
            ))
        }
    }

    @InitiatedBy(InitiatorFlow::class)
    class AcceptorFlow(val otherPartyFlow: FlowSession) : FlowLogic<SignedTransaction>() {
        @Suspendable
        override fun call(): SignedTransaction {
            val signTransactionFlow = object : SignTransactionFlow(otherPartyFlow) {
                override fun checkTransaction(stx: SignedTransaction) = requireThat {
                    "This must be an invoice transaction." using (stx.tx.outputs.single().data is InvoiceState)
                }
            }

            return subFlow(signTransactionFlow)
        }
    }
}