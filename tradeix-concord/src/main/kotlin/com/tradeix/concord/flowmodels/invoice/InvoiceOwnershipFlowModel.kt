package com.tradeix.concord.flowmodels.invoice

import com.tradeix.concord.messages.MultiIdentityMessage
import com.tradeix.concord.messages.SingleIdentityMessage
import net.corda.core.identity.CordaX500Name

data class InvoiceOwnershipFlowModel(
        override val externalIds: List<String>?,
        val newOwner: CordaX500Name?
) : MultiIdentityMessage