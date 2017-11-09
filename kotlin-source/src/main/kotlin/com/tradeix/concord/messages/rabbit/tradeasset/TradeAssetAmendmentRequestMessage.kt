package com.tradeix.concord.messages.rabbit.tradeasset

import com.tradeix.concord.messages.SingleIdentityMessage
import com.tradeix.concord.messages.rabbit.RabbitRequestMessage
import java.math.BigDecimal

class TradeAssetAmendmentRequestMessage(
        override val correlationId: String?,
        override val externalId: String?,
        override val tryCount: Int,
        val value: BigDecimal?,
        val currency: String?
) : RabbitRequestMessage(correlationId, tryCount), SingleIdentityMessage