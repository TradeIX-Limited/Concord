package com.tradeix.concord.flowmodels.purchaseorder

import com.tradeix.concord.messages.SingleIdentityMessage

data class PurchaseOrderCancellationFlowModel(
        override val externalId: String?
) : SingleIdentityMessage
