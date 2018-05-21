package com.tradeix.concord.shared.services.rabbitmq.configuration

data class RabbitMQConnectionConfiguration(
        val userName: String,
        val password: String,
        val hostName: String,
        val portNumber: Int,
        val virtualHost: String
)