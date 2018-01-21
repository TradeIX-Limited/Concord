package com.tradeix.concord.services.messaging

import com.google.gson.GsonBuilder
import com.nhaarman.mockito_kotlin.*
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Envelope
import com.tradeix.concord.flows.tradeasset.TradeAssetIssuance
import com.tradeix.concord.interfaces.IQueueDeadLetterProducer
import com.tradeix.concord.messages.rabbit.RabbitMessage
import com.tradeix.concord.messages.rabbit.tradeasset.TradeAssetIssuanceRequestMessage
import com.tradeix.concord.messages.rabbit.tradeasset.TradeAssetResponseMessage
import com.tradeix.concord.serialization.CordaX500NameSerializer
import net.corda.core.concurrent.CordaFuture
import net.corda.core.crypto.SecureHash
import net.corda.core.identity.CordaX500Name
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.messaging.FlowProgressHandle
import net.corda.core.messaging.startTrackedFlow
import net.corda.core.transactions.SignedTransaction
import org.junit.Test
import rx.Observable

class IssuanceMessageConsumerTest {

    @Test
    fun `Handle Delivery Successful Response`() {
        val request = TradeAssetIssuanceRequestMessage(
                correlationId = "corr1",
                tryCount = 1,
                externalId = "ext1",
                buyer = null,
                supplier = null,
                conductor = CordaX500Name("TradeIX", "London", "GB"),
                status = null,
                value = null,
                currency = null,
                attachmentId = null)

        val serializer = GsonBuilder()
                .registerTypeAdapter(CordaX500Name::class.java, CordaX500NameSerializer())
                .disableHtmlEscaping()
                .create()

        val requestString = serializer.toJson(request)
        val requestBytes = requestString.toByteArray()
        val mockCordaRPCOps = mock<CordaRPCOps>()
        val mockFlowHandle = mock<FlowProgressHandle<SignedTransaction>>()
        val mockFlowObservable = mock<Observable<String>>()
        val mockCordaFuture = mock<CordaFuture<SignedTransaction>>()
        val mockSignedTransaction = mock<SignedTransaction>() //SignedTransaction(mockCoreTransaction, mockTransactionSignature)
        val mockSecureHash = mock<SecureHash>()
        val mockResponder = mock<RabbitMqProducer<TradeAssetResponseMessage>>()
        val mockDeadLetterProducer = mock<IQueueDeadLetterProducer<RabbitMessage>>()
        val mockChannel = mock<Channel>()
        val mockEnvelope = mock<Envelope>()

        whenever(mockCordaRPCOps.startTrackedFlow(TradeAssetIssuance::InitiatorFlow, request.toModel())).thenReturn(mockFlowHandle)
        whenever(mockFlowHandle.progress).thenReturn(mockFlowObservable)
        whenever(mockFlowHandle.returnValue).thenReturn(mockCordaFuture)
        whenever(mockCordaFuture.get()).thenReturn(mockSignedTransaction)
        whenever(mockSignedTransaction.id).thenReturn(mockSecureHash)
        whenever(mockSecureHash.toString()).thenReturn("abc")

        val issuanceConsumer = IssuanceMessageConsumer(mockCordaRPCOps, mockChannel, mockDeadLetterProducer, 3, mockResponder, serializer)
        issuanceConsumer.handleDelivery("abc", mockEnvelope, null, requestBytes)

        verify(mockResponder, times(1)).publish(any<TradeAssetResponseMessage>())
        verify(mockResponder).publish(argForWhich { externalIds?.last() == "ext1" })
        verify(mockResponder).publish(argForWhich { correlationId == "corr1" })
        verify(mockResponder).publish(argForWhich { transactionId!! == "abc" })
        verify(mockResponder).publish(argForWhich { success })
        verify(mockResponder).publish(argForWhich { errorMessages == null })
    }

    @Test
    fun `Handle serialization error within retry limit`() {
        TradeAssetIssuanceRequestMessage(
                correlationId = "corr1",
                tryCount = 1,
                externalId = "ext1",
                buyer = null,
                supplier = null,
                conductor = CordaX500Name("TradeIX", "London", "GB"),
                status = null,
                value = null,
                currency = null,
                attachmentId = null)

        val serializer = GsonBuilder()
                .registerTypeAdapter(CordaX500Name::class.java, CordaX500NameSerializer())
                .disableHtmlEscaping()
                .create()

        val requestString = "{\"blah\":\"corr1\",\"tryCount\":4,\"externalId\":\"ext1\",\"conductor\":\"C=GB,L=London,O=TradeIX\"}"
        val requestBytes = requestString.toByteArray()
        val mockCordaRPCOps = mock<CordaRPCOps>()
        val mockResponder = mock<RabbitMqProducer<TradeAssetResponseMessage>>()
        val mockDeadLetterProducer = mock<IQueueDeadLetterProducer<RabbitMessage>>()
        val mockChannel = mock<Channel>()
        val mockEnvelope = mock<Envelope>()

        val issuanceConsumer = IssuanceMessageConsumer(mockCordaRPCOps, mockChannel, mockDeadLetterProducer, 3, mockResponder, serializer)
        issuanceConsumer.handleDelivery("abc", mockEnvelope, null, requestBytes)

        verify(mockDeadLetterProducer, times(1)).publish(any<TradeAssetIssuanceRequestMessage>(), any())
    }

    @Test
    fun `Handle serialization error outside retry limit`() {
        TradeAssetIssuanceRequestMessage(
                correlationId = "corr1",
                tryCount = 4,
                externalId = "ext1",
                buyer = null,
                supplier = null,
                conductor = CordaX500Name("TradeIX", "London", "GB"),
                status = null,
                value = null,
                currency = null,
                attachmentId = null)

        val serializer = GsonBuilder()
                .registerTypeAdapter(CordaX500Name::class.java, CordaX500NameSerializer())
                .disableHtmlEscaping()
                .create()

        val requestString = "{\"blah\":\"corr1\",\"tryCount\":4,\"externalId\":\"ext1\",\"conductor\":\"C=GB,L=London,O=TradeIX\"}"
        val requestBytes = requestString.toByteArray()
        val mockCordaRPCOps = mock<CordaRPCOps>()
        val mockResponder = mock<RabbitMqProducer<TradeAssetResponseMessage>>()
        val mockDeadLetterProducer = mock<IQueueDeadLetterProducer<RabbitMessage>>()
        val mockChannel = mock<Channel>()
        val mockEnvelope = mock<Envelope>()


        val issuanceConsumer = IssuanceMessageConsumer(mockCordaRPCOps, mockChannel, mockDeadLetterProducer, 3, mockResponder, serializer)
        issuanceConsumer.handleDelivery("abc", mockEnvelope, null, requestBytes)

        verify(mockDeadLetterProducer, times(1)).publish(any<TradeAssetIssuanceRequestMessage>(), any<Boolean>())
    }
}