package com.tradeix.concord.services.messaging

import com.rabbitmq.client.ConnectionFactory
import com.tradeix.concord.messages.Message

class RabbitMqConsumer<T : Message>(private val rabbitConfiguration: RabbitConfiguration) {
    init {
        //rabbitConfiguration = RabbitConfiguration("cordatix_exchange", "topic", ex)
    }

    fun Subscribe(){
        val connection = ConnectionFactory().newConnection()
        val channel = connection?.createChannel()
        channel?.exchangeDeclare(rabbitConfiguration.exchangeName, rabbitConfiguration.exchangeType, rabbitConfiguration.durableExchange, rabbitConfiguration.autoDeleteExchange, rabbitConfiguration.exchangeArguments)
        val queueDeclare = channel?.queueDeclare(rabbitConfiguration.queueName, rabbitConfiguration.durableQueue, rabbitConfiguration.exclusiveQueue, rabbitConfiguration.autoDeleteQueue, rabbitConfiguration.queueArguments)
        val assignedQueueName = queueDeclare?.queue
        channel?.queueBind(assignedQueueName, rabbitConfiguration.exchangeName, rabbitConfiguration.exchangeRoutingKey)
        val consumer = MessageConsumerFactory.getMessageConsumer(channel!!, Class<T>)
        channel?.basicConsume(assignedQueueName, true, consumer)
        //channel?.



    }
}