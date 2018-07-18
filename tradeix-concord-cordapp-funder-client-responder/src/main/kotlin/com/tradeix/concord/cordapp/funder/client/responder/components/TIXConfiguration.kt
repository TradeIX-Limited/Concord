package com.tradeix.concord.cordapp.funder.client.responder.components

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@Configuration
@PropertySource("file:application.properties", ignoreResourceNotFound = true)
open class TIXConfiguration(
        @Value("\${config.tix.webapi.url}") val webApiUrl: String,
        @Value("\${config.tix.webapi.composer_program_id}") val composerProgramId: String,
        @Value("\${config.tix.webapi.on_behalf_of}") val onBehalfOf: String,
        @Value("\${config.tix.vault_observer_timeout}") val vaultObserverTimeout: Long
)