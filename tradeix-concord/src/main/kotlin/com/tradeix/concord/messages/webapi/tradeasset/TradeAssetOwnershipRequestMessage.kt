package com.tradeix.concord.messages.webapi.tradeasset

import com.tradeix.concord.flowmodels.tradeasset.TradeAssetOwnershipFlowModel
import com.tradeix.concord.messages.MultiIdentityMessage
import net.corda.core.identity.CordaX500Name

class TradeAssetOwnershipRequestMessage(
        override val externalIds: List<String>?,
        val newOwner: CordaX500Name?
) : MultiIdentityMessage {

    fun toModel() = TradeAssetOwnershipFlowModel(
            externalIds,
            newOwner
    )
}