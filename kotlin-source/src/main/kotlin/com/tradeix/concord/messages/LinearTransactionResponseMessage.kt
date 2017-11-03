package com.tradeix.concord.messages

import net.corda.core.contracts.UniqueIdentifier

data class LinearTransactionResponseMessage(
        override val correlationId: String,
        override val transactionId: String,
        val linearId: UniqueIdentifier
) : TransactionResponseMessage(correlationId, 0, transactionId)