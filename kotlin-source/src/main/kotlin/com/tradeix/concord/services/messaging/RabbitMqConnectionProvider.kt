package com.tradeix.concord.services.messaging

import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory

class RabbitMqConnectionProvider(private val connectionFactory: ConnectionFactory) {
    companion object {
        private var connection: Connection? = null
    }

    fun GetConnection(): Connection {
        if(connection != null && connection!!.isOpen){
            return connection!!
        }

        connection = connectionFactory.newConnection()
        return connection!!
    }
}