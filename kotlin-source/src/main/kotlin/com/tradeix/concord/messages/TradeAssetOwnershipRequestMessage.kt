package com.tradeix.concord.messages

import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.CordaX500Name

data class TradeAssetOwnershipRequestMessage(
        override val correlationId: String?,
        override val tryCount: Int,
        val externalIds: Array<String>?,
        val newOwner: CordaX500Name?
) : Message() {
        val linearIds: List<UniqueIdentifier>? get() = externalIds?.map { UniqueIdentifier(it) }
}