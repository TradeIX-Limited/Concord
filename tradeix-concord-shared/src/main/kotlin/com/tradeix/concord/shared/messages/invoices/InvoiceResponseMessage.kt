package com.tradeix.concord.shared.messages.invoices

import net.corda.core.serialization.CordaSerializable
import java.math.BigDecimal
import java.time.LocalDateTime

@CordaSerializable
data class InvoiceResponseMessage(
        val networkInvoiceUId: String,
        val invoiceVersion: String,
        val invoiceVersionDate: LocalDateTime?,
        val buyerRef: String,
        val supplierRef: String,
        val invoiceNumber: String,
        val invoiceCurrency: String,
        val invoiceDate: LocalDateTime?,
        val invoiceDueDate: LocalDateTime?,
        val invoiceAmount: BigDecimal?,
        val cashPaidToDate: BigDecimal,
        val totalOutstanding: BigDecimal,
        val reference: String,
        val expectedSettlementDate: LocalDateTime?,
        val invoicePaidDate: LocalDateTime?,
        val siteId: String,
        val cancelled: String,
        val closeDate: LocalDateTime?,
        val hash: String,
        val shippingCompanyId: String,
        val trackingNumber: String,
        val purchaseOrderNumber: String
)