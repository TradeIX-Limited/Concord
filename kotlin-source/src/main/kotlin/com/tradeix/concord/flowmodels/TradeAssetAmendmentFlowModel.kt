package com.tradeix.concord.flowmodels

import com.tradeix.concord.messages.SingleIdentityMessage
import java.math.BigDecimal

data class TradeAssetAmendmentFlowModel(
        override val externalId: String?,
        val value: BigDecimal?,
        val currency: String?
) : SingleIdentityMessage