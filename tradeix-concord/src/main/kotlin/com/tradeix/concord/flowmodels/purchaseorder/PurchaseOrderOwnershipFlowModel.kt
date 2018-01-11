package com.tradeix.concord.flowmodels.purchaseorder

import com.tradeix.concord.messages.MultiIdentityMessage
import net.corda.core.identity.CordaX500Name

data class PurchaseOrderOwnershipFlowModel(
        override val externalIds: List<String>?,
        val newOwner: CordaX500Name?
) : MultiIdentityMessage