package com.tradeix.concord.shared.messages

data class TransactionRequestMessage<out TAsset>(
        val assets: Iterable<TAsset> = emptyList(),
        val observers: Iterable<String> = emptyList(),
        val attachments: Iterable<String> = emptyList()
)