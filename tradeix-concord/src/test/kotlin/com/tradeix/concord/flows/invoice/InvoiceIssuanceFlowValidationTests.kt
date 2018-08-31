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
import net.corda.testing.node.StartedMockNode
import org.junit.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class InvoiceIssuanceFlowValidationTests : AbstractFlowTest() {
    override fun configureNode(node: StartedMockNode) {
        node.registerInitiatedFlow(InvoiceIssuance.AcceptorFlow::class.java)
    }

    @Test
    fun `Issuance issuance flow will fail if externalId is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = null, //EXTERNAL_ID,
                    attachmentId = null,
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

        assertTrue(exception.validationErrors.size > 0)
        assertTrue(exception.validationErrors.contains("Field 'externalId' is required."))
    }

    @Test
    fun `Invoice issuance flow will fail if supplier is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = null,
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

        assertTrue(exception.validationErrors.size > 0)
        assertTrue(exception.validationErrors.contains("Field 'supplier' is required."))
    }

    @Test
    fun `Invoice issuance flow will fail if buyer is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = null,
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

        assertTrue(exception.validationErrors.size > 0)
        assertTrue(exception.validationErrors.contains("Field 'buyer' is required."))
    }

    @Test
    fun `Invoice issuance flow will fail if currency is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = null,
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

        assertTrue(exception.validationErrors.size > 0)
        assertTrue(exception.validationErrors.contains("Field 'currency' is required."))
    }

    @Test
    fun `Invoice issuance flow will fail if invoiceNumber is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = null,
                    buyer = buyer.name,
                    supplier = supplier.name,
                    funder = funder.name,
                    invoiceVersion = INVOICE_VERSION,
                    invoiceVersionDate = DATE_INSTANT_01,
                    tixInvoiceVersion = TIX_INVOICE_VERSION,
                    invoiceNumber = null, //INVOICE_NUMBER,
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

        assertTrue(exception.validationErrors.size > 0)
        assertTrue(exception.validationErrors.contains("Field 'invoiceNumber' is required."))
    }

    @Test
    fun `Invoice issuance flow will fail if invoiceType is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = null,
                    buyer = buyer.name,
                    supplier = supplier.name,
                    funder = funder.name,
                    invoiceVersion = INVOICE_VERSION,
                    invoiceVersionDate = DATE_INSTANT_01,
                    tixInvoiceVersion = TIX_INVOICE_VERSION,
                    invoiceNumber = INVOICE_NUMBER,
                    invoiceType = null, //INVOICE_TYPE,
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

        assertTrue(exception.validationErrors.size > 0)
        assertTrue(exception.validationErrors.contains("Field 'invoiceType' is required."))
    }

    @Test
    fun `Invoice issuance flow will fail if dueDate is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = null,
                    buyer = buyer.name,
                    supplier = supplier.name,
                    funder = funder.name,
                    invoiceVersion = INVOICE_VERSION,
                    invoiceVersionDate = DATE_INSTANT_01,
                    tixInvoiceVersion = TIX_INVOICE_VERSION,
                    invoiceNumber = INVOICE_NUMBER,
                    invoiceType = INVOICE_TYPE,
                    reference = REFERENCE,
                    dueDate = null, //DATE_INSTANT_02,
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

        assertTrue(exception.validationErrors.size > 0)
        assertTrue(exception.validationErrors.contains("Field 'dueDate' is required."))
    }

    @Test
    fun `Invoice issuance flow will fail if invoiceValue or amount is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = null,
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
                    amount = null, //POSITIVE_ONE,
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

        assertTrue(exception.validationErrors.size > 0)
        assertTrue(exception.validationErrors.contains("Field 'amount' is required."))
    }

    @Test
    fun `Invoice issuance flow will fail if totalOutstanding is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = null,
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
                    totalOutstanding = null, //POSITIVE_ONE,
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

        assertTrue(exception.validationErrors.size > 0)
        assertTrue(exception.validationErrors.contains("Field 'totalOutstanding' is required."))
    }

    @Test
    fun `Invoice issuance flow will fail if created is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = null,
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
                    created = null, //DATE_INSTANT_03,
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

        assertTrue(exception.validationErrors.size > 0)
        assertTrue(exception.validationErrors.contains("Field 'created' is required."))
    }

    @Test
    fun `Invoice issuance flow will fail if updated is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = null,
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
                    updated = null, //DATE_INSTANT_04,
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

        assertTrue(exception.validationErrors.size > 0)
        assertTrue(exception.validationErrors.contains("Field 'updated' is required."))
    }

    @Test
    fun `Invoice issuance flow will fail if expectedSettlementDate is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = null,
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
                    expectedSettlementDate = null, //DATE_INSTANT_04,
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

        assertTrue(exception.validationErrors.size > 0)
        assertTrue(exception.validationErrors.contains("Field 'expectedSettlementDate' is required."))
    }

    @Test
    fun `Invoice issuance flow will fail if invoiceDate is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = null,
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
                    invoiceDate = null, //DATE_INSTANT_07,
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

        assertTrue(exception.validationErrors.size > 0)
        assertTrue(exception.validationErrors.contains("Field 'invoiceDate' is required."))
    }

    @Test
    fun `Invoice issuance flow will fail if status is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = null,
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
                    status = null, //STATUS,
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

        assertTrue(exception.validationErrors.size > 0)
        assertTrue(exception.validationErrors.contains("Field 'status' is required."))
    }

    @Test
    fun `Invoice issuance flow will fail if eligibleValue is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = null,
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
                    eligibleValue = null, //POSITIVE_ONE,
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

        assertTrue(exception.validationErrors.size > 0)
        assertTrue(exception.validationErrors.contains("Field 'eligibleValue' is required."))
    }

    @Test
    fun `Invoice issuance flow will fail if invoicePurchaseValue is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = null,
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
                    invoicePurchaseValue = null,
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

        assertTrue(exception.validationErrors.size > 0)
        assertTrue(exception.validationErrors.contains("Field 'invoicePurchaseValue' is required."))
    }

    @Test
    fun `Invoice issuance flow will fail if invoicePayments is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = null,
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
                    invoicePayments = null,
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

        assertTrue(exception.validationErrors.size > 0)
        assertTrue(exception.validationErrors.contains("Field 'invoicePayments' is required."))
    }

    @Test
    fun `Invoice issuance flow will fail if invoiceDilutions is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = null,
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
                    invoiceDilutions = null,
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

        assertTrue(exception.validationErrors.size > 0)
        assertTrue(exception.validationErrors.contains("Field 'invoiceDilutions' is required."))
    }

    @Test
    fun `Invoice issuance flow will fail if originationNetwork is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = null,
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
                    originationNetwork = null, //ORIGINATION_NETWORK,
                    hash = HASH,
                    currency = POUNDS,
                    siteId = SITE_ID,
                    purchaseOrderNumber = PURCHASE_ORDER_NUMBER,
                    purchaseOrderId = PURCHASE_ORDER_ID,
                    composerProgramId = COMPOSER_PROGRAM_ID
            ))
        }

        assertTrue(exception.validationErrors.size > 0)
        assertTrue(exception.validationErrors.contains("Field 'originationNetwork' is required."))
    }
}