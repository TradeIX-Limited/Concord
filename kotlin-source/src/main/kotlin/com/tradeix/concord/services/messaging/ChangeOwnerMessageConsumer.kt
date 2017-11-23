package com.tradeix.concord.services.messaging

import com.google.gson.Gson
import com.rabbitmq.client.*
import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.flows.TradeAssetOwnership
import com.tradeix.concord.interfaces.IQueueDeadLetterProducer
import com.tradeix.concord.messages.rabbit.RabbitMessage
import com.tradeix.concord.messages.rabbit.RabbitResponseMessage
import com.tradeix.concord.messages.rabbit.tradeasset.TradeAssetOwnershipRequestMessage
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.messaging.startTrackedFlow
import net.corda.core.utilities.getOrThrow
import java.nio.charset.Charset

class ChangeOwnerMessageConsumer(
        val services: CordaRPCOps,
        private val channel: Channel,
        private val deadLetterProducer: IQueueDeadLetterProducer<RabbitMessage>,
        private val maxRetryCount: Int,
        private val responder: RabbitMqProducer<RabbitResponseMessage>,
        private val serializer: Gson
) : Consumer {

    override fun handleRecoverOk(consumerTag: String?) {
        println("ChangeOwnerMessageConsumer: handleRecoverOk for consumer tag: $consumerTag")
    }

    override fun handleConsumeOk(consumerTag: String?) {
        println("ChangeOwnerMessageConsumer: handleConsumeOk for consumer tag: $consumerTag")
    }

    override fun handleShutdownSignal(consumerTag: String?, sig: ShutdownSignalException?) {
        println("ChangeOwnerMessageConsumer: handleShutdownSignal for consumer tag: $consumerTag")
        println(sig)
    }

    override fun handleCancel(consumerTag: String?) {
        println("ChangeOwnerMessageConsumer: handleCancel for consumer tag: $consumerTag")
    }

    override fun handleDelivery(
            consumerTag: String?,
            envelope: Envelope?,
            properties: AMQP.BasicProperties?,
            body: ByteArray?) {
        // val deliveryTag = envelope?.deliveryTag

        // Handler logic here
        val messageBody = body?.toString(Charset.defaultCharset())
        println("Received message $messageBody")

        var requestMessage = TradeAssetOwnershipRequestMessage(
                correlationId = null,
                tryCount = 0,
                externalIds = null,
                newOwner = null)

        try {
            requestMessage = serializer.fromJson(messageBody, TradeAssetOwnershipRequestMessage::class.java)
            println("Received message with id ${requestMessage.correlationId} in ChangeOwnerMessageConsumer - about to process.")
            try {
                val flowHandle = services.startTrackedFlow(TradeAssetOwnership::InitiatorFlow, requestMessage.toModel())
                flowHandle.progress.subscribe { println(">> $it") }
                val result = flowHandle.returnValue.getOrThrow()


                println("Successfully processed OwnershipRequest - responding back to client")
                val response = RabbitResponseMessage(
                        correlationId = requestMessage.correlationId!!,
                        transactionId = result.id.toString(),
                        errorMessages = null,
                        externalIds = requestMessage.externalIds!!,
                        success = true
                )
                responder.publish(response)
            } catch (ex: Throwable){
                return when (ex){
                    is FlowValidationException -> {
                        println("Flow validation exception occurred, sending failed response")
                        val response = RabbitResponseMessage(
                                correlationId = requestMessage.correlationId!!,
                                transactionId = null,
                                errorMessages = ex.validationErrors,
                                externalIds = requestMessage.externalIds!!,
                                success = false
                        )

                        responder.publish(response)
                    }
                    else -> {
                        val response = RabbitResponseMessage(
                                correlationId = requestMessage.correlationId!!,
                                transactionId = null,
                                errorMessages = listOf(ex.message!!),
                                externalIds = requestMessage.externalIds!!,
                                success = false
                        )

                        responder.publish(response)
                    }
                }

            }
        } catch (ex: Throwable) {
            requestMessage.tryCount++
            if (requestMessage.tryCount < maxRetryCount) {
                println("Exception handled in ChangeOwnerMessageConsumer, writing to dlq")
                deadLetterProducer.publish(requestMessage, false)
            } else {
                println("Exception handled in ChangeOwnerMessageConsumer, writing to dlq fatally")
                deadLetterProducer.publish(requestMessage, true)
            }
        }
    }

    override fun handleCancelOk(consumerTag: String?) {
        println("ChangeOwnerMessageConsumer: handleCancelOk for consumer tag: $consumerTag")
    }
}