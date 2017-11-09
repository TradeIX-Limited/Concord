package com.tradeix.concord.messages.rabbit.tradeasset

import com.tradeix.concord.messages.SingleIdentityMessage
import com.tradeix.concord.messages.rabbit.RabbitRequestMessage

class TradeAssetCancellationRequestMessage(
        override val correlationId: String?,
        override val externalId: String?,
        override val tryCount: Int
) : RabbitRequestMessage(correlationId, tryCount), SingleIdentityMessage