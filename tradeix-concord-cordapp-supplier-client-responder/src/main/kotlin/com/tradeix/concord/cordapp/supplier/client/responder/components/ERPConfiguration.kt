package com.tradeix.concord.cordapp.supplier.client.responder.components

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@Configuration
@PropertySource("file:application.properties", ignoreResourceNotFound = true)
open class ERPConfiguration(
        @Value("\${config.erp.url}") val url: String
)