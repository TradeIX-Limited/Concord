package com.tradeix.concord.flowmodels.tradeasset

import com.tradeix.concord.messages.MultiIdentityMessage
import net.corda.core.identity.CordaX500Name

data class TradeAssetOwnershipFlowModel(
        override val externalIds: List<String>?,
        val newOwner: CordaX500Name?
) : MultiIdentityMessage