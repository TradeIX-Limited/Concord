package com.tradeix.concord.cordapp.funder.messages.invoices

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class InvoiceTransactionResponseMessage(
        val transactionId: String,
        val externalIds: Collection<String>
)