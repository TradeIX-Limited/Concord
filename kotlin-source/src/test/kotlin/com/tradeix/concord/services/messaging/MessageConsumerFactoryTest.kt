package com.tradeix.concord.services.messaging

import com.nhaarman.mockito_kotlin.mock
import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory
import com.tradeix.concord.interfaces.IQueueDeadLetterProducer
import com.tradeix.concord.messages.Message
import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage
import net.corda.core.messaging.CordaRPCOps
import org.junit.Test

class MessageConsumerFactoryTest{
    @Test
    fun `Retrieve IssuanceMessageConsumer`() {
        val producerMock = mock<IQueueDeadLetterProducer<Message>>()
        val cordaRpcServices = mock< CordaRPCOps>()
        val producerConfiguration = RabbitProducerConfiguration("abc", "topic", "def", false, true, emptyMap())
        val responseConfiguration = mapOf("cordatix_response" to producerConfiguration)
        val mockConnectionFactory = mock<ConnectionFactory>()
        val mockChannel = mock<Channel>()
        val messageConsumerFactory = MessageConsumerFactory(cordaRpcServices, responseConfiguration, RabbitMqConnectionProvider(mockConnectionFactory))
        val consumer = messageConsumerFactory.getMessageConsumer(mockChannel, TradeAssetIssuanceRequestMessage::class.java, producerMock, 1)
        assert(consumer is IssuanceMessageConsumer)
    }
}