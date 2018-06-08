package com.tradeix.concord.shared.messages

import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class TransactionResponseMessage(val transactionId: String, val assetIds: Iterable<UniqueIdentifier>)