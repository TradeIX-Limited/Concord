package com.tradeix.concord.flows.invoice

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
import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.flowmodels.invoice.InvoiceIssuanceFlowModel
import com.tradeix.concord.flows.AbstractFlowTest
import com.tradeix.concord.flows.FlowTestHelper.issueInvoice
import net.corda.node.internal.StartedNode
import net.corda.testing.node.MockNetwork
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class InvoiceIssuanceFlowValidationTests : AbstractFlowTest() {
    override fun configureNode(node: StartedNode<MockNetwork.MockNode>) {
        node.registerInitiatedFlow(InvoiceIssuance.AcceptorFlow::class.java)
    }

    @Test
    fun `Issuance issuance flow will fail if externalId is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = null, //EXTERNAL_ID,
                    attachmentId = HASH,
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
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'externalId' is required.", exception.validationErrors.single())
    }

    @Test
    fun `Invoice issuance flow will fail if supplier is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = HASH,
                    buyer = buyer.name,
                    supplier = null, //supplier.name,
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
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'supplier' is required.", exception.validationErrors.single())
    }

    @Test
    fun `Invoice issuance flow will fail if buyer is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = HASH,
                    buyer = null, //buyer.name,
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
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'buyer' is required.", exception.validationErrors.single())
    }

    @Test
    fun `Invoice issuance flow will fail if funder is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = HASH,
                    buyer = buyer.name,
                    supplier = supplier.name,
                    funder = null, //funder.name,
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
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'funder' is required.", exception.validationErrors.single())
    }

    @Test
    fun `Invoice issuance flow will fail if currency is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = HASH,
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
                    currency = null, //POUNDS,
                    siteId = SITE_ID,
                    purchaseOrderNumber = PURCHASE_ORDER_NUMBER,
                    purchaseOrderId = PURCHASE_ORDER_ID,
                    composerProgramId = COMPOSER_PROGRAM_ID
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'currency' is required.", exception.validationErrors.single())
    }

}