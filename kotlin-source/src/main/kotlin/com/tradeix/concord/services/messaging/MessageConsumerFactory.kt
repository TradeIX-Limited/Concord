package com.tradeix.concord.services.messaging

import com.rabbitmq.client.Channel
import com.rabbitmq.client.Consumer
import com.tradeix.concord.messages.Message
import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage

object MessageConsumerFactory{
    fun <T : Message> getMessageConsumer(channel: Channel, type:Class<T>): Consumer {
        return when {
            type.isAssignableFrom(TradeAssetIssuanceRequestMessage::class.java) -> IssuanceMessageConsumer(channel)
            else -> throw ClassNotFoundException()
        }
    }
}