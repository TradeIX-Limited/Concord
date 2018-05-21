package com.tradeix.concord.shared.messages

data class RabbitMqRequestMessage<TMessage>(
        val correlationId: String? = null,
        val tryCount: Int? = 0,
        val payload: TMessage? = null
)