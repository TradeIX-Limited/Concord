package com.tradeix.concord.flowmodels.tradeasset

import com.tradeix.concord.messages.SingleIdentityMessage

data class TradeAssetCancellationFlowModel(
        override val externalId: String?
) : SingleIdentityMessage