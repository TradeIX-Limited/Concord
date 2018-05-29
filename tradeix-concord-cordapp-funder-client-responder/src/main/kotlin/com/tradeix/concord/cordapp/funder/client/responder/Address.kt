package com.tradeix.concord.cordapp.funder.client.responder

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class Address(
        @Value("\${config.erp.host}") val host: String,
        @Value("\${config.erp.port}") val port: Int
)