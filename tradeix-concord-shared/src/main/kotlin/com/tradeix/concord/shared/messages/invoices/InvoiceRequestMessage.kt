package com.tradeix.concord.shared.messages.invoices

import net.corda.core.serialization.CordaSerializable
import java.math.BigDecimal
import java.time.LocalDateTime

@CordaSerializable
data class InvoiceRequestMessage(
        val externalId: String? = null,
        val buyer: String? = null,
        val supplier: String? = null,
        val invoiceNumber: String? = null,
        val reference: String? = null,
        val dueDate: LocalDateTime? = null,
        val amount: BigDecimal? = null,
        val totalOutstanding: BigDecimal? = null,
        val settlementDate: LocalDateTime? = null,
        val invoiceDate: LocalDateTime? = null,
        val invoicePayments: BigDecimal? = null,
        val invoiceDilutions: BigDecimal? = null,
        val originationNetwork: String? = null,
        val currency: String? = null,
        val siteId: String? = null
)