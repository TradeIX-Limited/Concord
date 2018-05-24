package com.tradeix.concord.shared.messages.invoices

import net.corda.core.serialization.CordaSerializable
import java.math.BigDecimal
import java.time.Instant

@CordaSerializable
data class InvoiceRequestMessage(
        val externalId: String? = null,
        val attachmentId: String? = null,
        val buyer: String? = null,
        val supplier: String? = null,
        val conductor: String? = null,
        val invoiceVersion: String? = null,
        val invoiceVersionDate: Instant? = null,
        val tixInvoiceVersion: Int? = null,
        val invoiceNumber: String? = null,
        val invoiceType: String? = null,
        val reference: String? = null,
        val dueDate: Instant? = null,
        val offerId: Int? = null,
        val amount: BigDecimal? = null,
        val totalOutstanding: BigDecimal? = null,
        val created: Instant? = null,
        val updated: Instant? = null,
        val expectedSettlementDate: Instant? = null,
        val settlementDate: Instant? = null,
        val mandatoryReconciliationDate: Instant? = null,
        val invoiceDate: Instant? = null,
        val status: String? = null,
        val rejectionReason: String? = null,
        val eligibleValue: BigDecimal? = null,
        val invoicePurchaseValue: BigDecimal? = null,
        val tradeDate: Instant? = null,
        val tradePaymentDate: Instant? = null,
        val invoicePayments: BigDecimal? = null,
        val invoiceDilutions: BigDecimal? = null,
        val cancelled: Boolean? = null,
        val closeDate: Instant? = null,
        val originationNetwork: String? = null,
        val currency: String? = null,
        val siteId: String? = null,
        val purchaseOrderNumber: String? = null,
        val purchaseOrderId: String? = null,
        val composerProgramId: Int? = null
)