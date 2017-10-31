package com.tradeix.concord.services.messaging

import com.rabbitmq.client.ConnectionFactory
import com.tradeix.concord.messages.Message
import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage

class RabbitMqConsumer<T : Message>(private val rabbitConfiguration: RabbitConfiguration, private val messageClass: Class<T>) {
    init {
        //rabbitConfiguration = RabbitConfiguration("cordatix_exchange", "topic", ex)
    }

    fun subscribe(){
        val connection = ConnectionFactory().newConnection()
        val channel = connection?.createChannel()

        if(channel != null) {
            channel.exchangeDeclare(
                    rabbitConfiguration.exchangeName,
                    rabbitConfiguration.exchangeType,
                    rabbitConfiguration.durableExchange,
                    rabbitConfiguration.autoDeleteExchange,
                    rabbitConfiguration.exchangeArguments)

            val queueDeclare = channel.queueDeclare(
                    rabbitConfiguration.queueName,
                    rabbitConfiguration.durableQueue,
                    rabbitConfiguration.exclusiveQueue,
                    rabbitConfiguration.autoDeleteQueue,
                    rabbitConfiguration.queueArguments)

            if(queueDeclare != null) {
                val assignedQueueName = queueDeclare.queue

                channel.queueBind(
                        assignedQueueName,
                        rabbitConfiguration.exchangeName,
                        rabbitConfiguration.exchangeRoutingKey)

                val consumer = MessageConsumerFactory.getMessageConsumer(channel!!, messageClass)

                channel.basicConsume(assignedQueueName, true, consumer)

                // TODO : Continue where we left off...
            } else {
                // TODO : What if there's no queueDeclare?
            }
        } else {
            // TODO : What if there's no channel?
        }
    }

    // Usage example...
    fun foo() {
        val rc = RabbitConfiguration()
        RabbitMqConsumer(rc, TradeAssetIssuanceRequestMessage::class.java)
    }
}