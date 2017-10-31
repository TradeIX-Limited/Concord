package com.tradeix.concord.services.messaging

import com.sun.org.apache.xpath.internal.operations.Bool

data class RabbitConsumerConfiguration(
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
        val exchangeArguments: Map<String, Any>,
        val queueName: String,
        val durableQueue: Boolean,
        val exclusiveQueue: Boolean,
        val autoDeleteQueue: Boolean,
        val queueArguments: Map<String, Any>
)