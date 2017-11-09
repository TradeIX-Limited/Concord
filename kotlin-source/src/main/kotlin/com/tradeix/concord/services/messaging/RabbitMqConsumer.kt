package com.tradeix.concord.services.messaging

import com.tradeix.concord.interfaces.IQueueConsumer
import com.tradeix.concord.interfaces.IQueueDeadLetterProducer
import com.tradeix.concord.messages.Message

class RabbitMqConsumer<T : Message>(
        private val rabbitConsumerConfiguration: RabbitConsumerConfiguration,
        private val messageClass: Class<T>,
        private val deadLetterProducer: IQueueDeadLetterProducer<Message>,
        private val rabbitConnectionProvider: RabbitMqConnectionProvider,
        private val messageConsumerFactory: MessageConsumerFactory
) : IQueueConsumer {

    override fun subscribe() {
        val connection = rabbitConnectionProvider.getConnection()
        val channel = connection.createChannel()

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

        val consumer = messageConsumerFactory.getMessageConsumer(
                channel!!,
                messageClass,
                deadLetterProducer,
                rabbitConsumerConfiguration.maxRetries
        )

        channel.basicConsume(assignedQueueName, false, consumer)
    }
}