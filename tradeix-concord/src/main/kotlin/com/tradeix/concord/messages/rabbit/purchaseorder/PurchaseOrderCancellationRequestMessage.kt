package com.tradeix.concord.messages.rabbit.purchaseorder

import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderCancellationFlowModel
import com.tradeix.concord.messages.SingleIdentityMessage
import com.tradeix.concord.messages.rabbit.RabbitRequestMessage

class PurchaseOrderCancellationRequestMessage(
        override val correlationId: String?,
        override var tryCount: Int,
        override val externalId: String?
): RabbitRequestMessage(), SingleIdentityMessage {
    fun toModel() = PurchaseOrderCancellationFlowModel(externalId)
}