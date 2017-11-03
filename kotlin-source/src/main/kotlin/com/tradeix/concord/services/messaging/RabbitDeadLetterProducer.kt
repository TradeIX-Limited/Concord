package com.tradeix.concord.services.messaging

import com.google.gson.Gson
import com.rabbitmq.client.AMQP
import com.tradeix.concord.interfaces.IQueueDeadLetterProducer
import com.tradeix.concord.messages.Message

class RabbitDeadLetterProducer<T : Message>(private val deadLetterConfiguration: RabbitDeadLetterConfiguration, private val rabbitConnectionProvider: RabbitMqConnectionProvider) : IQueueDeadLetterProducer<T> {
    override fun Publish(message: T, isFatal: Boolean) {
        val serializer = Gson()
        val serializedMessage = serializer.toJson(message)
        val connection = rabbitConnectionProvider.GetConnection()
        val channel = connection.createChannel()

        channel?.exchangeDeclare(
                deadLetterConfiguration.exchangeName,
                deadLetterConfiguration.exchangeType,
                deadLetterConfiguration.durableExchange,
                deadLetterConfiguration.autoDeleteExchange,
                deadLetterConfiguration.exchangeArguments)

        if (isFatal) {
            val queueDeclare = channel?.queueDeclare(
                    deadLetterConfiguration.poisonQueueName,
                    true,
                    false,
                    false,
                    emptyMap()
            )

            val assignedQueueName = queueDeclare?.queue

            channel?.queueBind(
                    assignedQueueName,
                    deadLetterConfiguration.exchangeName,
                    deadLetterConfiguration.poisonQueueRoutingKey)

            channel?.basicPublish(deadLetterConfiguration.exchangeName, deadLetterConfiguration.poisonQueueRoutingKey, AMQP.BasicProperties.Builder()
                    .contentType("text/plain")
                    .deliveryMode(2)
                    .priority(1)
                    .build(), serializedMessage.toByteArray())
        } else {
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
        }
    }

}