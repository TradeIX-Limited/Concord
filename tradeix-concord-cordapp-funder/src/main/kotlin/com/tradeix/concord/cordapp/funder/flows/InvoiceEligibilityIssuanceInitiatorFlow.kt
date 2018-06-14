package com.tradeix.concord.cordapp.funder.flows

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.shared.cordapp.flows.CollectSignaturesInitiatorFlow
import com.tradeix.concord.shared.cordapp.mapping.eligibility.InvoiceEligibilityIssuanceRequestMapper
import com.tradeix.concord.shared.domain.contracts.InvoiceEligibilityContract
import com.tradeix.concord.shared.domain.contracts.InvoiceEligibilityContract.Companion.INVOICE_ELIGIBILITY_CONTRACT_ID
import com.tradeix.concord.shared.domain.states.InvoiceEligibilityState
import com.tradeix.concord.shared.extensions.*
import com.tradeix.concord.shared.messages.InvoiceEligibilityTransactionRequestMessage
import com.tradeix.concord.shared.services.IdentityService
import com.tradeix.concord.shared.validators.InvoiceEligibilityTransactionRequestMessageValidator
import net.corda.core.contracts.Command
import net.corda.core.flows.FinalityFlow
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.InitiatingFlow
import net.corda.core.flows.StartableByRPC
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder

@StartableByRPC
@InitiatingFlow
class InvoiceEligibilityIssuanceInitiatorFlow(
        private val message: InvoiceEligibilityTransactionRequestMessage
) : FlowLogic<SignedTransaction>() {

    override val progressTracker = getDefaultProgressTracker()

    @Suspendable
    override fun call(): SignedTransaction {

        val validator = InvoiceEligibilityTransactionRequestMessageValidator()
        val identityService = IdentityService(serviceHub)
        val mapper = InvoiceEligibilityIssuanceRequestMapper(serviceHub)

        validator.validate(message)

        // Step 1 - Generating Unsigned Transaction
        progressTracker.currentStep = GeneratingTransactionStep
        val invoiceEligibilityOutputStates: Iterable<InvoiceEligibilityState> = mapper.mapMany(message.assets)

        val command = Command(
                InvoiceEligibilityContract.Issue(),
                identityService.getParticipants(invoiceEligibilityOutputStates).toOwningKeys())

        val transactionBuilder = TransactionBuilder(identityService.getNotary())
                .addOutputStates(invoiceEligibilityOutputStates, INVOICE_ELIGIBILITY_CONTRACT_ID)
                .addCommand(command)

        // Step 2 - Validating Unsigned Transaction
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
                        identityService.getWellKnownParticipantsExceptMe(invoiceEligibilityOutputStates),
                        GatheringSignaturesStep.childProgressTracker()
                )
        )

        // Step 5 - Finalize Transaction
        progressTracker.currentStep = FinalizingTransactionStep
        return subFlow(FinalityFlow(fullySignedTransaction, FinalizingTransactionStep.childProgressTracker()))
    }
}