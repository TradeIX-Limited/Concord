package com.tradeix.concord.services.messaging

data class RabbitMqConnectionConfiguration(
        val userName: String,
        val password: String,
        val hostName: String,
        val virtualHost: String,
        val portNumber: Int
)