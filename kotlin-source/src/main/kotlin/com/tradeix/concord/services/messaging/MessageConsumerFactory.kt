package com.tradeix.concord.services.messaging

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Consumer
import com.tradeix.concord.interfaces.IQueueDeadLetterProducer
import com.tradeix.concord.interfaces.IQueueProducer
import com.tradeix.concord.messages.Message
import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage
import net.corda.core.messaging.CordaRPCOps

class MessageConsumerFactory(val services: CordaRPCOps){
    fun <T : Message> getMessageConsumer(channel: Channel, type:Class<T>, deadLetterProducer: IQueueDeadLetterProducer<Message>, maxRetries: Int): Consumer {
        return when {
            type.isAssignableFrom(TradeAssetIssuanceRequestMessage::class.java) -> IssuanceMessageConsumer(channel, deadLetterProducer, maxRetries, services)
            else -> throw ClassNotFoundException()
        }
    }
}