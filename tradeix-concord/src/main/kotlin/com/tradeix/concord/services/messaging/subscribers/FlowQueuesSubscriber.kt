package com.tradeix.concord.services.messaging.subscribers

import com.google.gson.Gson
import com.tradeix.concord.interfaces.IQueueConsumer
import com.tradeix.concord.messages.rabbit.RabbitMessage
import com.tradeix.concord.services.messaging.*
import com.typesafe.config.Config
import com.typesafe.config.ConfigRenderOptions
import net.corda.core.messaging.CordaRPCOps

abstract class FlowQueuesSubscriber<TMessage : RabbitMessage>(
        private val cordaRpcService: CordaRPCOps,
        private val config: Config,
        private val serializer: Gson,
        private val configurationName: String,
        private val consumerConfigurationKey: String,
        private val deadLetterConfigurationKey: String,
        private val responseConfigurationKey: String,
        private val responderConfigurationExchangeRoutingKey: String,
        private val messageClass: Class<TMessage>) {

    fun initialize(connectionProvider: RabbitMqConnectionProvider, currentConsumers: MutableMap<String, IQueueConsumer>) {

        val consumerConfiguration = createConsumerConfiguration()

        if (!currentConsumers.containsKey(consumerConfiguration.queueName)) {

            println("Initializing ${this::class.simpleName} Subscriptions. This should happen only once.")

            val deadLetterConfiguration = createDeadLetterConfiguration()
            val responderConfiguration = createResponderConfiguration()
            val mappedResponderConfigurations = mapOf(
                    responderConfigurationExchangeRoutingKey to responderConfiguration)

            val consumer = RabbitMqConsumer(
                    rabbitConsumerConfiguration = consumerConfiguration,
                    messageClass = messageClass,
                    deadLetterProducer = RabbitDeadLetterProducer(
                            deadLetterConfiguration = deadLetterConfiguration,
                            rabbitConnectionProvider = connectionProvider
                    ),
                    rabbitConnectionProvider = connectionProvider,
                    messageConsumerFactory = MessageConsumerFactory(
                            services = cordaRpcService,
                            responderConfigurations = mappedResponderConfigurations,
                            rabbitConnectionProvider = connectionProvider
                    )
            )

            consumer.subscribe()
            currentConsumers.put(consumerConfiguration.queueName, consumer)

            println("Initialization of ${this::class.simpleName} complete.")
        }
    }

    private fun createConsumerConfiguration(): RabbitConsumerConfiguration {
        val consumerConfigurationString = config
                .resolve()
                .getConfig(configurationName)
                .getObject(consumerConfigurationKey)
                .render(ConfigRenderOptions.concise())

        return serializer.fromJson(consumerConfigurationString, RabbitConsumerConfiguration::class.java)
    }

    private fun createDeadLetterConfiguration(): RabbitDeadLetterConfiguration {
        val deadLetterConfigurationString = config
                .resolve()
                .getConfig(configurationName)
                .getObject(deadLetterConfigurationKey)
                .render(ConfigRenderOptions.concise())

        return serializer.fromJson(deadLetterConfigurationString, RabbitDeadLetterConfiguration::class.java)
    }

    private fun createResponderConfiguration(): RabbitProducerConfiguration {
        val responseConfigurationString = config
                .resolve()
                .getConfig(configurationName)
                .getObject(responseConfigurationKey)
                .render(ConfigRenderOptions.concise())

        return serializer.fromJson(responseConfigurationString, RabbitProducerConfiguration::class.java)
    }
}