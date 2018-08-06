package com.tradeix.concord.cordapp.funder.messages.invoices

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class InvoiceTransferTransactionRequestMessage(
        val assets: Collection<InvoiceTransferRequestMessage> = emptyList(),
        val observers: Collection<String> = emptyList(),
        val attachments: Collection<String> = emptyList()
)