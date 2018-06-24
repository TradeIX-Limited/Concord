package com.tradeix.concord.shared.messages

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class TransactionRequestMessage<out TAsset>(
        val assets: Collection<TAsset> = emptyList(),
        val observers: Collection<String> = emptyList(),
        val attachments: Collection<String> = emptyList()
)