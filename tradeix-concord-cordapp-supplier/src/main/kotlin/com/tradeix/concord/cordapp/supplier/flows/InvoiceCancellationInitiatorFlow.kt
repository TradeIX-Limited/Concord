package com.tradeix.concord.cordapp.supplier.flows

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.shared.cordapp.flows.CollectSignaturesInitiatorFlow
import com.tradeix.concord.shared.cordapp.flows.ObserveTransactionInitiatorFlow
import com.tradeix.concord.shared.cordapp.mapping.invoices.InvoiceCancellationRequestMapper
import com.tradeix.concord.shared.domain.contracts.InvoiceContract
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.extensions.*
import com.tradeix.concord.shared.messages.CancellationTransactionRequestMessage
import com.tradeix.concord.shared.services.IdentityService
import com.tradeix.concord.shared.validators.CancellationTransactionRequestMessageValidator
import net.corda.core.contracts.Command
import net.corda.core.contracts.StateAndRef
import net.corda.core.flows.FinalityFlow
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.InitiatingFlow
import net.corda.core.flows.StartableByRPC
import net.corda.core.identity.CordaX500Name
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder

@StartableByRPC
@InitiatingFlow
class InvoiceCancellationInitiatorFlow(
        private val message: CancellationTransactionRequestMessage
) : FlowLogic<SignedTransaction>() {

    override val progressTracker = getProgressTrackerWithObservationStep()

    @Suspendable
    override fun call(): SignedTransaction {

        val validator = CancellationTransactionRequestMessageValidator()
        val identityService = IdentityService(serviceHub)
        val mapper = InvoiceCancellationRequestMapper(serviceHub)

        validator.validate(message)

        // Step 1 - Generating Unsigned Transaction
        progressTracker.currentStep = GeneratingTransactionStep
        val inputStateAndRefs: Iterable<StateAndRef<InvoiceState>> = mapper.mapMany(message.assets)
        val inputStates = inputStateAndRefs.map { it.state.data }

        val command = Command(
                InvoiceContract.Cancel(),
                identityService.getParticipants(inputStates).toOwningKeys()
        )

        val transactionBuilder = TransactionBuilder(identityService.getNotary())
                .addInputStates(inputStateAndRefs)
                .addCommand(command)

        // Step 2 - Validate Unsigned Transaction
        progressTracker.currentStep = ValidatingTransactionStep
        transactionBuilder.verify(serviceHub)

        // Step 3 - Sign Unsigned Transaction
        progressTracker.currentStep = SigningTransactionStep
        val partiallySignedTransaction = serviceHub.signInitialTransaction(transactionBuilder)

        // Step 4 - Gather Counterparty Signatures
        progressTracker.currentStep = GatheringSignaturesStep
        val fullySignedTransaction = subFlow(
                CollectSignaturesInitiatorFlow(
                        partiallySignedTransaction,
                        identityService.getWellKnownParticipantsExceptMe(inputStates),
                        GatheringSignaturesStep.childProgressTracker()
                )
        )

        // Step 5 - Finalize Transaction
        progressTracker.currentStep = FinalizingTransactionStep
        val finalizedTransaction = subFlow(
                FinalityFlow(
                        fullySignedTransaction,
                        FinalizingTransactionStep.childProgressTracker()
                )
        )

        // Step 6 - Send Transaction To Observers
        progressTracker.currentStep = SendTransactionToObserversStep
        subFlow(
                ObserveTransactionInitiatorFlow(
                        finalizedTransaction,
                        message.observers.map { identityService.getPartyFromLegalNameOrThrow(CordaX500Name.parse(it)) }
                )
        )

        return finalizedTransaction
    }
}