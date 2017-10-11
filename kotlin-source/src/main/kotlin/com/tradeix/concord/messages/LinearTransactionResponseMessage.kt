package com.tradeix.concord.messages

data class LinearTransactionResponseMessage(
        val linearId: String,
        val transactionId: String
)