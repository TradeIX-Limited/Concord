package com.tradeix.concord.cordapp.supplier.messages.invoices

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class InvoiceResponseMessage(
        val externalId: String,
        val transactionId: String,
        val stateHash: String
)