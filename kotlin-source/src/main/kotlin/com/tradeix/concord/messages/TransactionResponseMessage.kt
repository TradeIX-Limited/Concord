package com.tradeix.concord.messages

open class TransactionResponseMessage(
        override val correlationId: String,
        override val tryCount: Int,
        open val transactionId: String
) : Message()