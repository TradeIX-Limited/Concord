package com.tradeix.concord.messages.rabbit.tradeasset

import com.tradeix.concord.flowmodels.tradeasset.TradeAssetOwnershipFlowModel
import com.tradeix.concord.messages.MultiIdentityMessage
import com.tradeix.concord.messages.rabbit.RabbitRequestMessage
import net.corda.core.identity.CordaX500Name

class TradeAssetOwnershipRequestMessage(
        override val correlationId: String?,
        override val externalIds: List<String>?,
        override var tryCount: Int,
        val newOwner: CordaX500Name?,
        val bidUniqueId: String?
        ) : RabbitRequestMessage(), MultiIdentityMessage
{
    fun toModel() = TradeAssetOwnershipFlowModel(
            externalIds,
            newOwner
    )
}