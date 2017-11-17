package com.tradeix.concord.services.messaging

import com.rabbitmq.client.ConnectionFactory
import com.tradeix.concord.interfaces.IQueueConsumer
import com.tradeix.concord.messages.rabbit.RabbitMessage
import com.tradeix.concord.messages.rabbit.tradeasset.TradeAssetIssuanceRequestMessage
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigParseOptions
import net.corda.core.messaging.CordaRPCOps

class TixMessageSubscriptionStartup(val services: CordaRPCOps) {

    init {
        println("Reached TixMessageSub")
        if (services.nodeInfo().legalIdentities.single().name.organisation == "TradeIX") {
            TixMessageSubscriptionStartup.initializeQueues(services)
        }
    }

    companion object {
        private val currentConsumers: MutableMap<String, IQueueConsumer> = mutableMapOf()
        val  defaultConfig = ConfigFactory.parseResources("tix.integration.conf", ConfigParseOptions.defaults().setAllowMissing(false))
        private fun initializeQueues(cordaRpcService: CordaRPCOps) {
            try {
                if (currentConsumers.count() == 0) {
                    println("Initializing RabbitMQ Subscriptions - this should happen only once")
                    val connectionConfig = RabbitMqConnectionConfiguration(
                            userName = "guest",
                            password = "guest",
                            hostName = "localhost",
                            virtualHost = "/",
                            portNumber = 5672
                    )
                    val issueConsumeConfiguration = RabbitConsumerConfiguration(
                            exchangeName = "tixcorda_messaging",
                            exchangeType = "topic",
                            exchangeRoutingKey = "issue_asset",
                            durableExchange = true,
                            autoDeleteExchange = false,
                            exchangeArguments = emptyMap(),
                            queueName = "issue_asset_request_queue",
                            durableQueue = true,
                            exclusiveQueue = false,
                            autoDeleteQueue = false,
                            queueArguments = emptyMap(),
                            maxRetries = 2
                    )

                    val deadLetterConfig = RabbitDeadLetterConfiguration(
                            exchangeName = "tixcorda_messaging_dlq",
                            exchangeType = "topic",
                            exchangeRoutingKey = "issue_asset",
                            durableExchange = true,
                            autoDeleteExchange = false,
                            exchangeArguments = emptyMap(),
                            queueName = "issue_asset_request_dlt_queue",
                            durableQueue = true,
                            exclusiveQueue = false,
                            autoDeleteQueue = false,
                            queueArguments = mapOf(
                                    "x-dead-letter-exchange" to "tixcorda_messaging",
                                    "x-message-ttl" to 60000),
                            poisonQueueName = "corda_poison_message_queue",
                            poisonQueueRoutingKey = "corda_poison"
                    )

                    val connectionFactory = ConnectionFactory()

                    connectionFactory.username = connectionConfig.userName
                    connectionFactory.password = connectionConfig.password
                    connectionFactory.host = connectionConfig.hostName
                    connectionFactory.virtualHost = connectionConfig.virtualHost
                    connectionFactory.port = connectionConfig.portNumber

                    val connectionProvider = RabbitMqConnectionProvider(connectionFactory)

                    val transactionResponseConfiguration = RabbitProducerConfiguration(
                            exchangeName = "tixcorda_messaging",
                            exchangeType = "topic",
                            exchangeRoutingKey = "cordatix_response",
                            durableExchange = true,
                            autoDeleteExchange = false,
                            exchangeArguments = emptyMap()
                    )

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
                println("Oooh Exception")
                println(ex)
                throw ex
            }
        }
    }
}