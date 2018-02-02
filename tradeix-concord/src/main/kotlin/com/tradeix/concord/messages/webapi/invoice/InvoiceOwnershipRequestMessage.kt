package com.tradeix.concord.messages.webapi.invoice

import com.tradeix.concord.flowmodels.invoice.InvoiceOwnershipFlowModel
import com.tradeix.concord.messages.SingleIdentityMessage
import net.corda.core.identity.CordaX500Name

class InvoiceOwnershipRequestMessage(
        override val externalId: String?,
        val newOwner: CordaX500Name?
) : SingleIdentityMessage {
    fun toModel() = InvoiceOwnershipFlowModel(externalId, newOwner)
}