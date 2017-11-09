package com.tradeix.concord.messages.webapi.tradeasset

import com.tradeix.concord.flowmodels.TradeAssetAmendmentFlowModel
import com.tradeix.concord.messages.SingleIdentityMessage
import java.math.BigDecimal

class TradeAssetAmendmentRequestMessage(
        override val externalId: String?,
        val value: BigDecimal?,
        val currency: String?
) : SingleIdentityMessage {

    fun toModel() = TradeAssetAmendmentFlowModel(
            externalId,
            value,
            currency
    )
}