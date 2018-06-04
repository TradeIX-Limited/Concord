package com.tradeix.concord.shared.messages

data class BatchTransactionResponseMessage(val items: Iterable<TransactionResponseMessage>)