package com.tradeix.concord.messages.webapi.purchaseorder

import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderOwnershipFlowModel
import com.tradeix.concord.messages.MultiIdentityMessage
import net.corda.core.identity.CordaX500Name

class PurchaseOrderOwnershipRequestMessage(
        override val externalIds: List<String>?,
        val newOwner: CordaX500Name?
) : MultiIdentityMessage {

    fun toModel() = PurchaseOrderOwnershipFlowModel(
            externalIds,
            newOwner
    )
}