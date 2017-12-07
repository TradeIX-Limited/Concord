package com.tradeix.concord.services.messaging

import com.google.gson.Gson
import com.rabbitmq.client.ConnectionFactory
import com.tradeix.concord.interfaces.IQueueConsumer
import com.tradeix.concord.services.messaging.subscribers.ChangeOwnerFlowQueuesSubscriber
import com.tradeix.concord.services.messaging.subscribers.IssuanceFlowQueuesSubscriber
import com.typesafe.config.ConfigFactory
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.utilities.loggerFor
import net.corda.nodeapi.config.parseAs
import org.slf4j.Logger
import java.io.File

class TixMessageSubscriptionStartup(val services: CordaRPCOps) {

    init {
        val legalEntity = services.nodeInfo().legalIdentities.single()
        log.info("Reached TixMessageSub in ${legalEntity.name.organisation}")

        if (legalEntity.name.organisation == "TradeIX") {
            TixMessageSubscriptionStartup.initializeQueues(services)
        }
    }

    companion object {
        protected  val log: Logger = loggerFor<TixMessageSubscriptionStartup>()
        private val currentConsumers: MutableMap<String, IQueueConsumer> = mutableMapOf()

        private fun initializeQueues(cordaRpcService: CordaRPCOps) {
            try {
                val currentPath = System.getProperty("user.dir")
                log.info("The current path is ${currentPath}")
                val  defaultConfig = ConfigFactory.parseFile(File("${currentPath}/tix.integration.conf"))
                val serializer = Gson()
                val connectionConfig = defaultConfig!!.resolve().getConfig("tix-integration.rabbitMqConnectionConfiguration").parseAs<RabbitMqConnectionConfiguration>()
                val connectionFactory = ConnectionFactory()

                connectionFactory.username = connectionConfig.userName
                connectionFactory.password = connectionConfig.password
                connectionFactory.host = connectionConfig.hostName
                connectionFactory.virtualHost = connectionConfig.virtualHost
                connectionFactory.port = connectionConfig.portNumber

                val connectionProvider = RabbitMqConnectionProvider(connectionFactory)

                log.info("Starting Issuance Rabbit Subscription")
                IssuanceFlowQueuesSubscriber(cordaRpcService, defaultConfig, serializer)
                        .initialize(connectionProvider, currentConsumers)

                log.info("Starting Change of owner Rabbit Subscription")
                ChangeOwnerFlowQueuesSubscriber(cordaRpcService, defaultConfig, serializer)
                        .initialize(connectionProvider, currentConsumers)

            } catch (ex: Throwable) {
                log.error("Exception caught when subscribing to Rabbit queues")
                log.error(ex.message)
                println(ex)
            }
        }
    }
}