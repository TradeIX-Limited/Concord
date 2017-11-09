package com.tradeix.concord.services.messaging

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Consumer
import com.tradeix.concord.interfaces.IQueueDeadLetterProducer
import com.tradeix.concord.messages.Message
import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage
import com.tradeix.concord.messages.TransactionResponseMessage
import net.corda.core.messaging.CordaRPCOps

class MessageConsumerFactory(
        val services: CordaRPCOps,
        private val responderConfigurations: Map<String, RabbitProducerConfiguration>,
        private val rabbitConnectionProvider: RabbitMqConnectionProvider) {

    fun <T : Message> getMessageConsumer(
            channel: Channel,
            type: Class<T>,
            deadLetterProducer: IQueueDeadLetterProducer<Message>,
            maxRetries: Int): Consumer {
        return when {
            type.isAssignableFrom(TradeAssetIssuanceRequestMessage::class.java) -> {
                val responder = RabbitMqProducer<TransactionResponseMessage>(
                        responderConfigurations["cordatix_response"]!!,
                        rabbitConnectionProvider
                )

                IssuanceMessageConsumer(services, channel, deadLetterProducer, maxRetries, responder)
            }
            else -> throw ClassNotFoundException()
        }
    }
}