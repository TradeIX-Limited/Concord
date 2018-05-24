package com.tradeix.concord.cordapp.supplier.flows

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.shared.cordapp.invoices.InvoiceIssuanceInitiatorFlow
import com.tradeix.concord.shared.data.AttachmentRepository
import com.tradeix.concord.shared.domain.contracts.InvoiceContract
import com.tradeix.concord.shared.domain.contracts.InvoiceContract.Companion.INVOICE_CONTRACT_ID
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.extensions.*
import com.tradeix.concord.shared.mapper.Mapper
import com.tradeix.concord.shared.messages.invoices.InvoiceRequestMessage
import com.tradeix.concord.shared.validators.InvoiceRequestMessageValidator
import net.corda.core.contracts.Command
import net.corda.core.crypto.SecureHash
import net.corda.core.flows.CollectSignaturesFlow
import net.corda.core.flows.FinalityFlow
import net.corda.core.flows.InitiatingFlow
import net.corda.core.flows.StartableByRPC
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder

@InitiatingFlow
@StartableByRPC
class InvoiceIssuanceInitiatorFlow(message: InvoiceRequestMessage) : InvoiceIssuanceInitiatorFlow(message) {

    override val progressTracker = getDefaultProgressTracker()

    @Suspendable
    override fun call(): SignedTransaction {

        InvoiceRequestMessageValidator().validate(message)

        val attachmentRepository = AttachmentRepository.fromServiceHub(serviceHub)

        val invoiceOutputState = Mapper.map<InvoiceRequestMessage, InvoiceState>("issuance", message, serviceHub)

        // Step 1 - Generating Unsigned Transaction
        progressTracker.currentStep = GeneratingTransactionStep
        val command = Command(InvoiceContract.Commands.Issue(), invoiceOutputState.participants.toOwningKeys())
        val transactionBuilder = TransactionBuilder(serviceHub.networkMapCache.getNotaryParty())
                .addOutputState(invoiceOutputState, INVOICE_CONTRACT_ID)
                .addCommand(command)

        val attachmentId = SecureHash.tryParse(message.attachmentId)
        if (attachmentId != null && !attachmentRepository.hasAttachment(attachmentId)) {
            transactionBuilder.addAttachment(attachmentId)
        }

        // Step 2 - Validate Unsigned Transaction
        progressTracker.currentStep = ValidatingTransactionStep
        transactionBuilder.verify(serviceHub)

        // Step 3 - Sign Unsigned Transaction
        progressTracker.currentStep = SigningTransactionStep
        val partiallySignedTransaction = serviceHub.signInitialTransaction(transactionBuilder)

        // Step 4 - Gather Counterparty Signatures
        progressTracker.currentStep = GatheringSignaturesStep
        val fullySignedTransaction = subFlow(CollectSignaturesFlow(
                partiallySignedTransaction,
                invoiceOutputState.participants.getFlowSessionsForCounterparties(this),
                GatheringSignaturesStep.childProgressTracker())
        )

        // Step 5 - Finalize Transaction
        progressTracker.currentStep = FinalizingTransactionStep
        return subFlow(FinalityFlow(fullySignedTransaction, FinalizingTransactionStep.childProgressTracker()))
    }
}