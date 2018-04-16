package com.tradeix.concord.services.messaging.subscribers

import com.google.gson.Gson
import com.tradeix.concord.interfaces.FlowQueuesSubscriber
import com.tradeix.concord.interfaces.IQueueConsumer
import com.tradeix.concord.messages.rabbit.RabbitMessage
import com.tradeix.concord.messages.rabbit.purchaseorder.PurchaseOrderOwnershipRequestMessage
import com.tradeix.concord.services.messaging.*
import com.typesafe.config.Config
import com.typesafe.config.ConfigRenderOptions
import net.corda.core.messaging.CordaRPCOps

class PurchaseOrderChangeOwnerFlowQueuesSubscriber(
        val cordaRpcService: CordaRPCOps,
        val config: Config,
        val serializer: Gson
) : FlowQueuesSubscriber {

    override fun initialize(
            connectionProvider: RabbitMqConnectionProvider,
            currentConsumers: MutableMap<String, IQueueConsumer>) {

        val consumerConfigurationString = config
                .resolve()
                .getConfig("tix-integration")
                .getObject("changeOwnerConsumeConfiguration")
                .render(ConfigRenderOptions.concise())

        val changeOwnerConsumeConfiguration = serializer
                .fromJson(consumerConfigurationString, RabbitConsumerConfiguration::class.java)

        if (!currentConsumers.containsKey(changeOwnerConsumeConfiguration.queueName)) {
            println("Initializing PurchaseOrderChangeOwnerFlowQueuesSubscriber Subscriptions - this should happen only once")

            val changeOwnerDeadLetterConfigurationString = config
                    .resolve()
                    .getConfig("tix-integration")
                    .getObject("changeOwnerDeadLetterConfiguration")
                    .render(ConfigRenderOptions.concise())

            val changeOwnerDeadLetterConfig = serializer
                    .fromJson(changeOwnerDeadLetterConfigurationString, RabbitDeadLetterConfiguration::class.java)

            val changeOwnerResponseConfigurationString = config
                    .resolve().getConfig("tix-integration")
                    .getObject("changeOwnerResponseConfiguration")
                    .render(ConfigRenderOptions.concise())

            val changeOwnerResponseConfiguration = serializer
                    .fromJson(changeOwnerResponseConfigurationString, RabbitProducerConfiguration::class.java)

            val responderConfigurations = mapOf("cordatix_changeowner_response" to changeOwnerResponseConfiguration)

            val deadLetterProducer = RabbitDeadLetterProducer<RabbitMessage>(
                    deadLetterConfiguration = changeOwnerDeadLetterConfig,
                    rabbitConnectionProvider = connectionProvider
            )

            val messageConsumerFactory = MessageConsumerFactory(
                    services = cordaRpcService,
                    responderConfigurations = responderConfigurations,
                    rabbitConnectionProvider = connectionProvider
            )

            val purchaseOrderOwnershipConsumer = RabbitMqConsumer(
                    rabbitConsumerConfiguration = changeOwnerConsumeConfiguration,
                    messageClass = PurchaseOrderOwnershipRequestMessage::class.java,
                    deadLetterProducer = deadLetterProducer,
                    rabbitConnectionProvider = connectionProvider,
                    messageConsumerFactory = messageConsumerFactory
            )

            purchaseOrderOwnershipConsumer.subscribe()
            currentConsumers.put(changeOwnerConsumeConfiguration.queueName, purchaseOrderOwnershipConsumer)
            println("RabbitMQ PurchaseOrderChangeOwnerFlowQueuesSubscriber subscriptions done")
        }
    }
}