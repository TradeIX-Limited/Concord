package com.tradeix.concord.services.messaging

import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage

object TixMessageSubscriptionStartup{
    fun exec() {
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
        , mapOf("x-dead-letter-exchange" to "b")
        , "issue_asset_request_queue"
        , true
        , false
        , false
        , emptyMap())

        RabbitMqConsumer(issueConsumeConfiguration, TradeAssetIssuanceRequestMessage::class.java)
    }
}