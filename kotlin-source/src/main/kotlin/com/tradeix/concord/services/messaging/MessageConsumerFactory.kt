package com.tradeix.concord.services.messaging

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Consumer
import com.tradeix.concord.interfaces.IQueueProducer
import com.tradeix.concord.messages.Message
import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage

object MessageConsumerFactory{
    fun <T : Message> getMessageConsumer(channel: Channel, type:Class<T>, deadLetterProducer: IQueueProducer<Message>, maxRetries: Int): Consumer {
        return when {
            type.isAssignableFrom(TradeAssetIssuanceRequestMessage::class.java) -> IssuanceMessageConsumer(channel, deadLetterProducer, maxRetries)
            else -> throw ClassNotFoundException()
        }
    }
}