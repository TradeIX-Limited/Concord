package com.tradeix.concord.shared.messages.invoices

import net.corda.core.serialization.CordaSerializable
import java.math.BigDecimal
import java.time.LocalDateTime

@CordaSerializable
data class InvoiceRequestMessage(
        val externalId: String? = null,
        val buyer: String? = null,
        val supplier: String? = null,
        val invoiceVersion: String? = null,
        val invoiceVersionDate: LocalDateTime? = null,
        val tixInvoiceVersion: Int? = null,
        val invoiceNumber: String? = null,
        val invoiceType: String? = null,
        val reference: String? = null,
        val dueDate: LocalDateTime? = null,
        val offerId: Int? = null,
        val amount: BigDecimal? = null,
        val totalOutstanding: BigDecimal? = null,
        val created: LocalDateTime? = null,
        val updated: LocalDateTime? = null,
        val expectedSettlementDate: LocalDateTime? = null,
        val settlementDate: LocalDateTime? = null,
        val mandatoryReconciliationDate: LocalDateTime? = null,
        val invoiceDate: LocalDateTime? = null,
        val status: String? = null,
        val rejectionReason: String? = null,
        val eligibleValue: BigDecimal? = null,
        val invoicePurchaseValue: BigDecimal? = null,
        val tradeDate: LocalDateTime? = null,
        val tradePaymentDate: LocalDateTime? = null,
        val invoicePayments: BigDecimal? = null,
        val invoiceDilutions: BigDecimal? = null,
        val cancelled: Boolean? = null,
        val closeDate: LocalDateTime? = null,
        val originationNetwork: String? = null,
        val currency: String? = null,
        val siteId: String? = null,
        val purchaseOrderNumber: String? = null,
        val purchaseOrderId: String? = null,
        val composerProgramId: Int? = null
)