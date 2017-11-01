package com.tradeix.concord.messages

open class TransactionResponseMessage(
        override val correlationId: String,
        open val transactionId: String
) : Message()