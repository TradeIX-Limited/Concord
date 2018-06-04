package com.tradeix.concord.shared.messages

data class TransactionRequestMessage<TAsset>(
        val asset: TAsset? = null,
        val observers: Iterable<String>? = null,
        val attachments: Iterable<String>? = null
)