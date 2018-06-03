package com.tradeix.concord.shared.messages

data class BatchTransactionRequestMessage<TAsset>(val items: Iterable<TransactionRequestMessage<TAsset>>? = null)