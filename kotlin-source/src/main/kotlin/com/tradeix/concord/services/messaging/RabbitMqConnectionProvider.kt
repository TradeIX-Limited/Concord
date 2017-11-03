package com.tradeix.concord.services.messaging

import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory

class RabbitMqConnectionProvider(private val rabbitMqConnctionConfiguration: RabbitMqConnectionConfiguration) {
    companion object {
        private lateinit var connection: Connection
    }

    fun GetConnection(): Connection{
        if(!connection.isOpen){
            val connectionFactory = ConnectionFactory()
            connectionFactory.username = rabbitMqConnctionConfiguration.userName
            connectionFactory.password = rabbitMqConnctionConfiguration.password
            connectionFactory.host = rabbitMqConnctionConfiguration.hostName
            connectionFactory.virtualHost = rabbitMqConnctionConfiguration.virtualHost
            connectionFactory.port = rabbitMqConnctionConfiguration.portNumber
            connection = connectionFactory.newConnection()
        }

        return connection
    }
}