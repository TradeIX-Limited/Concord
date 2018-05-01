package com.tradeix.concord.services.messaging.consumers

import com.google.gson.Gson
import com.rabbitmq.client.*
import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.flows.invoice.InvoiceOwnership
import com.tradeix.concord.interfaces.IQueueDeadLetterProducer
import com.tradeix.concord.messages.rabbit.RabbitMessage
import com.tradeix.concord.messages.rabbit.invoice.InvoiceOwnershipRequestMessage
import com.tradeix.concord.messages.rabbit.invoice.InvoiceResponseMessage
import com.tradeix.concord.services.messaging.RabbitMqProducer
import com.tradeix.concord.validators.RabbitRequestMessageValidator
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.messaging.startTrackedFlow
import net.corda.core.utilities.getOrThrow
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger
import java.nio.charset.Charset

class InvoiceChangeOwnerMessageConsumer(
        val services: CordaRPCOps,
        private val channel: Channel,
        private val deadLetterProducer: IQueueDeadLetterProducer<RabbitMessage>,
        private val maxRetryCount: Int,
        private val responder: RabbitMqProducer<InvoiceResponseMessage>,
        private val serializer: Gson
) : Consumer {

    companion object {
        protected val log: Logger = loggerFor<InvoiceChangeOwnerMessageConsumer>()
    }

    override fun handleRecoverOk(consumerTag: String?) {
        println("InvoiceChangeOwnerMessageConsumer: handleRecoverOk for consumer tag: $consumerTag")
    }

    override fun handleConsumeOk(consumerTag: String?) {
        println("InvoiceChangeOwnerMessageConsumer: handleConsumeOk for consumer tag: $consumerTag")
    }

    override fun handleShutdownSignal(consumerTag: String?, sig: ShutdownSignalException?) {
        println("InvoiceChangeOwnerMessageConsumer: handleShutdownSignal for consumer tag: $consumerTag")
        println(sig)
    }

    override fun handleCancel(consumerTag: String?) {
        println("InvoiceChangeOwnerMessageConsumer: handleCancel for consumer tag: $consumerTag")
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

        var requestMessage = InvoiceOwnershipRequestMessage(
                correlationId = null,
                tryCount = 0,
                externalId = null,
                newOwner = null)

        try {
            requestMessage = serializer.fromJson(messageBody, InvoiceOwnershipRequestMessage::class.java)
            println("Received message with id ${requestMessage.correlationId} in InvoiceChangeOwnerMessageConsumer - about to process.")
            log.info("Received message with id ${requestMessage.correlationId} in InvoiceChangeOwnerMessageConsumer - about to process.")
            try {
                val validator = RabbitRequestMessageValidator(requestMessage)

                if (!validator.isValid) {
                    throw FlowValidationException(validationErrors = validator.validationErrors)
                }
                val flowHandle = services.startTrackedFlow(InvoiceOwnership::InitiatorFlow, requestMessage.toModel())
                flowHandle.progress.subscribe { println(">> $it") }
                val result = flowHandle.returnValue.getOrThrow()


                println("Successfully processed OwnershipRequest - responding back to client")
                val response = InvoiceResponseMessage(
                        correlationId = requestMessage.correlationId!!,
                        transactionId = result.id.toString(),
                        errorMessages = null,
                        externalIds = listOf(requestMessage.externalId!!),
                        success = true
                )
                responder.publish(response)
                log.info("Successfully processed OwnershipRequest - responded back to client")
            } catch (ex: Throwable) {
                log.error("Failed to process the message ${ex}, Returning a error response")
                return when (ex) {
                    is FlowValidationException -> {
                        println("Flow validation exception occurred, sending failed response")
                        val response = InvoiceResponseMessage(
                                correlationId = requestMessage.correlationId!!,
                                transactionId = null,
                                errorMessages = ex.validationErrors,
                                externalIds = listOf(requestMessage.externalId!!),
                                success = false
                        )

                        responder.publish(response)
                    }
                    else -> {
                        val response = InvoiceResponseMessage(
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
                println("Exception handled in InvoiceChangeOwnerMessageConsumer, writing to dlq")
                log.error("Exception handled in InvoiceChangeOwnerMessageConsumer, writing to dlq")
                deadLetterProducer.publish(requestMessage, false)
            } else {
                println("Exception handled in InvoiceChangeOwnerMessageConsumer, writing to dlq fatally")
                log.error("Exception handled in InvoiceChangeOwnerMessageConsumer, writing to dlq fatally")
                deadLetterProducer.publish(requestMessage, true)
            }
        }
    }

    override fun handleCancelOk(consumerTag: String?) {
        println("InvoiceChangeOwnerMessageConsumer: handleCancelOk for consumer tag: $consumerTag")
    }
}