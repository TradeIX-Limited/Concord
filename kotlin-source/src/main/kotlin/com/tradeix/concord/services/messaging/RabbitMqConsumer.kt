package com.tradeix.concord.services.messaging

import com.rabbitmq.client.ConnectionFactory
import com.tradeix.concord.interfaces.IQueueConsumer
import com.tradeix.concord.messages.Message

class RabbitMqConsumer<T : Message>(private val rabbitConsumerConfiguration: RabbitConsumerConfiguration, private val messageClass: Class<T>) : IQueueConsumer {
    init {
        //rabbitConsumerConfiguration = RabbitConsumerConfiguration("cordatix_exchange", "topic", ex)
    }

    override fun subscribe() {
        val connectionFactory = ConnectionFactory()
        connectionFactory.username = rabbitConsumerConfiguration.userName
        connectionFactory.password = rabbitConsumerConfiguration.password
        connectionFactory.host = rabbitConsumerConfiguration.hostName
        connectionFactory.virtualHost = rabbitConsumerConfiguration.virtualHost
        connectionFactory.port = rabbitConsumerConfiguration.portNumber
        val connection = connectionFactory.newConnection()
        val channel = connection?.createChannel()


        channel?.exchangeDeclare(
                rabbitConsumerConfiguration.exchangeName,
                rabbitConsumerConfiguration.exchangeType,
                rabbitConsumerConfiguration.durableExchange,
                rabbitConsumerConfiguration.autoDeleteExchange,
                rabbitConsumerConfiguration.exchangeArguments)

        val queueDeclare = channel?.queueDeclare(
                rabbitConsumerConfiguration.queueName,
                rabbitConsumerConfiguration.durableQueue,
                rabbitConsumerConfiguration.exclusiveQueue,
                rabbitConsumerConfiguration.autoDeleteQueue,
                rabbitConsumerConfiguration.queueArguments)


        val assignedQueueName = queueDeclare?.queue

        channel?.queueBind(
                assignedQueueName,
                rabbitConsumerConfiguration.exchangeName,
                rabbitConsumerConfiguration.exchangeRoutingKey)

        val consumer = MessageConsumerFactory.getMessageConsumer(channel!!, messageClass)

        channel.basicConsume(assignedQueueName, false, consumer)
    }

    // Usage example...
//    fun foo() {
//        val rc = RabbitConsumerConfiguration()
//        RabbitMqConsumer(rc, TradeAssetIssuanceRequestMessage::class.java)
//    }
}