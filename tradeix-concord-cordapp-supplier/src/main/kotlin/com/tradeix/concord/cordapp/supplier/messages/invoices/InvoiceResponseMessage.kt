package com.tradeix.concord.cordapp.supplier.messages.invoices

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class InvoiceResponseMessage(  // TODO : Not sure if it should be "InvoiceResponseNotificationMessage"
        val externalId: String,
        val transactionId: String,
        val stateHash: String
)