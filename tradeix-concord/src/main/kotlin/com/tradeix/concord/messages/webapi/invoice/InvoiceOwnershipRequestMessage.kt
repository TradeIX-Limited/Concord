package com.tradeix.concord.messages.webapi.invoice

import com.tradeix.concord.flowmodels.invoice.InvoiceOwnershipFlowModel
import com.tradeix.concord.messages.MultiIdentityMessage
import com.tradeix.concord.messages.SingleIdentityMessage
import net.corda.core.identity.CordaX500Name

class InvoiceOwnershipRequestMessage(
        override val externalIds: List<String>?,
        val newOwner: CordaX500Name?
) : MultiIdentityMessage {
    fun toModel() = InvoiceOwnershipFlowModel(externalIds, newOwner)
}