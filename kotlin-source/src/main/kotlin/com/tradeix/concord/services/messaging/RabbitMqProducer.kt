package com.tradeix.concord.services.messaging

import com.google.gson.Gson
import com.rabbitmq.client.AMQP
import com.tradeix.concord.interfaces.IQueueProducer
import com.tradeix.concord.messages.Message

class RabbitMqProducer<T : Message>(private val rabbitProducerConfiguration: RabbitProducerConfiguration, private val rabbitConnectionProvider: RabbitMqConnectionProvider) : IQueueProducer<T> {


    override fun Publish(message: T) {
        val serializer = Gson()
        val serializedMessage = serializer.toJson(message)
        val connection = rabbitConnectionProvider.GetConnection()
        val channel = connection.createChannel()

        channel?.exchangeDeclare(
                rabbitProducerConfiguration.exchangeName,
                rabbitProducerConfiguration.exchangeType,
                rabbitProducerConfiguration.durableExchange,
                rabbitProducerConfiguration.autoDeleteExchange,
                rabbitProducerConfiguration.exchangeArguments)

        channel?.basicPublish(rabbitProducerConfiguration.exchangeName, rabbitProducerConfiguration.exchangeRoutingKey, AMQP.BasicProperties.Builder()
                .contentType("text/plain")
                .deliveryMode(2)
                .priority(1)
                .build(), serializedMessage.toByteArray())
    }
}