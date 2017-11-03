package com.tradeix.concord.services.messaging

import com.nhaarman.mockito_kotlin.mock
import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory
import com.tradeix.concord.interfaces.IQueueDeadLetterProducer
import com.tradeix.concord.messages.Message
import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage
import org.junit.Test

class MessageConsumerFactoryTest{
    @Test
    fun `Retrieve IssuanceMessageConsumer`() {
        val producerMock = mock<IQueueDeadLetterProducer<Message>>()
        val channel: Channel = ConnectionFactory().newConnection().createChannel()
        val consumer = MessageConsumerFactory.getMessageConsumer(channel, TradeAssetIssuanceRequestMessage::class.java, producerMock, 1)
        assert(consumer is IssuanceMessageConsumer)
    }
}