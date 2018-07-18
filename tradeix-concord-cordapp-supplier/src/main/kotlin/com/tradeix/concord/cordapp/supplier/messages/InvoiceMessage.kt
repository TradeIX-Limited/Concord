package com.tradeix.concord.cordapp.supplier.messages

import net.corda.core.serialization.CordaSerializable
import java.math.BigDecimal
import java.time.LocalDateTime

@CordaSerializable
data class InvoiceMessage(
        val externalId: String? = null,
        val buyer: String? = null,
        val buyerCompanyReference: String? = null,
        val supplier: String? = null,
        val supplierCompanyReference: String? = null,
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
        val siteId: String? = null,
        val tradeDate: LocalDateTime? = null,
        val tradePaymentDate: LocalDateTime? = null
)