package com.tradeix.concord.shared.client.components

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
class Address(
        @Value("\${config.erp.host}") val host: String,
        @Value("\${config.erp.port}") val port: Int
)