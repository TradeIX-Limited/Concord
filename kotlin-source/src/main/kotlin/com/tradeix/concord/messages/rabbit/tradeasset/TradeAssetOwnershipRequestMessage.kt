package com.tradeix.concord.messages.rabbit.tradeasset

import com.tradeix.concord.messages.MultiIdentityMessage
import com.tradeix.concord.messages.rabbit.RabbitRequestMessage

class TradeAssetOwnershipRequestMessage(
        override val correlationId: String?,
        override val externalIds: List<String>?,
        override var tryCount: Int
) : RabbitRequestMessage(), MultiIdentityMessage