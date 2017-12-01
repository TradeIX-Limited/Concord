package com.tradeix.concord.services.messaging

import com.google.gson.Gson
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.tradeix.concord.interfaces.IQueueProducer
import com.tradeix.concord.messages.rabbit.RabbitMessage
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger

class RabbitMqProducer<T : RabbitMessage>(
        private val rabbitProducerConfiguration: RabbitProducerConfiguration,
        private val rabbitConnectionProvider: RabbitMqConnectionProvider
) : IQueueProducer<T> {
    companion object {
        protected val log: Logger = loggerFor<IssuanceMessageConsumer>()
    }
    override fun publish(message: T) {
        val serializer = Gson()
        val serializedMessage = serializer.toJson(message)
        try {
            val connection = rabbitConnectionProvider.getConnection() //TODO getting connection, exchangeDeclare and channel should be one time tasks
            val channel = connection.createChannel()

            channel?.exchangeDeclare(
                    rabbitProducerConfiguration.exchangeName,
                    rabbitProducerConfiguration.exchangeType,
                    rabbitProducerConfiguration.durableExchange,
                    rabbitProducerConfiguration.autoDeleteExchange,
                    rabbitProducerConfiguration.exchangeArguments
            )

            handleMessage(channel, serializedMessage)
        } catch (e: Exception) {
            log.error(e.message)
        }
    }

    private fun handleMessage(channel: Channel?, serializedMessage: String) {
        channel?.basicPublish(
                rabbitProducerConfiguration.exchangeName,
                rabbitProducerConfiguration.exchangeRoutingKey,
                AMQP.BasicProperties
                        .Builder()
                        .contentType("text/plain")
                        .deliveryMode(2)
                        .priority(1)
                        .build(), serializedMessage.toByteArray()
        )
    }
}