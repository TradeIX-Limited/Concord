package com.tradeix.concord.cordapp.supplier.flows

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.shared.cordapp.flows.invoices.InvoiceIssuanceInitiatorFlow
import com.tradeix.concord.shared.domain.contracts.InvoiceContract
import com.tradeix.concord.shared.domain.contracts.InvoiceContract.Companion.INVOICE_CONTRACT_ID
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.extensions.*
import com.tradeix.concord.shared.mapper.Mapper
import com.tradeix.concord.shared.messages.TransactionRequestMessage
import com.tradeix.concord.shared.messages.invoices.InvoiceRequestMessage
import com.tradeix.concord.shared.validators.InvoiceTransactionRequestMessageValidator
import net.corda.core.contracts.Command
import net.corda.core.flows.CollectSignaturesFlow
import net.corda.core.flows.FinalityFlow
import net.corda.core.flows.SendTransactionFlow
import net.corda.core.flows.StartableByRPC
import net.corda.core.identity.CordaX500Name
import net.corda.core.identity.Party
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker

@StartableByRPC
class InvoiceIssuanceInitiatorFlow(
        message: TransactionRequestMessage<InvoiceRequestMessage>
) : InvoiceIssuanceInitiatorFlow(message) {

    override val progressTracker = ProgressTracker(
            GeneratingTransactionStep,
            ValidatingTransactionStep,
            SigningTransactionStep,
            GatheringSignaturesStep,
            SendTransactionToObserversStep,
            FinalizingTransactionStep
    )

    @Suspendable
    override fun call(): SignedTransaction {

        InvoiceTransactionRequestMessageValidator().validate(message)

        val invoiceOutputStates: Iterable<InvoiceState> = Mapper
                .mapMany("issuance", message.assets, serviceHub)

        val observers: Iterable<Party> = message.observers
                .map { CordaX500Name.parse(it) }
                .map { serviceHub.networkMapCache.getPartyFromLegalNameOrThrow(it) }

        // Step 1 - Generating Unsigned Transaction
        progressTracker.currentStep = GeneratingTransactionStep
        val command = Command(InvoiceContract.Issue(), invoiceOutputStates.getAllOwningKeys())
        val transactionBuilder = TransactionBuilder(serviceHub.networkMapCache.getNotaryParty())
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
        val fullySignedTransaction = subFlow(CollectSignaturesFlow(
                partiallySignedTransaction,
                invoiceOutputStates
                        .getAllParticipants()
                        .getFlowSessionsForCounterparties(this),
                GatheringSignaturesStep.childProgressTracker())
        )

        // Step 5 - Send Transaction To Observers
        progressTracker.currentStep = SendTransactionToObserversStep
        observers.forEach { subFlow(SendTransactionFlow(initiateFlow(it), fullySignedTransaction)) }

        // Step 6 - Finalize Transaction
        progressTracker.currentStep = FinalizingTransactionStep
        return subFlow(FinalityFlow(fullySignedTransaction, FinalizingTransactionStep.childProgressTracker()))
    }
}