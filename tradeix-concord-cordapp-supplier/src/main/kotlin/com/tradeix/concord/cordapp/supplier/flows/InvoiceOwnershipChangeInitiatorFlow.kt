package com.tradeix.concord.cordapp.supplier.flows

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.shared.cordapp.flows.CollectSignaturesInitiatorFlow
import com.tradeix.concord.shared.cordapp.mapping.invoices.InvoiceOwnershipRequestMapper
import com.tradeix.concord.shared.domain.contracts.InvoiceContract
import com.tradeix.concord.shared.domain.contracts.InvoiceContract.Companion.INVOICE_CONTRACT_ID
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.extensions.*
import com.tradeix.concord.shared.mapper.InputAndOutput
import com.tradeix.concord.shared.messages.OwnershipTransactionRequestMessage
import com.tradeix.concord.shared.services.IdentityService
import com.tradeix.concord.shared.validators.OwnershipTransactionRequestMessageValidator
import net.corda.core.contracts.Command
import net.corda.core.flows.FinalityFlow
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.InitiatingFlow
import net.corda.core.flows.StartableByRPC
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder

@StartableByRPC
@InitiatingFlow
class InvoiceOwnershipChangeInitiatorFlow(
        private val message: OwnershipTransactionRequestMessage
) : FlowLogic<SignedTransaction>() {

    override val progressTracker = getDefaultProgressTracker()

    @Suspendable
    override fun call(): SignedTransaction {

        val validator = OwnershipTransactionRequestMessageValidator()
        val identityService = IdentityService(serviceHub)
        val mapper = InvoiceOwnershipRequestMapper(serviceHub)

        validator.validate(message)

        // Step 1 - Generating Unsigned Transaction
        progressTracker.currentStep = GeneratingTransactionStep
        val states: Iterable<InputAndOutput<InvoiceState>> = mapper.mapMany(message.assets)

        val invoiceInputStates = states.map { it.input }
        val invoiceOutputStates = states.map { it.output }

        val command = Command(
                InvoiceContract.ChangeOwner(),
                identityService.getParticipants(invoiceOutputStates).toOwningKeys()
        )

        val transactionBuilder = TransactionBuilder(identityService.getNotary())
                .addInputStates(invoiceInputStates)
                .addOutputStates(invoiceOutputStates, INVOICE_CONTRACT_ID)
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
                        identityService.getWellKnownParticipantsExceptMe(invoiceOutputStates),
                        GatheringSignaturesStep.childProgressTracker()
                )
        )

        // Step 5 - Finalize Transaction
        progressTracker.currentStep = FinalizingTransactionStep
        return subFlow(
                FinalityFlow(
                        fullySignedTransaction,
                        FinalizingTransactionStep.childProgressTracker()
                )
        )
    }
}