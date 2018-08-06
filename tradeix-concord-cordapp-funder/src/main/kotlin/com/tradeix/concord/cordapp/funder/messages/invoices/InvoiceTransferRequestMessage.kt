package com.tradeix.concord.cordapp.funder.messages.invoices

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class InvoiceTransferRequestMessage(val externalId: String? = null, val owner: String? = null)