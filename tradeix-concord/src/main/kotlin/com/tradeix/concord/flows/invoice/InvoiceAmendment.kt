package com.tradeix.concord.flows.invoice

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.contracts.InvoiceContract
import com.tradeix.concord.contracts.InvoiceContract.Companion.INVOICE_CONTRACT_ID
import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.flowmodels.invoice.InvoiceAmendmentFlowModel
import com.tradeix.concord.helpers.FlowHelper
import com.tradeix.concord.helpers.VaultHelper
import com.tradeix.concord.states.InvoiceState
import com.tradeix.concord.validators.invoice.InvoiceAmendmentFlowModelValidator
import net.corda.core.contracts.Amount
import net.corda.core.contracts.Command
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.contracts.requireThat
import net.corda.core.crypto.SecureHash.Companion.parse
import net.corda.core.flows.*
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker
import java.util.*

object InvoiceAmendment {
    @InitiatingFlow
    @StartableByRPC
    class InitiatorFlow(private val model: InvoiceAmendmentFlowModel) : FlowLogic<SignedTransaction>() {
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

            val EX_INVALID_HASH_FOR_ATTACHMENT = "Invalid SecureHash for the Supporting Document"
        }

        override val progressTracker = InvoiceIssuance.InitiatorFlow.tracker()

        @Suspendable
        override fun call(): SignedTransaction {
            val validator = InvoiceAmendmentFlowModelValidator(model)

            if (!validator.isValid) {
                throw FlowValidationException(validationErrors = validator.validationErrors)
            }

            val notary = FlowHelper.getNotary(serviceHub)
            val buyer = FlowHelper.getPeerByLegalNameOrThrow(serviceHub, model.buyer)
            val supplier = FlowHelper.getPeerByLegalNameOrMe(serviceHub, model.supplier)
            val conductor = FlowHelper.getPeerByLegalNameOrThrow(serviceHub, model.conductor)
            val currency = Currency.getInstance(model.currency)

            val inputState = VaultHelper.getStateAndRefByLinearId(
                    serviceHub,
                    UniqueIdentifier(model.externalId!!),
                    InvoiceState::class.java)

            val outputState = inputState
                    .state
                    .data
                    .copy(
                            buyer = buyer,
                            supplier = supplier,
                            conductor = conductor,
                            invoiceVersion = model.invoiceVersion!!,
                            invoiceVersionDate = model.invoiceVersionDate!!,
                            tixInvoiceVersion = model.tixInvoiceVersion!!,
                            invoiceNumber = model.invoiceNumber!!,
                            invoiceType = model.invoiceType!!,
                            reference = model.reference!!,
                            dueDate = model.dueDate!!,
                            offerId = model.offerId!!,
                            amount = Amount.fromDecimal(model.amount!!, currency),
                            totalOutstanding = Amount.fromDecimal(model.totalOutstanding!!, currency),
                            created = model.created!!,
                            updated = model.updated!!,
                            expectedSettlementDate = model.expectedSettlementDate!!,
                            settlementDate = model.settlementDate!!,
                            mandatoryReconciliationDate = model.mandatoryReconciliationDate!!,
                            invoiceDate = model.invoiceDate!!,
                            status = model.status!!,
                            rejectionReason = model.rejectionReason!!,
                            eligibleValue = Amount.fromDecimal(model.eligibleValue!!, currency),
                            invoicePurchaseValue = Amount.fromDecimal(model.invoicePurchaseValue!!, currency),
                            tradeDate = model.tradeDate!!,
                            tradePaymentDate = model.tradePaymentDate!!,
                            invoicePayments = Amount.fromDecimal(model.invoicePayments!!, currency),
                            invoiceDilutions = Amount.fromDecimal(model.invoiceDilutions!!, currency),
                            cancelled = model.cancelled!!,
                            closeDate = model.closeDate!!,
                            originationNetwork = model.originationNetwork!!,
                            hash = model.hash!!,
                            currency = currency,
                            siteId = model.siteId!!,
                            purchaseOrderNumber = model.purchaseOrderNumber!!,
                            purchaseOrderId = model.purchaseOrderId!!,
                            composerProgramId = model.composerProgramId!!
                    )


            if (model.attachmentId != null && !VaultHelper.isAttachmentInVault(serviceHub, model.attachmentId)) {
                throw FlowValidationException(validationErrors = arrayListOf(EX_INVALID_HASH_FOR_ATTACHMENT))
            }

            // Stage 1 - Create unsigned transaction
            progressTracker.currentStep = GENERATING_TRANSACTION
            val command = Command(
                    value = InvoiceContract.Commands.Amend(),
                    signers = outputState.participants.map { it.owningKey }
            )

            val transactionBuilder = TransactionBuilder(notary)
                    .addInputState(inputState)
                    .addOutputState(outputState, INVOICE_CONTRACT_ID)
                    .addCommand(command)

            if (model.attachmentId != null) {
                transactionBuilder.addAttachment(parse(model.attachmentId))
            }

            // Stage 2 - Verify transaction
            progressTracker.currentStep = VERIFYING_TRANSACTION
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

            // TODO : Move this into FlowHelper ^

            val fullySignedTransaction = subFlow(CollectSignaturesFlow(
                    partiallySignedTransaction,
                    requiredSignatureFlowSessions,
                    GATHERING_SIGNATURES.childProgressTracker()))

            // Stage 5 - Finalize transaction
            progressTracker.currentStep = FINALISING_TRANSACTION
            return subFlow(FinalityFlow(
                    transaction = fullySignedTransaction,
                    progressTracker = FINALISING_TRANSACTION.childProgressTracker()))
        }
    }

    @InitiatedBy(InitiatorFlow::class)
    class AcceptorFlow(val otherPartyFlow: FlowSession) : FlowLogic<SignedTransaction>() {
        @Suspendable
        override fun call(): SignedTransaction {
            val signTransactionFlow = object : SignTransactionFlow(otherPartyFlow) {
                override fun checkTransaction(stx: SignedTransaction) = requireThat {
                    val output = stx.tx.outputs.single().data
                    "This must be an invoice transaction." using (output is InvoiceState)
                }
            }

            return subFlow(signTransactionFlow)
        }
    }
}