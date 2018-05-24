package com.tradeix.concord.shared.messages.purchaseorders

import com.tradeix.concord.shared.messagecontracts.CancellationMessage
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class PurchaseOrderCancellationRequestMessage(
        override val externalId: String? = null
) : CancellationMessage