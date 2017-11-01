package com.tradeix.concord.services.messaging

import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory
import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage
import org.junit.Test

class MessageConsumerFactoryTest{
    @Test
    fun `Retrieve IssuanceMessageConsumer`() {
        val channel: Channel = ConnectionFactory().newConnection().createChannel()
        val consumer = MessageConsumerFactory.getMessageConsumer(channel, TradeAssetIssuanceRequestMessage::class.java)
        assert(consumer is IssuanceMessageConsumer)
    }
}