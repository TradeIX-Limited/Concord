package com.tradeix.concord.shared.client.components

import java.time.LocalDateTime

data class OAuthAccessToken(
        val accessToken: String,
        val expiresIn: Long,
        val tokenType: String) {

    private val expiryDate: LocalDateTime = LocalDateTime.now().plusSeconds(expiresIn)
    val isExpired: Boolean get() = LocalDateTime.now() >= expiryDate
}