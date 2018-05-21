package com.tradeix.concord.shared.messages.purchaseorders

import com.tradeix.concord.shared.messagecontracts.CancellationMessage

data class PurchaseOrderCancellationRequestMessage(
        override val externalId: String? = null
) : CancellationMessage