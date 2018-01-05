package com.tradeix.concord.services.messaging

import com.google.gson.GsonBuilder
import com.nhaarman.mockito_kotlin.*
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Envelope
import com.tradeix.concord.flows.tradeasset.TradeAssetOwnership
import com.tradeix.concord.interfaces.IQueueDeadLetterProducer
import com.tradeix.concord.messages.rabbit.RabbitMessage
import com.tradeix.concord.messages.rabbit.tradeasset.TradeAssetOwnershipRequestMessage
import com.tradeix.concord.messages.rabbit.tradeasset.TradeAssetOwnershipResponseMessage
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

class ChangeOwnerMessageConsumerTest {

    @Test
    fun `Handle Delivery Successful Response`() {
        val request = TradeAssetOwnershipRequestMessage(
                correlationId = "corr1",
                tryCount = 1,
                externalIds = listOf("ext1"),
                newOwner = null,
                bidUniqueId = "uniqueId")

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
        val mockResponder = mock<RabbitMqProducer<TradeAssetOwnershipResponseMessage>>()
        val mockDeadLetterProducer = mock<IQueueDeadLetterProducer<RabbitMessage>>()
        val mockChannel = mock<Channel>()
        val mockEnvelope = mock<Envelope>()

        whenever(mockCordaRPCOps.startTrackedFlow(TradeAssetOwnership::InitiatorFlow, request.toModel())).thenReturn(mockFlowHandle)
        whenever(mockFlowHandle.progress).thenReturn(mockFlowObservable)
        whenever(mockFlowHandle.returnValue).thenReturn(mockCordaFuture)
        whenever(mockCordaFuture.get()).thenReturn(mockSignedTransaction)
        whenever(mockSignedTransaction.id).thenReturn(mockSecureHash)
        whenever(mockSecureHash.toString()).thenReturn("abc")

        val issuanceConsumer = ChangeOwnerMessageConsumer(mockCordaRPCOps, mockChannel, mockDeadLetterProducer, 3, mockResponder, serializer)
        issuanceConsumer.handleDelivery("abc", mockEnvelope, null, requestBytes)

        verify(mockResponder, times(1)).publish(any<TradeAssetOwnershipResponseMessage>())
        verify(mockResponder).publish(argForWhich { externalIds?.last() == "ext1" })
        verify(mockResponder).publish(argForWhich { correlationId == "corr1" })
        verify(mockResponder).publish(argForWhich { transactionId!! == "abc" })
        verify(mockResponder).publish(argForWhich { success })
        verify(mockResponder).publish(argForWhich { errorMessages == null })
        verify(mockResponder).publish(argForWhich { bidUniqueId == "uniqueId" })
    }

    @Test
    fun `Handle serialization error within retry limit`(){
        val requestString = "{\"correlationId\":\"corr1\",\"exxxxxxxxx\":[\"ext1\"],\"tryCount\":1}"
        val requestBytes = requestString.toByteArray()
        val mockCordaRPCOps = mock<CordaRPCOps>()
        val mockResponder = mock<RabbitMqProducer<TradeAssetOwnershipResponseMessage>>()
        val mockDeadLetterProducer = mock<IQueueDeadLetterProducer<RabbitMessage>>()
        val mockChannel = mock<Channel>()
        val mockEnvelope = mock<Envelope>()

        val serializer = GsonBuilder()
                .registerTypeAdapter(CordaX500Name::class.java, CordaX500NameSerializer())
                .disableHtmlEscaping()
                .create()

        val issuanceConsumer = ChangeOwnerMessageConsumer(mockCordaRPCOps, mockChannel, mockDeadLetterProducer, 3, mockResponder, serializer)
        issuanceConsumer.handleDelivery("abc", mockEnvelope, null, requestBytes)

        verify(mockDeadLetterProducer, times(1)).publish(any<TradeAssetOwnershipRequestMessage>(), any<Boolean>())
    }

    @Test
    fun `Handle serialization error outside retry limit`(){
        val requestString = "{\"corrrrrrr\":\"corr1\",\"externalIds\":[\"ext1\"],\"tryCount\":4}"
        val requestBytes = requestString.toByteArray()
        val mockCordaRPCOps = mock<CordaRPCOps>()
        val mockResponder = mock<RabbitMqProducer<TradeAssetOwnershipResponseMessage>>()
        val mockDeadLetterProducer = mock<IQueueDeadLetterProducer<RabbitMessage>>()
        val mockChannel = mock<Channel>()
        val mockEnvelope = mock<Envelope>()
        val serializer = GsonBuilder()
                .registerTypeAdapter(CordaX500Name::class.java, CordaX500NameSerializer())
                .disableHtmlEscaping()
                .create()


        val issuanceConsumer = ChangeOwnerMessageConsumer(mockCordaRPCOps, mockChannel, mockDeadLetterProducer, 3, mockResponder, serializer)
        issuanceConsumer.handleDelivery("abc", mockEnvelope, null, requestBytes)

        verify(mockDeadLetterProducer, times(1)).publish(any<TradeAssetOwnershipRequestMessage>(), any<Boolean>())
    }
}