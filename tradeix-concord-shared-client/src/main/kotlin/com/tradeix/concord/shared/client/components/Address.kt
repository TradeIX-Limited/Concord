package com.tradeix.concord.shared.client.components

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources
import org.springframework.stereotype.Component

@Component
@Configuration
@PropertySources(
        PropertySource("classpath:receiver.properties", ignoreResourceNotFound = true),
        PropertySource("classpath:responder.properties", ignoreResourceNotFound = true)
)
class Address(
        @Value("\${config.tix.host}") val host: String,
        @Value("\${config.tix.port}") val port: Int
)