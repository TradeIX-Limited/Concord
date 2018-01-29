package com.tradeix.concord.messages.webapi.purchaseorder

import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderCancellationFlowModel
import com.tradeix.concord.messages.SingleIdentityMessage

class PurchaseOrderCancellationRequestMessage(
        override val externalId: String?
) : SingleIdentityMessage {
    fun toModel() = PurchaseOrderCancellationFlowModel(externalId)
}