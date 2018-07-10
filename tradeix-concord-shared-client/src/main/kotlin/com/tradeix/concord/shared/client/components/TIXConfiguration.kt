package com.tradeix.concord.shared.client.components

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@Configuration
@PropertySource("file:application.properties", ignoreResourceNotFound = true)
class TIXConfiguration(
        @Value("\${config.tix.idserver.url}") val idServerUrl: String,
        @Value("\${config.tix.webapi.url}") val webApiUrl: String
)