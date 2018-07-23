package com.tradeix.concord.cordapp.funder.client.responder.components

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDateTime

data class OAuthAccessToken(
        @JsonProperty("access_token") val accessToken: String,
        @JsonProperty("expires_in") val expiresIn: Long,
        @JsonProperty("token_type") val tokenType: String) {

    private val expiryDate: LocalDateTime = LocalDateTime.now().plusSeconds(expiresIn)
    val isExpired: Boolean get() = LocalDateTime.now() >= expiryDate
}