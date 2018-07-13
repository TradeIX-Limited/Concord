package com.tradeix.concord.shared.client.components

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@Configuration
@PropertySource("file:application.properties", ignoreResourceNotFound = true)
class NetSuiteConfiguration(
        @Value("\${config.erp.url") val url: String
)