package com.tradeix.concord.services.messaging

import com.tradeix.concord.interfaces.IQueueConsumer
import com.tradeix.concord.messages.Message
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
                , "issue_asset"
                , true
                , false
                //, mapOf("x-dead-letter-exchange" to "tixcorda_messaging")
                , emptyMap()
                , "issue_asset_request_queue"
                , true
                , false
                , false
                , emptyMap(), 2)

        val deadLetterConfiguration = RabbitConsumerConfiguration("guest"
                , "guest"
                , "localhost"
                , "/"
                , 5672
                , "tixcorda_messaging_dlt"
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
                , 2)

        val deadLetterProducer = RabbitMqProducer<Message>(null, deadLetterConfiguration)

        val tradeIssuanceConsumer = RabbitMqConsumer(issueConsumeConfiguration, TradeAssetIssuanceRequestMessage::class.java, deadLetterProducer)
        tradeIssuanceConsumer.subscribe()

        val consumers = mutableMapOf<String, IQueueConsumer>()
        consumers.put(issueConsumeConfiguration.queueName, tradeIssuanceConsumer)
        return consumers
    }
}