package com.tradeix.concord.shared.client.components

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class OAuthConfiguration(
        @Value("\${config.oauth.grant_type}") val grantType: String,
        @Value("\${config.oauth.username}") val username: String,
        @Value("\${config.oauth.password}") val password: String,
        @Value("\${config.oauth.scope}") val scope: String,
        @Value("\${config.oauth.client_id}") val clientId: String,
        @Value("\${config.oauth.client_secret}") val clientSecret: String
)