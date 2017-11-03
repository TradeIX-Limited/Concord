package com.tradeix.concord.services.messaging

data class RabbitDeadLetterConfiguration(
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
        val poisonQueueName: String,
        val poisonQueueRoutingKey: String
)