package com.tradeix.concord.flows.invoice

import com.tradeix.concord.TestValueHelper.ATTACHMENT
import com.tradeix.concord.TestValueHelper.CANCELLED
import com.tradeix.concord.TestValueHelper.COMPOSER_PROGRAM_ID
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_01
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_02
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_03
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_04
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_05
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_06
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_07
import com.tradeix.concord.TestValueHelper.EXTERNAL_ID
import com.tradeix.concord.TestValueHelper.HASH
import com.tradeix.concord.TestValueHelper.INVOICE_NUMBER
import com.tradeix.concord.TestValueHelper.INVOICE_TYPE
import com.tradeix.concord.TestValueHelper.INVOICE_VERSION
import com.tradeix.concord.TestValueHelper.OFFER_ID
import com.tradeix.concord.TestValueHelper.ORIGINATION_NETWORK
import com.tradeix.concord.TestValueHelper.POSITIVE_ONE
import com.tradeix.concord.TestValueHelper.POUNDS
import com.tradeix.concord.TestValueHelper.PURCHASE_ORDER_ID
import com.tradeix.concord.TestValueHelper.PURCHASE_ORDER_NUMBER
import com.tradeix.concord.TestValueHelper.REFERENCE
import com.tradeix.concord.TestValueHelper.REJECTION_REASON
import com.tradeix.concord.TestValueHelper.SITE_ID
import com.tradeix.concord.TestValueHelper.STATUS
import com.tradeix.concord.TestValueHelper.TIX_INVOICE_VERSION
import com.tradeix.concord.flowmodels.invoice.InvoiceIssuanceFlowModel
import com.tradeix.concord.flows.AbstractFlowTest
import com.tradeix.concord.flows.FlowTestHelper.issueInvoice
import com.tradeix.concord.flows.FlowTestHelper.runActivateMembershipFlow
import com.tradeix.concord.flows.FlowTestHelper.runRevokeMembershipFlow
import com.tradeix.concord.states.InvoiceState
import net.corda.core.crypto.SecureHash
import net.corda.testing.node.StartedMockNode
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.fail

class InvoiceIssuanceFlowTests : AbstractFlowTest() {
    override fun configureNode(node: StartedMockNode) {
        node.registerInitiatedFlow(InvoiceIssuance.AcceptorFlow::class.java)
    }

    @Test
    fun `Invoice issuance flow initiated by the buyer is signed by the initiator`() {
        val transaction = issueInvoice(network, buyer.node, InvoiceIssuanceFlowModel(
                externalId = EXTERNAL_ID,
                attachmentId = null,
                conductor = conductor.name,
                buyer = buyer.name,
                supplier = supplier.name,
                funder = funder.name,
                invoiceVersion = INVOICE_VERSION,
                invoiceVersionDate = DATE_INSTANT_01,
                tixInvoiceVersion = TIX_INVOICE_VERSION,
                invoiceNumber = INVOICE_NUMBER,
                invoiceType = INVOICE_TYPE,
                reference = REFERENCE,
                dueDate = DATE_INSTANT_02,
                offerId = OFFER_ID,
                amount = POSITIVE_ONE,
                totalOutstanding = POSITIVE_ONE,
                created = DATE_INSTANT_03,
                updated = DATE_INSTANT_04,
                expectedSettlementDate = DATE_INSTANT_04,
                settlementDate = DATE_INSTANT_05,
                mandatoryReconciliationDate = DATE_INSTANT_06,
                invoiceDate = DATE_INSTANT_07,
                status = STATUS,
                rejectionReason = REJECTION_REASON,
                eligibleValue = POSITIVE_ONE,
                invoicePurchaseValue = POSITIVE_ONE,
                tradeDate = DATE_INSTANT_06,
                tradePaymentDate = DATE_INSTANT_06,
                invoicePayments = POSITIVE_ONE,
                invoiceDilutions = POSITIVE_ONE,
                cancelled = CANCELLED,
                closeDate = DATE_INSTANT_06,
                originationNetwork = ORIGINATION_NETWORK,
                hash = HASH,
                currency = POUNDS,
                siteId = SITE_ID,
                purchaseOrderNumber = PURCHASE_ORDER_NUMBER,
                purchaseOrderId = PURCHASE_ORDER_ID,
                composerProgramId = COMPOSER_PROGRAM_ID
        ))

        transaction.verifySignaturesExcept(supplier.publicKey, conductor.publicKey)
    }

    @Test
    fun `Invoice issuance flow initiated by the buyer is signed by the acceptor`() {
        val transaction = issueInvoice(network, buyer.node, InvoiceIssuanceFlowModel(
                externalId = EXTERNAL_ID,
                attachmentId = null,
                conductor = conductor.name,
                buyer = buyer.name,
                supplier = supplier.name,
                funder = funder.name,
                invoiceVersion = INVOICE_VERSION,
                invoiceVersionDate = DATE_INSTANT_01,
                tixInvoiceVersion = TIX_INVOICE_VERSION,
                invoiceNumber = INVOICE_NUMBER,
                invoiceType = INVOICE_TYPE,
                reference = REFERENCE,
                dueDate = DATE_INSTANT_02,
                offerId = OFFER_ID,
                amount = POSITIVE_ONE,
                totalOutstanding = POSITIVE_ONE,
                created = DATE_INSTANT_03,
                updated = DATE_INSTANT_04,
                expectedSettlementDate = DATE_INSTANT_04,
                settlementDate = DATE_INSTANT_05,
                mandatoryReconciliationDate = DATE_INSTANT_06,
                invoiceDate = DATE_INSTANT_07,
                status = STATUS,
                rejectionReason = REJECTION_REASON,
                eligibleValue = POSITIVE_ONE,
                invoicePurchaseValue = POSITIVE_ONE,
                tradeDate = DATE_INSTANT_06,
                tradePaymentDate = DATE_INSTANT_06,
                invoicePayments = POSITIVE_ONE,
                invoiceDilutions = POSITIVE_ONE,
                cancelled = CANCELLED,
                closeDate = DATE_INSTANT_06,
                originationNetwork = ORIGINATION_NETWORK,
                hash = HASH,
                currency = POUNDS,
                siteId = SITE_ID,
                purchaseOrderNumber = PURCHASE_ORDER_NUMBER,
                purchaseOrderId = PURCHASE_ORDER_ID,
                composerProgramId = COMPOSER_PROGRAM_ID
        ))

        transaction.verifySignaturesExcept(buyer.publicKey)
    }

    @Test
    fun `Invoice issuance flow initiated by the conductor is signed by the initiator`() {
        val transaction = issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                externalId = EXTERNAL_ID,
                attachmentId = null,
                conductor = conductor.name,
                buyer = buyer.name,
                supplier = supplier.name,
                funder = funder.name,
                invoiceVersion = INVOICE_VERSION,
                invoiceVersionDate = DATE_INSTANT_01,
                tixInvoiceVersion = TIX_INVOICE_VERSION,
                invoiceNumber = INVOICE_NUMBER,
                invoiceType = INVOICE_TYPE,
                reference = REFERENCE,
                dueDate = DATE_INSTANT_02,
                offerId = OFFER_ID,
                amount = POSITIVE_ONE,
                totalOutstanding = POSITIVE_ONE,
                created = DATE_INSTANT_03,
                updated = DATE_INSTANT_04,
                expectedSettlementDate = DATE_INSTANT_04,
                settlementDate = DATE_INSTANT_05,
                mandatoryReconciliationDate = DATE_INSTANT_06,
                invoiceDate = DATE_INSTANT_07,
                status = STATUS,
                rejectionReason = REJECTION_REASON,
                eligibleValue = POSITIVE_ONE,
                invoicePurchaseValue = POSITIVE_ONE,
                tradeDate = DATE_INSTANT_06,
                tradePaymentDate = DATE_INSTANT_06,
                invoicePayments = POSITIVE_ONE,
                invoiceDilutions = POSITIVE_ONE,
                cancelled = CANCELLED,
                closeDate = DATE_INSTANT_06,
                originationNetwork = ORIGINATION_NETWORK,
                hash = HASH,
                currency = POUNDS,
                siteId = SITE_ID,
                purchaseOrderNumber = PURCHASE_ORDER_NUMBER,
                purchaseOrderId = PURCHASE_ORDER_ID,
                composerProgramId = COMPOSER_PROGRAM_ID
        ))

        transaction.verifySignaturesExcept(buyer.publicKey, supplier.publicKey)
    }

    @Test
    fun `Invoice issuance flow initiated by the conductor is signed by the acceptor`() {
        val transaction = issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                externalId = EXTERNAL_ID,
                attachmentId = null,
                conductor = conductor.name,
                buyer = buyer.name,
                supplier = supplier.name,
                funder = funder.name,
                invoiceVersion = INVOICE_VERSION,
                invoiceVersionDate = DATE_INSTANT_01,
                tixInvoiceVersion = TIX_INVOICE_VERSION,
                invoiceNumber = INVOICE_NUMBER,
                invoiceType = INVOICE_TYPE,
                reference = REFERENCE,
                dueDate = DATE_INSTANT_02,
                offerId = OFFER_ID,
                amount = POSITIVE_ONE,
                totalOutstanding = POSITIVE_ONE,
                created = DATE_INSTANT_03,
                updated = DATE_INSTANT_04,
                expectedSettlementDate = DATE_INSTANT_04,
                settlementDate = DATE_INSTANT_05,
                mandatoryReconciliationDate = DATE_INSTANT_06,
                invoiceDate = DATE_INSTANT_07,
                status = STATUS,
                rejectionReason = REJECTION_REASON,
                eligibleValue = POSITIVE_ONE,
                invoicePurchaseValue = POSITIVE_ONE,
                tradeDate = DATE_INSTANT_06,
                tradePaymentDate = DATE_INSTANT_06,
                invoicePayments = POSITIVE_ONE,
                invoiceDilutions = POSITIVE_ONE,
                cancelled = CANCELLED,
                closeDate = DATE_INSTANT_06,
                originationNetwork = ORIGINATION_NETWORK,
                hash = HASH,
                currency = POUNDS,
                siteId = SITE_ID,
                purchaseOrderNumber = PURCHASE_ORDER_NUMBER,
                purchaseOrderId = PURCHASE_ORDER_ID,
                composerProgramId = COMPOSER_PROGRAM_ID
        ))

        transaction.verifySignaturesExcept(conductor.publicKey)
    }

    @Test
    fun `Invoice issuance flow records a transaction in all counter-party vaults`() {
        val transaction = issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                externalId = EXTERNAL_ID,
                attachmentId = null,
                conductor = conductor.name,
                buyer = buyer.name,
                supplier = supplier.name,
                funder = funder.name,
                invoiceVersion = INVOICE_VERSION,
                invoiceVersionDate = DATE_INSTANT_01,
                tixInvoiceVersion = TIX_INVOICE_VERSION,
                invoiceNumber = INVOICE_NUMBER,
                invoiceType = INVOICE_TYPE,
                reference = REFERENCE,
                dueDate = DATE_INSTANT_02,
                offerId = OFFER_ID,
                amount = POSITIVE_ONE,
                totalOutstanding = POSITIVE_ONE,
                created = DATE_INSTANT_03,
                updated = DATE_INSTANT_04,
                expectedSettlementDate = DATE_INSTANT_04,
                settlementDate = DATE_INSTANT_05,
                mandatoryReconciliationDate = DATE_INSTANT_06,
                invoiceDate = DATE_INSTANT_07,
                status = STATUS,
                rejectionReason = REJECTION_REASON,
                eligibleValue = POSITIVE_ONE,
                invoicePurchaseValue = POSITIVE_ONE,
                tradeDate = DATE_INSTANT_06,
                tradePaymentDate = DATE_INSTANT_06,
                invoicePayments = POSITIVE_ONE,
                invoiceDilutions = POSITIVE_ONE,
                cancelled = CANCELLED,
                closeDate = DATE_INSTANT_06,
                originationNetwork = ORIGINATION_NETWORK,
                hash = HASH,
                currency = POUNDS,
                siteId = SITE_ID,
                purchaseOrderNumber = PURCHASE_ORDER_NUMBER,
                purchaseOrderId = PURCHASE_ORDER_ID,
                composerProgramId = COMPOSER_PROGRAM_ID
        ))
        listOf(buyer.node, supplier.node, conductor.node).forEach {
            assertEquals(transaction, it.services.validatedTransactions.getTransaction(transaction.id))
        }
    }

    @Test
    fun `Invoice issuance flow has zero inputs and a single output`() {
        val transaction = issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                externalId = EXTERNAL_ID,
                attachmentId = null,
                conductor = conductor.name,
                buyer = buyer.name,
                supplier = supplier.name,
                funder = funder.name,
                invoiceVersion = INVOICE_VERSION,
                invoiceVersionDate = DATE_INSTANT_01,
                tixInvoiceVersion = TIX_INVOICE_VERSION,
                invoiceNumber = INVOICE_NUMBER,
                invoiceType = INVOICE_TYPE,
                reference = REFERENCE,
                dueDate = DATE_INSTANT_02,
                offerId = OFFER_ID,
                amount = POSITIVE_ONE,
                totalOutstanding = POSITIVE_ONE,
                created = DATE_INSTANT_03,
                updated = DATE_INSTANT_04,
                expectedSettlementDate = DATE_INSTANT_04,
                settlementDate = DATE_INSTANT_05,
                mandatoryReconciliationDate = DATE_INSTANT_06,
                invoiceDate = DATE_INSTANT_07,
                status = STATUS,
                rejectionReason = REJECTION_REASON,
                eligibleValue = POSITIVE_ONE,
                invoicePurchaseValue = POSITIVE_ONE,
                tradeDate = DATE_INSTANT_06,
                tradePaymentDate = DATE_INSTANT_06,
                invoicePayments = POSITIVE_ONE,
                invoiceDilutions = POSITIVE_ONE,
                cancelled = CANCELLED,
                closeDate = DATE_INSTANT_06,
                originationNetwork = ORIGINATION_NETWORK,
                hash = HASH,
                currency = POUNDS,
                siteId = SITE_ID,
                purchaseOrderNumber = PURCHASE_ORDER_NUMBER,
                purchaseOrderId = PURCHASE_ORDER_ID,
                composerProgramId = COMPOSER_PROGRAM_ID
        ))

        listOf(buyer.node, supplier.node, conductor.node).forEach {
            val recordedTransaction = it.services.validatedTransactions.getTransaction(transaction.id) ?: fail()
            assertEquals(0, recordedTransaction.tx.inputs.size)
            assertEquals(1, recordedTransaction.tx.outputs.size)
            assertEquals(EXTERNAL_ID, recordedTransaction.tx.outputsOfType<InvoiceState>().single().linearId.externalId)
        }
    }

    @Test
    fun `Invoice issuance flow with a valid attachmentId will store the attachment`() {
        val validAttachment: SecureHash = conductor.node.transaction {
            conductor.node.services.attachments.importAttachment(File(ATTACHMENT).inputStream(), "IMPORTER", "FILENAME")
        }

        val transaction = issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                externalId = EXTERNAL_ID,
                attachmentId = validAttachment.toString(),
                conductor = conductor.name,
                buyer = buyer.name,
                supplier = supplier.name,
                funder = funder.name,
                invoiceVersion = INVOICE_VERSION,
                invoiceVersionDate = DATE_INSTANT_01,
                tixInvoiceVersion = TIX_INVOICE_VERSION,
                invoiceNumber = INVOICE_NUMBER,
                invoiceType = INVOICE_TYPE,
                reference = REFERENCE,
                dueDate = DATE_INSTANT_02,
                offerId = OFFER_ID,
                amount = POSITIVE_ONE,
                totalOutstanding = POSITIVE_ONE,
                created = DATE_INSTANT_03,
                updated = DATE_INSTANT_04,
                expectedSettlementDate = DATE_INSTANT_04,
                settlementDate = DATE_INSTANT_05,
                mandatoryReconciliationDate = DATE_INSTANT_06,
                invoiceDate = DATE_INSTANT_07,
                status = STATUS,
                rejectionReason = REJECTION_REASON,
                eligibleValue = POSITIVE_ONE,
                invoicePurchaseValue = POSITIVE_ONE,
                tradeDate = DATE_INSTANT_06,
                tradePaymentDate = DATE_INSTANT_06,
                invoicePayments = POSITIVE_ONE,
                invoiceDilutions = POSITIVE_ONE,
                cancelled = CANCELLED,
                closeDate = DATE_INSTANT_06,
                originationNetwork = ORIGINATION_NETWORK,
                hash = HASH,
                currency = POUNDS,
                siteId = SITE_ID,
                purchaseOrderNumber = PURCHASE_ORDER_NUMBER,
                purchaseOrderId = PURCHASE_ORDER_ID,
                composerProgramId = COMPOSER_PROGRAM_ID
        ))

        listOf(buyer.node, supplier.node, conductor.node).forEach {
            assertEquals(transaction, it.services.validatedTransactions.getTransaction(transaction.id))
            // TODO : Can we actually check that the attachment exists?
        }
    }

    @Test
    fun `Invoice issuance flow initiated by the conductor must not fail if the conductor is a member of the BN`() {

        issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                externalId = EXTERNAL_ID,
                attachmentId = null,
                conductor = conductor.name,
                buyer = buyer.name,
                supplier = supplier.name,
                funder = funder.name,
                invoiceVersion = INVOICE_VERSION,
                invoiceVersionDate = DATE_INSTANT_01,
                tixInvoiceVersion = TIX_INVOICE_VERSION,
                invoiceNumber = INVOICE_NUMBER,
                invoiceType = INVOICE_TYPE,
                reference = REFERENCE,
                dueDate = DATE_INSTANT_02,
                offerId = OFFER_ID,
                amount = POSITIVE_ONE,
                totalOutstanding = POSITIVE_ONE,
                created = DATE_INSTANT_03,
                updated = DATE_INSTANT_04,
                expectedSettlementDate = DATE_INSTANT_04,
                settlementDate = DATE_INSTANT_05,
                mandatoryReconciliationDate = DATE_INSTANT_06,
                invoiceDate = DATE_INSTANT_07,
                status = STATUS,
                rejectionReason = REJECTION_REASON,
                eligibleValue = POSITIVE_ONE,
                invoicePurchaseValue = POSITIVE_ONE,
                tradeDate = DATE_INSTANT_06,
                tradePaymentDate = DATE_INSTANT_06,
                invoicePayments = POSITIVE_ONE,
                invoiceDilutions = POSITIVE_ONE,
                cancelled = CANCELLED,
                closeDate = DATE_INSTANT_06,
                originationNetwork = ORIGINATION_NETWORK,
                hash = HASH,
                currency = POUNDS,
                siteId = SITE_ID,
                purchaseOrderNumber = PURCHASE_ORDER_NUMBER,
                purchaseOrderId = PURCHASE_ORDER_ID,
                composerProgramId = COMPOSER_PROGRAM_ID
        )).verifySignaturesExcept(conductor.publicKey)
    }

    @Test
    fun `Invoice issuance flow initiated by the conductor must fail if the conductor is revoked`() {

        issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                externalId = EXTERNAL_ID,
                attachmentId = null,
                conductor = conductor.name,
                buyer = buyer.name,
                supplier = supplier.name,
                funder = funder.name,
                invoiceVersion = INVOICE_VERSION,
                invoiceVersionDate = DATE_INSTANT_01,
                tixInvoiceVersion = TIX_INVOICE_VERSION,
                invoiceNumber = INVOICE_NUMBER,
                invoiceType = INVOICE_TYPE,
                reference = REFERENCE,
                dueDate = DATE_INSTANT_02,
                offerId = OFFER_ID,
                amount = POSITIVE_ONE,
                totalOutstanding = POSITIVE_ONE,
                created = DATE_INSTANT_03,
                updated = DATE_INSTANT_04,
                expectedSettlementDate = DATE_INSTANT_04,
                settlementDate = DATE_INSTANT_05,
                mandatoryReconciliationDate = DATE_INSTANT_06,
                invoiceDate = DATE_INSTANT_07,
                status = STATUS,
                rejectionReason = REJECTION_REASON,
                eligibleValue = POSITIVE_ONE,
                invoicePurchaseValue = POSITIVE_ONE,
                tradeDate = DATE_INSTANT_06,
                tradePaymentDate = DATE_INSTANT_06,
                invoicePayments = POSITIVE_ONE,
                invoiceDilutions = POSITIVE_ONE,
                cancelled = CANCELLED,
                closeDate = DATE_INSTANT_06,
                originationNetwork = ORIGINATION_NETWORK,
                hash = HASH,
                currency = POUNDS,
                siteId = SITE_ID,
                purchaseOrderNumber = PURCHASE_ORDER_NUMBER,
                purchaseOrderId = PURCHASE_ORDER_ID,
                composerProgramId = COMPOSER_PROGRAM_ID
        )).verifySignaturesExcept(conductor.publicKey)

        runRevokeMembershipFlow(network, bno, conductor.party)

        try {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = null,
                    conductor = conductor.name,
                    buyer = buyer.name,
                    supplier = supplier.name,
                    funder = funder.name,
                    invoiceVersion = INVOICE_VERSION,
                    invoiceVersionDate = DATE_INSTANT_01,
                    tixInvoiceVersion = TIX_INVOICE_VERSION,
                    invoiceNumber = INVOICE_NUMBER,
                    invoiceType = INVOICE_TYPE,
                    reference = REFERENCE,
                    dueDate = DATE_INSTANT_02,
                    offerId = OFFER_ID,
                    amount = POSITIVE_ONE,
                    totalOutstanding = POSITIVE_ONE,
                    created = DATE_INSTANT_03,
                    updated = DATE_INSTANT_04,
                    expectedSettlementDate = DATE_INSTANT_04,
                    settlementDate = DATE_INSTANT_05,
                    mandatoryReconciliationDate = DATE_INSTANT_06,
                    invoiceDate = DATE_INSTANT_07,
                    status = STATUS,
                    rejectionReason = REJECTION_REASON,
                    eligibleValue = POSITIVE_ONE,
                    invoicePurchaseValue = POSITIVE_ONE,
                    tradeDate = DATE_INSTANT_06,
                    tradePaymentDate = DATE_INSTANT_06,
                    invoicePayments = POSITIVE_ONE,
                    invoiceDilutions = POSITIVE_ONE,
                    cancelled = CANCELLED,
                    closeDate = DATE_INSTANT_06,
                    originationNetwork = ORIGINATION_NETWORK,
                    hash = HASH,
                    currency = POUNDS,
                    siteId = SITE_ID,
                    purchaseOrderNumber = PURCHASE_ORDER_NUMBER,
                    purchaseOrderId = PURCHASE_ORDER_ID,
                    composerProgramId = COMPOSER_PROGRAM_ID
            ))
            fail()
        } catch (e: Exception) {
            assertEquals("Counterparty ${conductor.name} is not a member of this business network",e.message)
        }
    }

    @Test
    fun `Invoice issuance flow initiated by the conductor must not fail if the conductor is activated after being revoked`() {

        runRevokeMembershipFlow(network, bno, conductor.party)

        runActivateMembershipFlow(network, bno, conductor.party)

        issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                externalId = EXTERNAL_ID,
                attachmentId = null,
                conductor = conductor.name,
                buyer = buyer.name,
                supplier = supplier.name,
                funder = funder.name,
                invoiceVersion = INVOICE_VERSION,
                invoiceVersionDate = DATE_INSTANT_01,
                tixInvoiceVersion = TIX_INVOICE_VERSION,
                invoiceNumber = INVOICE_NUMBER,
                invoiceType = INVOICE_TYPE,
                reference = REFERENCE,
                dueDate = DATE_INSTANT_02,
                offerId = OFFER_ID,
                amount = POSITIVE_ONE,
                totalOutstanding = POSITIVE_ONE,
                created = DATE_INSTANT_03,
                updated = DATE_INSTANT_04,
                expectedSettlementDate = DATE_INSTANT_04,
                settlementDate = DATE_INSTANT_05,
                mandatoryReconciliationDate = DATE_INSTANT_06,
                invoiceDate = DATE_INSTANT_07,
                status = STATUS,
                rejectionReason = REJECTION_REASON,
                eligibleValue = POSITIVE_ONE,
                invoicePurchaseValue = POSITIVE_ONE,
                tradeDate = DATE_INSTANT_06,
                tradePaymentDate = DATE_INSTANT_06,
                invoicePayments = POSITIVE_ONE,
                invoiceDilutions = POSITIVE_ONE,
                cancelled = CANCELLED,
                closeDate = DATE_INSTANT_06,
                originationNetwork = ORIGINATION_NETWORK,
                hash = HASH,
                currency = POUNDS,
                siteId = SITE_ID,
                purchaseOrderNumber = PURCHASE_ORDER_NUMBER,
                purchaseOrderId = PURCHASE_ORDER_ID,
                composerProgramId = COMPOSER_PROGRAM_ID
        )).verifySignaturesExcept(conductor.publicKey)
    }

    @Test
    fun `Invoice issuance flow initiated by the conductor must fail if another party is revoked after being activated`() {

        runRevokeMembershipFlow(network, bno, buyer.party)

        try {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = null,
                    conductor = conductor.name,
                    buyer = buyer.name,
                    supplier = supplier.name,
                    funder = funder.name,
                    invoiceVersion = INVOICE_VERSION,
                    invoiceVersionDate = DATE_INSTANT_01,
                    tixInvoiceVersion = TIX_INVOICE_VERSION,
                    invoiceNumber = INVOICE_NUMBER,
                    invoiceType = INVOICE_TYPE,
                    reference = REFERENCE,
                    dueDate = DATE_INSTANT_02,
                    offerId = OFFER_ID,
                    amount = POSITIVE_ONE,
                    totalOutstanding = POSITIVE_ONE,
                    created = DATE_INSTANT_03,
                    updated = DATE_INSTANT_04,
                    expectedSettlementDate = DATE_INSTANT_04,
                    settlementDate = DATE_INSTANT_05,
                    mandatoryReconciliationDate = DATE_INSTANT_06,
                    invoiceDate = DATE_INSTANT_07,
                    status = STATUS,
                    rejectionReason = REJECTION_REASON,
                    eligibleValue = POSITIVE_ONE,
                    invoicePurchaseValue = POSITIVE_ONE,
                    tradeDate = DATE_INSTANT_06,
                    tradePaymentDate = DATE_INSTANT_06,
                    invoicePayments = POSITIVE_ONE,
                    invoiceDilutions = POSITIVE_ONE,
                    cancelled = CANCELLED,
                    closeDate = DATE_INSTANT_06,
                    originationNetwork = ORIGINATION_NETWORK,
                    hash = HASH,
                    currency = POUNDS,
                    siteId = SITE_ID,
                    purchaseOrderNumber = PURCHASE_ORDER_NUMBER,
                    purchaseOrderId = PURCHASE_ORDER_ID,
                    composerProgramId = COMPOSER_PROGRAM_ID
            ))
            fail()
        } catch (e: Exception) {
            assertEquals("Counterparty's ${buyer.name} membership in this business network is not active",e.message)
        }
    }
}