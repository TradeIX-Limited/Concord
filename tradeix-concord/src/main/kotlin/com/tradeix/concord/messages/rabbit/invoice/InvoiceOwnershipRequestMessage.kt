package com.tradeix.concord.messages.rabbit.invoice

import com.tradeix.concord.flowmodels.invoice.InvoiceOwnershipFlowModel
import com.tradeix.concord.messages.SingleIdentityMessage
import com.tradeix.concord.messages.rabbit.RabbitRequestMessage
import net.corda.core.identity.CordaX500Name

class InvoiceOwnershipRequestMessage(
        override val correlationId: String?,
        override var tryCount: Int,
        override val externalId: String?,
        val newOwner: CordaX500Name?
) : RabbitRequestMessage(), SingleIdentityMessage {

    fun toModel() = InvoiceOwnershipFlowModel(
            externalId,
            newOwner
    )
}