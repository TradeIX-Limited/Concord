package com.tradeix.concord.flowmodels

import com.tradeix.concord.messages.SingleIdentityMessage

data class TradeAssetCancellationFlowModel(
        override val externalId: String?
) : SingleIdentityMessage