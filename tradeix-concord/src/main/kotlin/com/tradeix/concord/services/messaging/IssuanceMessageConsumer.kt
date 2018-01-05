package com.tradeix.concord.services.messaging

import com.google.gson.Gson
import com.rabbitmq.client.*
import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.flows.tradeasset.TradeAssetIssuance
import com.tradeix.concord.interfaces.IQueueDeadLetterProducer
import com.tradeix.concord.messages.rabbit.RabbitMessage
import com.tradeix.concord.messages.rabbit.tradeasset.TradeAssetIssuanceRequestMessage
import com.tradeix.concord.messages.rabbit.tradeasset.TradeAssetResponseMessage
import com.tradeix.concord.validators.RabbitRequestMessageValidator
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger
import net.corda.core.identity.CordaX500Name
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.messaging.startTrackedFlow
import net.corda.core.utilities.getOrThrow
import java.nio.charset.Charset

class IssuanceMessageConsumer(
        val services: CordaRPCOps,
        private val channel: Channel,
        private val deadLetterProducer: IQueueDeadLetterProducer<RabbitMessage>,
        private val maxRetryCount: Int,
        private val responder: RabbitMqProducer<TradeAssetResponseMessage>,
        private val serializer: Gson
) : Consumer {

    companion object {
        protected val log: Logger = loggerFor<IssuanceMessageConsumer>()
    }

    override fun handleRecoverOk(consumerTag: String?) {
        println("IssuanceMessageConsumer: handleRecoverOk for consumer tag: $consumerTag")
    }

    override fun handleConsumeOk(consumerTag: String?) {
        println("IssuanceMessageConsumer: handleConsumeOk for consumer tag: $consumerTag")
    }

    override fun handleShutdownSignal(consumerTag: String?, sig: ShutdownSignalException?) {
        println("IssuanceMessageConsumer: handleShutdownSignal for consumer tag: $consumerTag")
        println(sig)
    }

    override fun handleCancel(consumerTag: String?) {
        println("IssuanceMessageConsumer: handleCancel for consumer tag: $consumerTag")
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

        var requestMessage = TradeAssetIssuanceRequestMessage(
                correlationId = null,
                tryCount = 0,
                externalId = null,
                buyer = null,
                supplier = null,
                conductor = CordaX500Name("TradeIX", "London", "GB"),
                status = null,
                value = null,
                currency = null,
                attachmentId = null)

        try {
            requestMessage = serializer.fromJson(messageBody, TradeAssetIssuanceRequestMessage::class.java)
            println("Received message with id ${requestMessage.correlationId} in IssuanceMessageConsumer - about to process.")
            log.info("Received message with id ${requestMessage.correlationId} in IssuanceMessageConsumer - about to process.")

            try {
                val validator = RabbitRequestMessageValidator(requestMessage)

                if (!validator.isValid) {
                    throw FlowValidationException(validationErrors = validator.validationErrors)
                }

                val flowHandle = services.startTrackedFlow(TradeAssetIssuance::InitiatorFlow, requestMessage.toModel())
                flowHandle.progress.subscribe { println(">> $it") }
                val result = flowHandle.returnValue.getOrThrow()


                println("Successfully processed IssuanceRequest - responding back to client")
                val response = TradeAssetResponseMessage(
                        correlationId = requestMessage.correlationId!!,
                        transactionId = result.id.toString(),
                        errorMessages = null,
                        externalIds = listOf(requestMessage.externalId!!),
                        success = true
                )
                responder.publish(response)
                log.info("Successfully processed IssuanceRequest - responded back to client")
            } catch (ex: Throwable){
                log.error("Failed to process the message ${ex.message}, Returning a error response")
                return when (ex){
                    is FlowValidationException -> {
                        println("Flow validation exception occurred, sending failed response")
                        val response = TradeAssetResponseMessage(
                                correlationId = requestMessage.correlationId!!,
                                transactionId = null,
                                errorMessages = ex.validationErrors,
                                externalIds = listOf(requestMessage.externalId!!),
                                success = false
                        )

                        responder.publish(response)
                    }
                    else -> {
                        val response = TradeAssetResponseMessage(
                                correlationId = requestMessage.correlationId!!,
                                transactionId = null,
                                errorMessages = listOf(ex.message!!),
                                externalIds = listOf(requestMessage.externalId!!),
                                success = false
                        )

                        responder.publish(response)
                    }
                }

            }
        } catch (ex: Throwable) {
            requestMessage.tryCount++
            if (requestMessage.tryCount < maxRetryCount) {
                println("Exception handled in IssuanceMessageConsumer, writing to dlq")
                log.error("Exception handled in IssuanceMessageConsumer, writing to dlq")

                deadLetterProducer.publish(requestMessage, false)
            } else {
                println("Exception handled in IssuanceMessageConsumer, writing to dlq fatally")
                log.error("Exception handled in IssuanceMessageConsumer, writing to dlq fatally")
                deadLetterProducer.publish(requestMessage, true)
            }
        }
    }

    override fun handleCancelOk(consumerTag: String?) {
        println("IssuanceMessageConsumer: handleCancelOk for consumer tag: $consumerTag")
    }
}