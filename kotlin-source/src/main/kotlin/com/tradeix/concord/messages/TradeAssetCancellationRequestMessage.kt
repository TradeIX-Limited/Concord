package com.tradeix.concord.messages

import net.corda.core.contracts.UniqueIdentifier

data class TradeAssetCancellationRequestMessage(
        override val correlationId: String?,
        val externalId: String?
) : Message() {
    val linearId: UniqueIdentifier get() = UniqueIdentifier(externalId!!)
}