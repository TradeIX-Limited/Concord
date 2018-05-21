package com.tradeix.concord.shared.services.rabbitmq.configuration

data class RabbitMQConsumerConfiguration(
        val exchangeName: String,
        val exchangeType: String,
        val exchangeRoutingKey: String,
        val durableExchange: Boolean,
        val autoDeleteExchange: Boolean,
        val exchangeArguments: Map<String, Any>,
        val queueName: String,
        val durableQueue: Boolean,
        val exclusiveQueue: Boolean,
        val autoDeleteQueue: Boolean,
        val queueArguments: Map<String, Any>,
        val maxRetries: Int
)