package com.tradeix.concord.shared.messages.invoices

import com.tradeix.concord.shared.messagecontracts.CancellationMessage

data class InvoiceCancellationRequestMessage(
        override val externalId: String? = null
) : CancellationMessage