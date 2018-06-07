package com.tradeix.concord.shared.messages

import net.corda.core.contracts.UniqueIdentifier

data class TransactionResponseMessage(val transactionId: String, val assetIds: Iterable<UniqueIdentifier>)