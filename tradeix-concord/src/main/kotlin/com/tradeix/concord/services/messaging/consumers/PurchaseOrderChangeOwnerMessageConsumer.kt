package com.tradeix.concord.services.messaging.consumers

import com.google.gson.Gson
import com.rabbitmq.client.*
import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.flows.purchaseorder.PurchaseOrderOwnership
import com.tradeix.concord.interfaces.IQueueDeadLetterProducer
import com.tradeix.concord.messages.rabbit.RabbitMessage
import com.tradeix.concord.messages.rabbit.purchaseorder.PurchaseOrderOwnershipRequestMessage
import com.tradeix.concord.messages.rabbit.purchaseorder.PurchaseOrderResponseMessage
import com.tradeix.concord.services.messaging.RabbitMqProducer
import com.tradeix.concord.validators.RabbitRequestMessageValidator
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.messaging.startTrackedFlow
import net.corda.core.utilities.getOrThrow
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger
import java.nio.charset.Charset

class PurchaseOrderChangeOwnerMessageConsumer(
        val services: CordaRPCOps,
        private val channel: Channel,
        private val deadLetterProducer: IQueueDeadLetterProducer<RabbitMessage>,
        private val maxRetryCount: Int,
        private val responder: RabbitMqProducer<PurchaseOrderResponseMessage>,
        private val serializer: Gson
) : Consumer {

    companion object {
        protected val log: Logger = loggerFor<PurchaseOrderChangeOwnerMessageConsumer>()
    }

    override fun handleRecoverOk(consumerTag: String?) {
        println("PurchaseOrderChangeOwnerMessageConsumer: handleRecoverOk for consumer tag: $consumerTag")
    }

    override fun handleConsumeOk(consumerTag: String?) {
        println("PurchaseOrderChangeOwnerMessageConsumer: handleConsumeOk for consumer tag: $consumerTag")
    }

    override fun handleShutdownSignal(consumerTag: String?, sig: ShutdownSignalException?) {
        println("PurchaseOrderChangeOwnerMessageConsumer: handleShutdownSignal for consumer tag: $consumerTag")
        println(sig)
    }

    override fun handleCancel(consumerTag: String?) {
        println("PurchaseOrderChangeOwnerMessageConsumer: handleCancel for consumer tag: $consumerTag")
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

        var requestMessage = PurchaseOrderOwnershipRequestMessage(
                correlationId = null,
                tryCount = 0,
                externalIds = null,
                newOwner = null,
                bidUniqueId = null)

        try {
            requestMessage = serializer.fromJson(messageBody, PurchaseOrderOwnershipRequestMessage::class.java)
            println("Received message with id ${requestMessage.correlationId} in PurchaseOrderChangeOwnerMessageConsumer - about to process.")
            log.info("Received message with id ${requestMessage.correlationId} in PurchaseOrderChangeOwnerMessageConsumer - about to process.")
            try {
                val validator = RabbitRequestMessageValidator(requestMessage)

                if (!validator.isValid) {
                    throw FlowValidationException(validationErrors = validator.validationErrors)
                }
                val flowHandle = services.startTrackedFlow(PurchaseOrderOwnership::InitiatorFlow, requestMessage.toModel())
                flowHandle.progress.subscribe { println(">> $it") }
                val result = flowHandle.returnValue.getOrThrow()


                println("Successfully processed OwnershipRequest - responding back to client")
                val response = PurchaseOrderResponseMessage(
                        correlationId = requestMessage.correlationId!!,
                        transactionId = result.id.toString(),
                        errorMessages = null,
                        externalIds = requestMessage.externalIds!!,
                        success = true,
                        bidUniqueId = requestMessage.bidUniqueId
                )
                responder.publish(response)
                log.info("Successfully processed OwnershipRequest - responded back to client")
            } catch (ex: Throwable) {
                log.error("Failed to process the message ${ex}, Returning a error response")
                return when (ex) {
                    is FlowValidationException -> {
                        println("Flow validation exception occurred, sending failed response")
                        val response = PurchaseOrderResponseMessage(
                                correlationId = requestMessage.correlationId!!,
                                transactionId = null,
                                errorMessages = ex.validationErrors,
                                externalIds = requestMessage.externalIds!!,
                                success = false,
                                bidUniqueId = requestMessage.bidUniqueId
                        )

                        responder.publish(response)
                    }
                    else -> {
                        val response = PurchaseOrderResponseMessage(
                                correlationId = requestMessage.correlationId!!,
                                transactionId = null,
                                errorMessages = listOf(ex.message!!),
                                externalIds = requestMessage.externalIds!!,
                                success = false,
                                bidUniqueId = requestMessage.bidUniqueId
                        )

                        responder.publish(response)
                    }
                }

            }
        } catch (ex: Throwable) {
            requestMessage.tryCount++
            if (requestMessage.tryCount < maxRetryCount) {
                println("Exception handled in PurchaseOrderChangeOwnerMessageConsumer, writing to dlq")
                log.error("Exception handled in PurchaseOrderChangeOwnerMessageConsumer, writing to dlq")
                deadLetterProducer.publish(requestMessage, false)
            } else {
                println("Exception handled in PurchaseOrderChangeOwnerMessageConsumer, writing to dlq fatally")
                log.error("Exception handled in PurchaseOrderChangeOwnerMessageConsumer, writing to dlq fatally")
                deadLetterProducer.publish(requestMessage, true)
            }
        }
    }

    override fun handleCancelOk(consumerTag: String?) {
        println("PurchaseOrderChangeOwnerMessageConsumer: handleCancelOk for consumer tag: $consumerTag")
    }
}