package com.tradeix.concord.messages.rabbit.purchaseorder

import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderOwnershipFlowModel
import com.tradeix.concord.messages.MultiIdentityMessage
import com.tradeix.concord.messages.rabbit.RabbitRequestMessage
import net.corda.core.identity.CordaX500Name

class PurchaseOrderOwnershipRequestMessage(
        override val correlationId: String?,
        override var tryCount: Int,
        override val externalIds: List<String>?,
        val newOwner: CordaX500Name?
) : RabbitRequestMessage(), MultiIdentityMessage {

    fun toModel() = PurchaseOrderOwnershipFlowModel(
            externalIds,
            newOwner
    )
}