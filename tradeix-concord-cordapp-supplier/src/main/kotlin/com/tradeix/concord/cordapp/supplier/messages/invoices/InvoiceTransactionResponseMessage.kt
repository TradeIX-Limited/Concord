package com.tradeix.concord.cordapp.supplier.messages.invoices

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class InvoiceTransactionResponseMessage(
        val transactionId: String,
        val externalIds: Collection<String>
)