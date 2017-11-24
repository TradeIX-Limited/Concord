package com.tradeix.concord.services.messaging

import com.google.gson.Gson
import com.rabbitmq.client.ConnectionFactory
import com.tradeix.concord.interfaces.IQueueConsumer
import com.tradeix.concord.messages.rabbit.RabbitMessage
import com.tradeix.concord.messages.rabbit.tradeasset.TradeAssetIssuanceRequestMessage
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigParseOptions
import com.typesafe.config.ConfigRenderOptions
import net.corda.core.messaging.CordaRPCOps
import net.corda.nodeapi.config.parseAs
import org.slf4j.Logger
import net.corda.core.utilities.loggerFor

class TixMessageSubscriptionStartup(val services: CordaRPCOps) {

    init {
        println("Reached TixMessageSub")
        if (services.nodeInfo().legalIdentities.single().name.organisation == "TradeIX") {
            TixMessageSubscriptionStartup.initializeQueues(services)
        }
    }

    companion object {
        protected  val log: Logger = loggerFor<TixMessageSubscriptionStartup>()
        private val currentConsumers: MutableMap<String, IQueueConsumer> = mutableMapOf()
        val  defaultConfig = ConfigFactory.parseResources("tix.integration.conf", ConfigParseOptions.defaults().setAllowMissing(false))
        private fun initializeQueues(cordaRpcService: CordaRPCOps) {
            try {
                if (currentConsumers.count() == 0) {
                    println("Initializing RabbitMQ Subscriptions - this should happen only once")
                    val serializer = Gson()
                    val connectionConfig = defaultConfig!!.resolve().getConfig("tix-integration.rabbitMqConnectionConfiguration").parseAs<RabbitMqConnectionConfiguration>()

                    val consumerConfigurationString = defaultConfig.resolve().getConfig("tix-integration").getObject("issuanceConsumeConfiguration").render(ConfigRenderOptions.concise())
                    val issueConsumeConfiguration = serializer.fromJson(consumerConfigurationString, RabbitConsumerConfiguration::class.java)

                    val deadLetterConfigurationString = defaultConfig.resolve().getConfig("tix-integration").getObject("issuanceDeadLetterConfiguration").render(ConfigRenderOptions.concise())
                    val deadLetterConfig =  serializer.fromJson(deadLetterConfigurationString, RabbitDeadLetterConfiguration::class.java)

                    val connectionFactory = ConnectionFactory()

                    connectionFactory.username = connectionConfig.userName
                    connectionFactory.password = connectionConfig.password
                    connectionFactory.host = connectionConfig.hostName
                    connectionFactory.virtualHost = connectionConfig.virtualHost
                    connectionFactory.port = connectionConfig.portNumber

                    val connectionProvider = RabbitMqConnectionProvider(connectionFactory)

                    val transactionResponseConfigurationString = defaultConfig.resolve().getConfig("tix-integration").getObject("transactionResponseConfiguration").render(ConfigRenderOptions.concise())
                    val transactionResponseConfiguration = serializer.fromJson(transactionResponseConfigurationString, RabbitProducerConfiguration::class.java)

                    val responderConfigurations = mapOf("cordatix_response" to transactionResponseConfiguration)

                    val deadLetterProducer = RabbitDeadLetterProducer<RabbitMessage>(
                            deadLetterConfiguration = deadLetterConfig,
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
                    println("RabbitMQ subscriptions done")
                }

            } catch (ex: Throwable) {
                log.error(ex.message)
                println(ex)
            }
        }
    }
}