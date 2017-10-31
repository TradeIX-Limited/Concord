package com.tradeix.concord.services.messaging
import com.rabbitmq.client.ConnectionFactory
import com.google.gson.Gson
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Connection
import com.tradeix.concord.messages.Message

class RabbitMqProducer<T:Message>(private val rabbitProducerConfiguration: RabbitProducerConfiguration){
    private val connection: Connection
    init {
        val connectionFactory = ConnectionFactory()
        connectionFactory.username = rabbitProducerConfiguration.userName
        connectionFactory.password = rabbitProducerConfiguration.password
        connectionFactory.host = rabbitProducerConfiguration.hostName
        connectionFactory.virtualHost = rabbitProducerConfiguration.virtualHost
        connectionFactory.port = rabbitProducerConfiguration.portNumber
        connection = connectionFactory.newConnection()
    }

    fun Publish(message:T){
        val channel = connection?.createChannel()
        channel?.exchangeDeclare(
                rabbitProducerConfiguration.exchangeName,
                rabbitProducerConfiguration.exchangeType,
                rabbitProducerConfiguration.durableExchange,
                rabbitProducerConfiguration.autoDeleteExchange,
                rabbitProducerConfiguration.exchangeArguments)

        val serializer = Gson()
        val serializedMessage = serializer.toJson(message)

        channel?.basicPublish(rabbitProducerConfiguration.exchangeName, rabbitProducerConfiguration.exchangeRoutingKey, AMQP.BasicProperties.Builder()
                .contentType("text/json")
                .deliveryMode(2)
                .priority(1)
                .build(), serializedMessage.toByteArray())
    }
}