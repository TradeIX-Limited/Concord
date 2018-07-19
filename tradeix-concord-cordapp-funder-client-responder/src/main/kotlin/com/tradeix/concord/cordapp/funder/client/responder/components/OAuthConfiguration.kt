package com.tradeix.concord.cordapp.funder.client.responder.components

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@Configuration
@PropertySource("file:application.properties", ignoreResourceNotFound = true)
open class OAuthConfiguration(
        @Value("\${config.oauth.token_url}") val url: String,
        @Value("\${config.oauth.grant_type}") val grantType: String,
        @Value("\${config.oauth.username}") val username: String,
        @Value("\${config.oauth.password}") val password: String,
        @Value("\${config.oauth.scope}") val scope: String,
        @Value("\${config.oauth.client_id}") val clientId: String,
        @Value("\${config.oauth.client_secret}") val clientSecret: String
)