package com.tradeix.concord.services.messaging

import com.google.gson.GsonBuilder
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Consumer
import com.tradeix.concord.interfaces.IQueueDeadLetterProducer
import com.tradeix.concord.messages.rabbit.RabbitMessage
import com.tradeix.concord.messages.rabbit.invoice.*
import com.tradeix.concord.messages.rabbit.purchaseorder.*
import com.tradeix.concord.serialization.CordaX500NameSerializer
import com.tradeix.concord.serialization.DateInstantSerializer
import com.tradeix.concord.services.messaging.consumers.*
import net.corda.core.identity.CordaX500Name
import net.corda.core.messaging.CordaRPCOps
import java.time.Instant

class MessageConsumerFactory(
        val services: CordaRPCOps,
        private val responderConfigurations: Map<String, RabbitProducerConfiguration>,
        private val rabbitConnectionProvider: RabbitMqConnectionProvider) {

    fun <T : RabbitMessage> getMessageConsumer(
            channel: Channel,
            type: Class<T>,
            deadLetterProducer: IQueueDeadLetterProducer<RabbitMessage>,
            maxRetries: Int): Consumer {

        val cordaNameSerialiser = GsonBuilder()
                .registerTypeAdapter(CordaX500Name::class.java, CordaX500NameSerializer())
                .registerTypeAdapter(Instant::class.java, DateInstantSerializer())
                .disableHtmlEscaping()
                .create()

        return when {
            type.isAssignableFrom(PurchaseOrderIssuanceRequestMessage::class.java) -> {

                val responder = RabbitMqProducer<PurchaseOrderResponseMessage>(
                        responderConfigurations["cordatix_issue_purchase_order_response"]!!,
                        rabbitConnectionProvider
                )

                PurchaseOrderIssuanceMessageConsumer(
                        services,
                        channel,
                        deadLetterProducer,
                        maxRetries,
                        responder,
                        cordaNameSerialiser
                )
            }
            type.isAssignableFrom(PurchaseOrderAmendmentRequestMessage::class.java) -> {

                val responder = RabbitMqProducer<PurchaseOrderResponseMessage>(
                        responderConfigurations["cordatix_amend_purchase_order_response"]!!,
                        rabbitConnectionProvider
                )

                PurchaseOrderAmendmentMessageConsumer(
                        services,
                        channel,
                        deadLetterProducer,
                        maxRetries,
                        responder,
                        cordaNameSerialiser
                )
            }
            type.isAssignableFrom(PurchaseOrderOwnershipRequestMessage::class.java) -> {

                val responder = RabbitMqProducer<PurchaseOrderResponseMessage>(
                        responderConfigurations["cordatix_change_owner_purchase_order_response"]!!,
                        rabbitConnectionProvider
                )

                PurchaseOrderChangeOwnerMessageConsumer(
                        services,
                        channel,
                        deadLetterProducer,
                        maxRetries,
                        responder,
                        cordaNameSerialiser
                )
            }
            type.isAssignableFrom(PurchaseOrderCancellationRequestMessage::class.java) -> {

                val responder = RabbitMqProducer<PurchaseOrderResponseMessage>(
                        responderConfigurations["cordatix_cancel_purchase_order_response"]!!,
                        rabbitConnectionProvider
                )

                PurchaseOrderCancellationMessageConsumer(
                        services,
                        channel,
                        deadLetterProducer,
                        maxRetries,
                        responder,
                        cordaNameSerialiser
                )
            }
            type.isAssignableFrom(InvoiceIssuanceRequestMessage::class.java) -> {

                val responder = RabbitMqProducer<InvoiceResponseMessage>(
                        responderConfigurations["cordatix_issue_invoice_response"]!!,
                        rabbitConnectionProvider
                )

                InvoiceIssuanceMessageConsumer(
                        services,
                        channel,
                        deadLetterProducer,
                        maxRetries,
                        responder,
                        cordaNameSerialiser
                )
            }
            type.isAssignableFrom(InvoiceAmendmentRequestMessage::class.java) -> {

                val responder = RabbitMqProducer<InvoiceResponseMessage>(
                        responderConfigurations["cordatix_amend_invoice_response"]!!,
                        rabbitConnectionProvider
                )

                InvoiceAmendmentMessageConsumer(
                        services,
                        channel,
                        deadLetterProducer,
                        maxRetries,
                        responder,
                        cordaNameSerialiser
                )
            }
            type.isAssignableFrom(InvoiceOwnershipRequestMessage::class.java) -> {

                val responder = RabbitMqProducer<InvoiceResponseMessage>(
                        responderConfigurations["cordatix_change_owner_invoice_response"]!!,
                        rabbitConnectionProvider
                )

                InvoiceChangeOwnerMessageConsumer(
                        services,
                        channel,
                        deadLetterProducer,
                        maxRetries,
                        responder,
                        cordaNameSerialiser
                )
            }
            type.isAssignableFrom(InvoiceCancellationRequestMessage::class.java) -> {

                val responder = RabbitMqProducer<InvoiceResponseMessage>(
                        responderConfigurations["cordatix_cancel_invoice_response"]!!,
                        rabbitConnectionProvider
                )

                InvoiceCancellationMessageConsumer(
                        services,
                        channel,
                        deadLetterProducer,
                        maxRetries,
                        responder,
                        cordaNameSerialiser
                )
            }
            else -> throw ClassNotFoundException()
        }
    }
}