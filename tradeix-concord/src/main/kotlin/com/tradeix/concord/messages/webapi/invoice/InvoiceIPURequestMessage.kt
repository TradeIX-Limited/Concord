package com.tradeix.concord.messages.webapi.invoice

import com.tradeix.concord.flowmodels.invoice.InvoiceIPUFlowModel
import com.tradeix.concord.messages.SingleIdentityMessage

data class InvoiceIPURequestMessage(
        override val externalId: String? = null
) : SingleIdentityMessage {

    fun toModel() = InvoiceIPUFlowModel(externalId)
}