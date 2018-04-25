package com.tradeix.concord.services.messaging

import com.nhaarman.mockito_kotlin.mock
import com.rabbitmq.client.Channel
import com.rabbitmq.client.ConnectionFactory
import com.tradeix.concord.interfaces.IQueueDeadLetterProducer
import com.tradeix.concord.messages.rabbit.RabbitMessage
import com.tradeix.concord.messages.rabbit.purchaseorder.PurchaseOrderIssuanceRequestMessage
import com.tradeix.concord.messages.rabbit.purchaseorder.PurchaseOrderOwnershipRequestMessage
import net.corda.core.messaging.CordaRPCOps
import org.junit.Test

class MessageConsumerFactoryTest {
    @Test
    fun `Retrieve IssuanceMessageConsumer`() {
        val producerMock = mock<IQueueDeadLetterProducer<RabbitMessage>>()
        val cordaRpcServices = mock<CordaRPCOps>()
        val producerConfiguration = RabbitProducerConfiguration("abc", "topic", "def", false, true, emptyMap())
        val responseConfiguration = mapOf("cordatix_issuance_response" to producerConfiguration)
        val mockConnectionFactory = mock<ConnectionFactory>()
        val mockChannel = mock<Channel>()

        val messageConsumerFactory = MessageConsumerFactory(
                services = cordaRpcServices,
                responderConfigurations = responseConfiguration,
                rabbitConnectionProvider = RabbitMqConnectionProvider(mockConnectionFactory)
        )

        val consumer = messageConsumerFactory.getMessageConsumer(
                channel = mockChannel,
                type = PurchaseOrderIssuanceRequestMessage::class.java,
                deadLetterProducer = producerMock,
                maxRetries = 1
        )

        assert(consumer is PurchaseOrderIssuanceMessageConsumer)
    }

    @Test
    fun `Retrieve ChangeOwnerMessageConsumer`() {
        val producerMock = mock<IQueueDeadLetterProducer<RabbitMessage>>()
        val cordaRpcServices = mock<CordaRPCOps>()
        val producerConfiguration = RabbitProducerConfiguration("abc", "topic", "def", false, true, emptyMap())
        val responseConfiguration = mapOf("cordatix_changeowner_response" to producerConfiguration)
        val mockConnectionFactory = mock<ConnectionFactory>()
        val mockChannel = mock<Channel>()

        val messageConsumerFactory = MessageConsumerFactory(
                services = cordaRpcServices,
                responderConfigurations = responseConfiguration,
                rabbitConnectionProvider = RabbitMqConnectionProvider(mockConnectionFactory)
        )

        val consumer = messageConsumerFactory.getMessageConsumer(
                channel = mockChannel,
                type = PurchaseOrderOwnershipRequestMessage::class.java,
                deadLetterProducer = producerMock,
                maxRetries = 1
        )

        assert(consumer is ChangeOwnerMessageConsumer)
    }
}