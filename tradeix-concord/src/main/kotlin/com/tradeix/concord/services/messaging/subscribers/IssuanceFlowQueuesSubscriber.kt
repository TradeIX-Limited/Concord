package com.tradeix.concord.services.messaging.subscribers

import com.google.gson.Gson
import com.rabbitmq.client.ConnectionFactory
import com.tradeix.concord.interfaces.FlowQueuesSubscriber
import com.tradeix.concord.interfaces.IQueueConsumer
import com.tradeix.concord.messages.rabbit.RabbitMessage
import com.tradeix.concord.messages.rabbit.tradeasset.TradeAssetIssuanceRequestMessage
import com.tradeix.concord.services.messaging.*
import com.typesafe.config.Config
import com.typesafe.config.ConfigRenderOptions
import net.corda.core.messaging.CordaRPCOps
import net.corda.nodeapi.config.parseAs

class IssuanceFlowQueuesSubscriber(
        val cordaRpcService: CordaRPCOps,
        val config: Config,
        val serializer: Gson
) : FlowQueuesSubscriber {

    override fun initialize(connectionProvider: RabbitMqConnectionProvider, currentConsumers: MutableMap<String, IQueueConsumer>) {
        val consumerConfigurationString = config.resolve().getConfig("tix-integration").getObject("issuanceConsumeConfiguration").render(ConfigRenderOptions.concise())
        val issueConsumeConfiguration = serializer.fromJson(consumerConfigurationString, RabbitConsumerConfiguration::class.java)

        if(!currentConsumers.containsKey(issueConsumeConfiguration.queueName)) {
            println("Initializing IssuanceFlowQueuesSubscriber Subscriptions - this should happen only once")

            val issuanceDeadLetterConfigurationString = config.resolve().getConfig("tix-integration").getObject("issuanceDeadLetterConfiguration").render(ConfigRenderOptions.concise())
            val issuanceDeadLetterConfig =  serializer.fromJson(issuanceDeadLetterConfigurationString, RabbitDeadLetterConfiguration::class.java)

            val issuanceResponseConfigurationString = config.resolve().getConfig("tix-integration").getObject("issuanceResponseConfiguration").render(ConfigRenderOptions.concise())
            val issuanceResponseConfiguration = serializer.fromJson(issuanceResponseConfigurationString, RabbitProducerConfiguration::class.java)

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

            val tradeIssuanceConsumer = RabbitMqConsumer(
                    rabbitConsumerConfiguration = issueConsumeConfiguration,
                    messageClass = TradeAssetIssuanceRequestMessage::class.java,
                    deadLetterProducer = deadLetterProducer,
                    rabbitConnectionProvider = connectionProvider,
                    messageConsumerFactory = messageConsumerFactory
            )

            tradeIssuanceConsumer.subscribe()
            currentConsumers.put(issueConsumeConfiguration.queueName, tradeIssuanceConsumer)
            println("RabbitMQ IssuanceFlowQueuesSubscriber subscriptions done")
        }
    }
}