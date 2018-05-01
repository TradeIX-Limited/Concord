package com.tradeix.concord.messages.rabbit.invoice

import com.tradeix.concord.flowmodels.invoice.InvoiceCancellationFlowModel
import com.tradeix.concord.messages.SingleIdentityMessage
import com.tradeix.concord.messages.rabbit.RabbitRequestMessage

class InvoiceCancellationRequestMessage(
        override val correlationId: String? = null,
        override var tryCount: Int = 0,
        override val externalId: String? = null
) : RabbitRequestMessage(), SingleIdentityMessage {
    fun toModel() = InvoiceCancellationFlowModel(externalId)
}