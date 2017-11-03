package com.tradeix.concord.services.messaging
import com.rabbitmq.client.ConnectionFactory
import com.google.gson.Gson
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Connection
import com.tradeix.concord.interfaces.IQueueProducer
import com.tradeix.concord.messages.Message

class RabbitMqProducer<T:Message>(private val rabbitProducerConfiguration: RabbitProducerConfiguration?, private val deadLetterConfiguration: RabbitConsumerConfiguration?) : IQueueProducer<T> {
    private val connection: Connection

    init {
        val connectionFactory = ConnectionFactory()
        if(deadLetterConfiguration != null){
            connectionFactory.username = deadLetterConfiguration.userName
            connectionFactory.password = deadLetterConfiguration.password
            connectionFactory.host = deadLetterConfiguration.hostName
            connectionFactory.virtualHost = deadLetterConfiguration.virtualHost
            connectionFactory.port = deadLetterConfiguration.portNumber
        } else if(rabbitProducerConfiguration != null) {
            connectionFactory.username = rabbitProducerConfiguration.userName
            connectionFactory.password = rabbitProducerConfiguration.password
            connectionFactory.host = rabbitProducerConfiguration.hostName
            connectionFactory.virtualHost = rabbitProducerConfiguration.virtualHost
            connectionFactory.port = rabbitProducerConfiguration.portNumber
        }

        connection = connectionFactory.newConnection()
    }

    override fun Publish(message:T){
        val serializer = Gson()
        val serializedMessage = serializer.toJson(message)

        val channel = connection.createChannel()

        if(deadLetterConfiguration != null){
            channel?.exchangeDeclare(
                    deadLetterConfiguration.exchangeName,
                    deadLetterConfiguration.exchangeType,
                    deadLetterConfiguration.durableExchange,
                    deadLetterConfiguration.autoDeleteExchange,
                    deadLetterConfiguration.exchangeArguments)

            val queueDeclare = channel?.queueDeclare(
                    deadLetterConfiguration.queueName,
                    deadLetterConfiguration.durableQueue,
                    deadLetterConfiguration.exclusiveQueue,
                    deadLetterConfiguration.autoDeleteQueue,
                    deadLetterConfiguration.queueArguments)

            val assignedQueueName = queueDeclare?.queue

            channel?.queueBind(
                    assignedQueueName,
                    deadLetterConfiguration.exchangeName,
                    deadLetterConfiguration.exchangeRoutingKey)

            channel?.basicPublish(deadLetterConfiguration.exchangeName, deadLetterConfiguration.exchangeRoutingKey, AMQP.BasicProperties.Builder()
                    .contentType("text/plain")
                    .deliveryMode(2)
                    .priority(1)
                    .build(), serializedMessage.toByteArray())

        } else if(rabbitProducerConfiguration != null) {
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
}