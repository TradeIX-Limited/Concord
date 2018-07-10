package com.tradeix.concord.shared.messages.invoices

import net.corda.core.serialization.CordaSerializable
import java.math.BigDecimal
import java.time.LocalDateTime

@CordaSerializable
data class InvoiceResponseMessage(
        val externalId: String,
        val buyer: String,
        val supplier: String,
        val invoiceNumber: String,
        val reference: String,
        val dueDate: LocalDateTime,
        val amount: BigDecimal,
        val totalOutstanding: BigDecimal,
        val settlementDate: LocalDateTime,
        val invoiceDate: LocalDateTime,
        val invoicePayments: BigDecimal,
        val invoiceDilutions: BigDecimal,
        val originationNetwork: String,
        val currency: String,
        val siteId: String,
        val tradeDate: LocalDateTime?,
        val tradePaymentDate: LocalDateTime?
)