package com.tradeix.concord.messages.webapi.invoice

import com.tradeix.concord.flowmodels.invoice.InvoiceCancellationFlowModel
import com.tradeix.concord.messages.SingleIdentityMessage

class InvoiceCancellationRequestMessage(
        override val externalId: String?
) : SingleIdentityMessage {
    fun toModel() = InvoiceCancellationFlowModel(externalId)
}