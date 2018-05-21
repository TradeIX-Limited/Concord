package com.tradeix.concord.shared.services.rabbitmq.messages

class RabbitMQMessage<TPayload>(
        val correlationId: String?,
        val tryCount: Int = 0,
        val payload: TPayload
)