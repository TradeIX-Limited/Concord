package com.tradeix.concord.messages

import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.CordaX500Name

data class TradeAssetOwnershipRequestMessage(
        override val correlationId: String?,
        val externalId: String?,
        val newOwner: CordaX500Name?
) : Message(correlationId) {
    val linearId: UniqueIdentifier get() = UniqueIdentifier(externalId!!)
}