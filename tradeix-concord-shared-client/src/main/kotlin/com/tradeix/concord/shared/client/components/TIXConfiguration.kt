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
        @Value("\${config.tix.webapi.url}") val webApiUrl: String,
        @Value("\${config.tix.webapi.composer_program_id}") val composerProgramId: String,
        @Value("\${config.tix.webapi.on_behalf_of}") val onBehalfOf: String,
        @Value("\${config.tix.vault_observer_timeout}") val vaultObserverTimeout: Int
)