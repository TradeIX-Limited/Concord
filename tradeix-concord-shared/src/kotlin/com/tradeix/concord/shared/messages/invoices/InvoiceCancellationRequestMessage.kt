package com.tradeix.concord.shared.messages.invoices

import com.tradeix.concord.shared.messagecontracts.CancellationMessage
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class InvoiceCancellationRequestMessage(
        override val externalId: String? = null
) : CancellationMessage