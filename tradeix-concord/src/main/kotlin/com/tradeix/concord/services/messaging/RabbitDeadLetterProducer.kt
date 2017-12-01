package com.tradeix.concord.services.messaging

import com.google.gson.Gson
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.tradeix.concord.interfaces.IQueueDeadLetterProducer
import com.tradeix.concord.messages.rabbit.RabbitMessage
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger

class RabbitDeadLetterProducer<in T : RabbitMessage>(
        private val deadLetterConfiguration: RabbitDeadLetterConfiguration,
        private val rabbitConnectionProvider: RabbitMqConnectionProvider
) : IQueueDeadLetterProducer<T> {

    companion object {
        protected val log: Logger = loggerFor<RabbitDeadLetterProducer<RabbitMessage>>()
    }

    override fun publish(message: T, isFatal: Boolean) {
        val serializer = Gson()
        try {
            val serializedMessage = serializer.toJson(message)
            val connection = rabbitConnectionProvider.getConnection()
            val channel = connection.createChannel()

            channel?.exchangeDeclare(
                    deadLetterConfiguration.exchangeName,
                    deadLetterConfiguration.exchangeType,
                    deadLetterConfiguration.durableExchange,
                    deadLetterConfiguration.autoDeleteExchange,
                    deadLetterConfiguration.exchangeArguments)

            if (isFatal) {
                handleFatalMessage(channel, serializedMessage)
            } else {
                handleNonFatalMessage(channel, serializedMessage)
            }
        } catch (e: Exception) {
            log.error("Unable to return the Rabbit response for ${message.correlationId}")
        }
    }

    private fun handleNonFatalMessage(channel: Channel?, serializedMessage: String) {
        val queueDeclare = channel?.queueDeclare(
                deadLetterConfiguration.queueName,
                deadLetterConfiguration.durableQueue,
                deadLetterConfiguration.exclusiveQueue,
                deadLetterConfiguration.autoDeleteQueue,
                deadLetterConfiguration.getParsedQueueArguments()
        )

        val assignedQueueName = queueDeclare?.queue

        channel?.queueBind(
                assignedQueueName,
                deadLetterConfiguration.exchangeName,
                deadLetterConfiguration.exchangeRoutingKey
        )

        channel?.basicPublish(
                deadLetterConfiguration.exchangeName,
                deadLetterConfiguration.exchangeRoutingKey,
                AMQP.BasicProperties
                        .Builder()
                        .contentType("text/plain")
                        .deliveryMode(2)
                        .priority(1)
                        .build(), serializedMessage.toByteArray()
        )
    }

    private fun handleFatalMessage(channel: Channel?, serializedMessage: String) {
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

        channel?.basicPublish(
                deadLetterConfiguration.exchangeName,
                deadLetterConfiguration.poisonQueueRoutingKey,
                AMQP.BasicProperties
                        .Builder()
                        .contentType("text/plain")
                        .deliveryMode(2)
                        .priority(1)
                        .build(), serializedMessage.toByteArray()
        )
    }
}