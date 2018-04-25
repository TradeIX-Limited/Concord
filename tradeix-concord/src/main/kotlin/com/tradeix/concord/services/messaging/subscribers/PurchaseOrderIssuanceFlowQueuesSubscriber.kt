package com.tradeix.concord.services.messaging.subscribers

import com.google.gson.Gson
import com.tradeix.concord.interfaces.FlowQueuesSubscriber
import com.tradeix.concord.interfaces.IQueueConsumer
import com.tradeix.concord.messages.rabbit.RabbitMessage
import com.tradeix.concord.messages.rabbit.purchaseorder.PurchaseOrderIssuanceRequestMessage
import com.tradeix.concord.services.messaging.*
import com.typesafe.config.Config
import com.typesafe.config.ConfigRenderOptions
import net.corda.core.messaging.CordaRPCOps

class PurchaseOrderIssuanceFlowQueuesSubscriber(
        private val cordaRpcService: CordaRPCOps,
        private val config: Config,
        private val serializer: Gson
) : FlowQueuesSubscriber {

    override fun initialize(
            connectionProvider: RabbitMqConnectionProvider,
            currentConsumers: MutableMap<String, IQueueConsumer>) {

        val consumerConfigurationString = config
                .resolve()
                .getConfig("tix-integration")
                .getObject("issuanceConsumeConfiguration")
                .render(ConfigRenderOptions.concise())

        val issueConsumeConfiguration = serializer
                .fromJson(consumerConfigurationString, RabbitConsumerConfiguration::class.java)

        if (!currentConsumers.containsKey(issueConsumeConfiguration.queueName)) {
            println("Initializing PurchaseOrderIssuanceFlowQueuesSubscriber Subscriptions - this should happen only once")

            val issuanceDeadLetterConfigurationString = config
                    .resolve()
                    .getConfig("tix-integration")
                    .getObject("issuanceDeadLetterConfiguration")
                    .render(ConfigRenderOptions.concise())

            val issuanceDeadLetterConfig = serializer
                    .fromJson(issuanceDeadLetterConfigurationString, RabbitDeadLetterConfiguration::class.java)

            val issuanceResponseConfigurationString = config
                    .resolve()
                    .getConfig("tix-integration")
                    .getObject("issuanceResponseConfiguration")
                    .render(ConfigRenderOptions.concise())

            val issuanceResponseConfiguration = serializer
                    .fromJson(issuanceResponseConfigurationString, RabbitProducerConfiguration::class.java)

            val responderConfigurations = mapOf("cordatix_issuance_response" to issuanceResponseConfiguration)

            val deadLetterProducer = RabbitDeadLetterProducer<RabbitMessage>(
                    deadLetterConfiguration = issuanceDeadLetterConfig,
                    rabbitConnectionProvider = connectionProvider
            )

            val messageConsumerFactory = MessageConsumerFactory(
                    services = cordaRpcService,
                    responderConfigurations = responderConfigurations,
                    rabbitConnectionProvider = connectionProvider
            )

            val purchaseOrderConsumer = RabbitMqConsumer(
                    rabbitConsumerConfiguration = issueConsumeConfiguration,
                    messageClass = PurchaseOrderIssuanceRequestMessage::class.java,
                    deadLetterProducer = deadLetterProducer,
                    rabbitConnectionProvider = connectionProvider,
                    messageConsumerFactory = messageConsumerFactory
            )

            purchaseOrderConsumer.subscribe()
            currentConsumers.put(issueConsumeConfiguration.queueName, purchaseOrderConsumer)
            println("RabbitMQ PurchaseOrderIssuanceFlowQueuesSubscriber subscriptions done")
        }
    }
}