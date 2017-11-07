package com.tradeix.concord.services.messaging

import com.tradeix.concord.interfaces.IQueueConsumer
import com.tradeix.concord.messages.Message
import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage
import net.corda.client.rpc.CordaRPCClient
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.CordaService
import net.corda.core.utilities.NetworkHostAndPort

@CordaService
class TixMessageSubscriptionStartup(val services: ServiceHub) {

    init {
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

       /* val connectionProvider = RabbitMqConnectionProvider(connectionConfig)

        val deadLetterProducer = RabbitDeadLetterProducer<Message>(deadLetterConfig, connectionProvider)// RabbitMqProducer<Message>(null, deadLetterConfiguration)
        //val rpcClient = CordaRPCClient()
        val svs: CordaRPCOps = getCordaRPCOps()
        val messageConsumerFactory = MessageConsumerFactory(svs)
        val tradeIssuanceConsumer = RabbitMqConsumer(issueConsumeConfiguration, TradeAssetIssuanceRequestMessage::class.java, deadLetterProducer, connectionProvider, messageConsumerFactory)
        tradeIssuanceConsumer.subscribe()

        val consumers = mutableMapOf<String, IQueueConsumer>()
        consumers.put(issueConsumeConfiguration.queueName, tradeIssuanceConsumer)
        currentConsumers = consumers*/
    }

    fun getCordaRPCOps(): CordaRPCOps{
        val nodeAddress = NetworkHostAndPort.parse("localhost:10003")
        val client = CordaRPCClient(nodeAddress)

        // Can be amended in the com.example.MainKt file.
        val proxy = client.start("user1", "test").proxy
        return proxy

    }

    companion object {
        private lateinit var currentConsumers: MutableMap<String, IQueueConsumer>
    }
}