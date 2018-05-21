package com.tradeix.concord.shared.messages.invoices

import com.tradeix.concord.shared.messagecontracts.OwnershipMessage

data class InvoiceOwnershipRequestMessage(
        override val externalId: String? = null,
        override val owner: String? = null
) : OwnershipMessage