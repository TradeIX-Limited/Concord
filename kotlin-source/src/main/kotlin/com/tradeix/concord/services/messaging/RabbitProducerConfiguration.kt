package com.tradeix.concord.services.messaging

class RabbitProducerConfiguration(
        val userName: String,
        val password: String,
        val hostName: String,
        val virtualHost: String,
        val portNumber: Int,
        val exchangeName: String,
        val exchangeType: String,
        val exchangeRoutingKey: String,
        val durableExchange: Boolean,
        val autoDeleteExchange: Boolean,
        val exchangeArguments: Map<String, Any>
)