package com.tradeix.concord.messages.rabbit.invoice

import com.tradeix.concord.flowmodels.invoice.InvoiceCancellationFlowModel
import com.tradeix.concord.messages.SingleIdentityMessage
import com.tradeix.concord.messages.rabbit.RabbitRequestMessage

class InvoiceCancellationRequestMessage(
        override val correlationId: String?,
        override var tryCount: Int,
        override val externalId: String?
) : RabbitRequestMessage(), SingleIdentityMessage {
    fun toModel() = InvoiceCancellationFlowModel(externalId)
}