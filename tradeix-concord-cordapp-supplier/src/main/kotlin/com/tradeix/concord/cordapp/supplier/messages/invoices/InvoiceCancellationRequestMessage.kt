package com.tradeix.concord.cordapp.supplier.messages.invoices

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class InvoiceCancellationRequestMessage(val externalId: String? = null)