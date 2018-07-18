package com.tradeix.concord.cordapp.supplier.messages.invoices

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class InvoiceTransactionRequestMessage(
        val assets: Collection<InvoiceRequestMessage> = emptyList(),
        val observers: Collection<String> = emptyList(),
        val attachments: Collection<String> = emptyList()
)