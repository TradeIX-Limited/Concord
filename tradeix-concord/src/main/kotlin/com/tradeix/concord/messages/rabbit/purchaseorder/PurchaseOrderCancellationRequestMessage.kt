package com.tradeix.concord.messages.rabbit.purchaseorder

import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderCancellationFlowModel
import com.tradeix.concord.messages.SingleIdentityMessage
import com.tradeix.concord.messages.rabbit.RabbitRequestMessage

class PurchaseOrderCancellationRequestMessage(
        override val correlationId: String? = null,
        override var tryCount: Int = 0,
        override val externalId: String? = null
) : RabbitRequestMessage(), SingleIdentityMessage {
    fun toModel() = PurchaseOrderCancellationFlowModel(externalId)
}