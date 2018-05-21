package com.tradeix.concord.shared.services.rabbitmq.configuration

data class RabbitMQProducerConfiguration(
        val exchangeName: String,
        val exchangeType: String,
        val exchangeRoutingKey: String,
        val durableExchange: Boolean,
        val autoDeleteExchange: Boolean,
        val exchangeArguments: Map<String, Any>
)