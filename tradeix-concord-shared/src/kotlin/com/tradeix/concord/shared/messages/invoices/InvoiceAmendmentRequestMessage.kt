package com.tradeix.concord.shared.messages.invoices

import com.tradeix.concord.shared.messagecontracts.InvoiceMessage
import net.corda.core.serialization.CordaSerializable
import java.math.BigDecimal
import java.time.Instant

@CordaSerializable
data class InvoiceAmendmentRequestMessage(
        override val externalId: String? = null,
        override val attachmentId: String? = null,
        override val buyer: String? = null,
        override val supplier: String? = null,
        override val conductor: String? = null,
        override val invoiceVersion: String? = null,
        override val invoiceVersionDate: Instant? = null,
        override val tixInvoiceVersion: Int? = null,
        override val invoiceNumber: String? = null,
        override val invoiceType: String? = null,
        override val reference: String? = null,
        override val dueDate: Instant? = null,
        override val offerId: Int? = null,
        override val amount: BigDecimal? = null,
        override val totalOutstanding: BigDecimal? = null,
        override val created: Instant? = null,
        override val updated: Instant? = null,
        override val expectedSettlementDate: Instant? = null,
        override val settlementDate: Instant? = null,
        override val mandatoryReconciliationDate: Instant? = null,
        override val invoiceDate: Instant? = null,
        override val status: String? = null,
        override val rejectionReason: String? = null,
        override val eligibleValue: BigDecimal? = null,
        override val invoicePurchaseValue: BigDecimal? = null,
        override val tradeDate: Instant? = null,
        override val tradePaymentDate: Instant? = null,
        override val invoicePayments: BigDecimal? = null,
        override val invoiceDilutions: BigDecimal? = null,
        override val cancelled: Boolean? = null,
        override val closeDate: Instant? = null,
        override val originationNetwork: String? = null,
        override val currency: String? = null,
        override val siteId: String? = null,
        override val purchaseOrderNumber: String? = null,
        override val purchaseOrderId: String? = null,
        override val composerProgramId: Int? = null
) : InvoiceMessage