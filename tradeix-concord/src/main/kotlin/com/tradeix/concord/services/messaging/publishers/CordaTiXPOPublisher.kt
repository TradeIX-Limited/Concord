package com.tradeix.concord.services.messaging.publishers

import com.google.gson.Gson
import com.tradeix.concord.interfaces.FlowQueuesPublisher
import com.tradeix.concord.interfaces.IQueueProducer
import com.tradeix.concord.messages.rabbit.RabbitRequestMessage
import com.tradeix.concord.messages.rabbit.purchaseorder.PurchaseOrderIssuanceRequestMessage
import com.tradeix.concord.services.messaging.RabbitMqConnectionProvider
import com.tradeix.concord.services.messaging.RabbitMqProducer
import com.tradeix.concord.services.messaging.RabbitProducerConfiguration
import com.typesafe.config.Config
import com.typesafe.config.ConfigRenderOptions
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger
import java.math.BigDecimal

class CordaTiXPOPublisher(
        val config: Config,
        val serializer: Gson
) : FlowQueuesPublisher {
    private val log: Logger = loggerFor<CordaTiXPOPublisher>()
    private val configRoot = "tix-integration"
    private val configBranch = "cordatixNotificationConfiguration2"
    private val exchangeRoutingKey = "cordatix_po_notification"
    override fun initialize(connectionProvider: RabbitMqConnectionProvider, currentPublishers: MutableMap<String, IQueueProducer<RabbitRequestMessage>>) {
        val producerConfigurationString = config.resolve().getConfig(configRoot).getObject(configBranch).render(ConfigRenderOptions.concise())
        val producerConfiguration = serializer.fromJson(producerConfigurationString, RabbitProducerConfiguration::class.java)
        if(!currentPublishers.containsKey(producerConfiguration.exchangeRoutingKey)) {
            log.info("Initializing CordaTiXPOPublisher - this should happen only once and only for TradeIX node")
            val producerConfigurations = mapOf(exchangeRoutingKey to producerConfiguration)
            val producer = RabbitMqProducer<RabbitRequestMessage>(
                    producerConfigurations[exchangeRoutingKey]!!,
                    rabbitConnectionProvider = connectionProvider
            )
            currentPublishers.put(producerConfiguration.exchangeRoutingKey, producer)
            println("RabbitMQ CordaTiXPOPublisher ready")
        }
    }
}