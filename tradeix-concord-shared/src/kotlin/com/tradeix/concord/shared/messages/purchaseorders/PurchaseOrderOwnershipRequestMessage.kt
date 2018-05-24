package com.tradeix.concord.shared.messages.purchaseorders

import com.tradeix.concord.shared.messagecontracts.OwnershipMessage
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class PurchaseOrderOwnershipRequestMessage(
        override val externalId: String? = null,
        override val owner: String? = null
) : OwnershipMessage