package com.tradeix.concord.services.messaging

import com.google.gson.Gson
import com.rabbitmq.client.ConnectionFactory
import com.tradeix.concord.interfaces.IQueueConsumer
import com.tradeix.concord.messages.rabbit.RabbitMessage
import com.tradeix.concord.messages.rabbit.tradeasset.TradeAssetIssuanceRequestMessage
import com.tradeix.concord.services.messaging.subscribers.IssuanceFlowQueuesSubscriber
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
                val serializer = Gson()
                val connectionConfig = defaultConfig!!.resolve().getConfig("tix-integration.rabbitMqConnectionConfiguration").parseAs<RabbitMqConnectionConfiguration>()
                val connectionFactory = ConnectionFactory()

                connectionFactory.username = connectionConfig.userName
                connectionFactory.password = connectionConfig.password
                connectionFactory.host = connectionConfig.hostName
                connectionFactory.virtualHost = connectionConfig.virtualHost
                connectionFactory.port = connectionConfig.portNumber

                val connectionProvider = RabbitMqConnectionProvider(connectionFactory)

                IssuanceFlowQueuesSubscriber(cordaRpcService, defaultConfig, serializer)
                        .initialize(connectionProvider, currentConsumers)

            } catch (ex: Throwable) {
                log.error(ex.message)
                println(ex)
            }
        }
    }
}