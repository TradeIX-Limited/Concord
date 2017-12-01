package com.tradeix.concord.messages.webapi.tradeasset

import com.tradeix.concord.flowmodels.TradeAssetCancellationFlowModel
import com.tradeix.concord.messages.SingleIdentityMessage

class TradeAssetCancellationRequestMessage(
        override val externalId: String?
) : SingleIdentityMessage {

    fun toModel() = TradeAssetCancellationFlowModel(
            externalId
    )
}