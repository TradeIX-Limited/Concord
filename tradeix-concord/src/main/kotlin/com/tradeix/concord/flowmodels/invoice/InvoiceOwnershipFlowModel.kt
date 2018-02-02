package com.tradeix.concord.flowmodels.invoice

import com.tradeix.concord.messages.SingleIdentityMessage
import net.corda.core.identity.CordaX500Name

data class InvoiceOwnershipFlowModel(
        override val externalId: String?,
        val newOwner: CordaX500Name?
) : SingleIdentityMessage