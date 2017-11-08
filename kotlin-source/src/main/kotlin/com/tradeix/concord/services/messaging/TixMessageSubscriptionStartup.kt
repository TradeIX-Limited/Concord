package com.tradeix.concord.services.messaging

import com.rabbitmq.client.ConnectionFactory
import com.tradeix.concord.interfaces.IQueueConsumer
import com.tradeix.concord.messages.Message
import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage
import net.corda.client.rpc.CordaRPCClient
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.utilities.NetworkHostAndPort

class TixMessageSubscriptionStartup(val services: CordaRPCOps) {

    init {
        println("Reached TixMessageSub")
        if (services.nodeInfo().legalIdentities.single().name.organisation.equals("TradeIX")) {
            TixMessageSubscriptionStartup.intializeQueues(services)
        }
    }

    companion object {
        private val currentConsumers: MutableMap<String, IQueueConsumer> = mutableMapOf<String, IQueueConsumer>()
        private fun intializeQueues(cordarpcService: CordaRPCOps) {
            try {
                if (currentConsumers.count() == 0) {
                    println("Intializing RabbitMQ Subscriptions - this should happen only once")
                    val issueConsumeConfiguration = RabbitConsumerConfiguration("tixcorda_messaging"
                            , "topic"
                            , "issue_asset"
                            , true
                            , false
                            , emptyMap()
                            , "issue_asset_request_queue"
                            , true
                            , false
                            , false
                            , emptyMap(), 2)

                    val deadLetterConfig = RabbitDeadLetterConfiguration("tixcorda_messaging_dlt"
                            , "topic"
                            , "issue_asset"
                            , true
                            , false
                            , emptyMap()
                            , "issue_asset_request_dlt_queue"
                            , true
                            , false
                            , false
                            , mapOf("x-dead-letter-exchange" to "tixcorda_messaging", "x-message-ttl" to 10000)
                            , "corda_poison_message_queue"
                            , "corda_poison")

                    val connectionConfig = RabbitMqConnectionConfiguration("guest"
                            , "guest"
                            , "localhost"
                            , "/"
                            , 5672)

                    val connectionFactory = ConnectionFactory()
                    connectionFactory.username = connectionConfig.userName
                    connectionFactory.password = connectionConfig.password
                    connectionFactory.host = connectionConfig.hostName
                    connectionFactory.virtualHost = connectionConfig.virtualHost
                    connectionFactory.port = connectionConfig.portNumber

                    val connectionProvider = RabbitMqConnectionProvider(ConnectionFactory())

                    val transactionResponseConfiguration = RabbitProducerConfiguration(
                            "tixcorda_messaging",
                            "topic",
                            "cordatix_response",
                            true,
                            false,
                            emptyMap()
                    )

                    val responderConfurations = mapOf("cordatix_response" to transactionResponseConfiguration)

                    val deadLetterProducer = RabbitDeadLetterProducer<Message>(deadLetterConfig, connectionProvider)// RabbitMqProducer<Message>(null, deadLetterConfiguration)
                    val messageConsumerFactory = MessageConsumerFactory(cordarpcService, responderConfurations, connectionProvider)
                    val tradeIssuanceConsumer = RabbitMqConsumer(issueConsumeConfiguration, TradeAssetIssuanceRequestMessage::class.java, deadLetterProducer, connectionProvider, messageConsumerFactory)
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

    fun getCordaRPCOps(): CordaRPCOps {
        val nodeAddress = NetworkHostAndPort.parse("localhost:10003")
        val client = CordaRPCClient(nodeAddress)

        // Can be amended in the com.example.MainKt file.
        val proxy = client.start("user1", "test").proxy
        return proxy

    }
}