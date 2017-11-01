package com.tradeix.concord.services.messaging

import com.tradeix.concord.interfaces.IQueueConsumer
import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage

object TixMessageSubscriptionStartup {
    fun exec(): MutableMap<String, IQueueConsumer> {
        val issueConsumeConfiguration = RabbitConsumerConfiguration("guest"
                , "guest"
                , "localhost"
                , "/"
                , 5672
                , "tixcorda_messaging"
                , "topic"
                , "issue_asset.#"
                , true
                , false
                //, mapOf("x-dead-letter-exchange" to "tixcorda_dlt")
                , emptyMap()
                , "issue_asset_request_queue"
                , true
                , false
                , false
                , emptyMap())

        val tradeIssuanceConsumer = RabbitMqConsumer(issueConsumeConfiguration, TradeAssetIssuanceRequestMessage::class.java)
        tradeIssuanceConsumer.subscribe()

        val consumers = mutableMapOf<String, IQueueConsumer>()
        consumers.put(issueConsumeConfiguration.queueName, tradeIssuanceConsumer)
        return consumers
    }
}