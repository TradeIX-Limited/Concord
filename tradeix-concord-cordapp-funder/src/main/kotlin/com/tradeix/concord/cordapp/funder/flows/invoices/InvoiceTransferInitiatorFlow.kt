package com.tradeix.concord.cordapp.funder.flows.invoices

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.cordapp.funder.mappers.invoices.InvoiceTransferMapper
import com.tradeix.concord.cordapp.funder.messages.invoices.InvoiceTransferTransactionRequestMessage
import com.tradeix.concord.cordapp.funder.validators.invoices.InvoiceTransferTransactionRequestMessageValidator
import com.tradeix.concord.shared.cordapp.flows.CollectSignaturesInitiatorFlow
import com.tradeix.concord.shared.domain.contracts.InvoiceContract
import com.tradeix.concord.shared.domain.contracts.InvoiceContract.Companion.INVOICE_CONTRACT_ID
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.extensions.*
import com.tradeix.concord.shared.mapper.InputAndOutput
import com.tradeix.concord.shared.services.IdentityService
import net.corda.core.contracts.Command
import net.corda.core.flows.FinalityFlow
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.InitiatingFlow
import net.corda.core.flows.StartableByRPC
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.config.Configurator

@StartableByRPC
@InitiatingFlow
class InvoiceTransferInitiatorFlow(
        private val message: InvoiceTransferTransactionRequestMessage
) : FlowLogic<SignedTransaction>() {

    override val progressTracker = getDefaultProgressTracker()

    @Suspendable
    override fun call(): SignedTransaction {

        val validator = InvoiceTransferTransactionRequestMessageValidator()
        val identityService = IdentityService(serviceHub)
        val mapper = InvoiceTransferMapper(serviceHub)

        validator.validate(message)

        // Step 1 - Generating Unsigned Transaction
        progressTracker.currentStep = GeneratingTransactionStep
        val states: Iterable<InputAndOutput<InvoiceState>> = mapper.mapMany(message.assets)

        val invoiceInputStates = states.map { it.input }
        val invoiceOutputStates = states.map { it.output }

        val command = Command(
                InvoiceContract.Transfer(),
                identityService.getParticipants(invoiceOutputStates).toOwningKeys()
        )

        val transactionBuilder = TransactionBuilder(identityService.getNotary())
                .addInputStates(invoiceInputStates)
                .addOutputStates(invoiceOutputStates, INVOICE_CONTRACT_ID)
                .addCommand(command)

        Configurator.setLevel(logger.name, Level.DEBUG)
        invoiceOutputStates.forEach {
            logger.debug("*** INVOICE TRANSFER >> Invoice external Id: " + it.linearId.externalId)
        }

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