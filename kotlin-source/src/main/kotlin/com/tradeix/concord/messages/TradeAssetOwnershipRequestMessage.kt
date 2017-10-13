package com.tradeix.concord.messages

import net.corda.core.identity.CordaX500Name
import java.util.UUID

data class TradeAssetOwnershipRequestMessage(
        val linearId: UUID?,
        val newOwner: CordaX500Name?)