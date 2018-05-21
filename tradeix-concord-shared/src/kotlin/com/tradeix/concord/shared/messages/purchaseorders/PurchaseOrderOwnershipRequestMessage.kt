package com.tradeix.concord.shared.messages.purchaseorders

import com.tradeix.concord.shared.messagecontracts.OwnershipMessage

data class PurchaseOrderOwnershipRequestMessage(
        override val externalId: String? = null,
        override val owner: String? = null
) : OwnershipMessage