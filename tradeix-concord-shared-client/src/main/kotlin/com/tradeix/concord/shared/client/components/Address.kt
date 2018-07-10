package com.tradeix.concord.shared.client.components

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources
import org.springframework.stereotype.Component

@Component
@Configuration
@PropertySource("file:application.properties", ignoreResourceNotFound = true)
class Address(
        @Value("\${config.erp.host}") val host: String,
        @Value("\${config.erp.port}") val port: Int
)