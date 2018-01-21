package com.tradeix.concord.services.messaging.publishers

import com.google.gson.Gson
import com.tradeix.concord.interfaces.FlowQueuesPublisher
import com.tradeix.concord.interfaces.IQueueProducer
import com.tradeix.concord.messages.rabbit.RabbitRequestMessage
import com.tradeix.concord.services.messaging.RabbitMqConnectionProvider
import com.tradeix.concord.services.messaging.RabbitMqProducer
import com.tradeix.concord.services.messaging.RabbitProducerConfiguration
import com.typesafe.config.Config
import com.typesafe.config.ConfigRenderOptions
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger

class CordaTiXTradeAssetPublisher(
        val config: Config,
        val serializer: Gson
) : FlowQueuesPublisher {
    val log: Logger = loggerFor<CordaTiXTradeAssetPublisher>()
    val configRoot = "tix-integration"
    val configBranch = "cordatixNotificationConfiguration1"
    val exchanceRoutingKey = "cordatix_tradeasset_notification"
    override fun initialize(connectionProvider: RabbitMqConnectionProvider, currentPublishers: MutableMap<String, IQueueProducer<RabbitRequestMessage>>) {

        val producerConfigurationString = config.resolve().getConfig(configRoot).getObject(configBranch).render(ConfigRenderOptions.concise())
        val producerConfiguration = serializer.fromJson(producerConfigurationString, RabbitProducerConfiguration::class.java)

        if(!currentPublishers.containsKey(producerConfiguration.exchangeRoutingKey)) {
            log.info("Initializing CordaTiXTradeAssetPublisher - this should happen only once and only for tradeix node")


            val producerConfigurations = mapOf(exchanceRoutingKey to producerConfiguration)
            val producer = RabbitMqProducer<RabbitRequestMessage>(
                    producerConfigurations[exchanceRoutingKey]!!,
                    rabbitConnectionProvider = connectionProvider
            )

            currentPublishers.put(producerConfiguration.exchangeRoutingKey, producer)
            println("RabbitMQ CordaTiXTradeAssetQueuesPublisher ready")
        }
    }
}