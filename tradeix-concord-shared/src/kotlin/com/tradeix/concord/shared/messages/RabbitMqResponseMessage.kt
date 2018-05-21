package com.tradeix.concord.shared.messages

data class RabbitMqResponseMessage(
        val correlationId: String,
        val externalId: String,
        val success: Boolean
)