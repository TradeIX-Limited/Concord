package com.tradeix.concord.cordapp.supplier.messages.invoices

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class InvoiceCancellationTransactionRequestMessage(
        val assets: Collection<InvoiceCancellationRequestMessage> = emptyList(),
        val observers: Collection<String> = emptyList(),
        val attachments: Collection<String> = emptyList()
)