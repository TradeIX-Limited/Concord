package com.tradeix.concord.shared.client.components

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

@Component
class OAuthAccessTokenProvider(
        private val tixConfiguration: TixConfiguration,
        private val oauthConfiguration: OAuthConfiguration) {

    private val client = RestTemplate()
    private var token = getOAuthAccessToken()

    val accessToken: OAuthAccessToken
        get() {
            if (token.isExpired) {
                token = getOAuthAccessToken()
            }

            return token
        }

    private fun getOAuthAccessToken(): OAuthAccessToken {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED

        val map = LinkedMultiValueMap<String, String>()

        map.add("grant_type", oauthConfiguration.grantType)
        map.add("username", oauthConfiguration.username)
        map.add("password", oauthConfiguration.password)
        map.add("scope", oauthConfiguration.scope)
        map.add("client_id", oauthConfiguration.clientId)
        map.add("client_secret", oauthConfiguration.clientSecret)

        val request = HttpEntity<MultiValueMap<String, String>>(map, headers)
        return client.postForEntity(
                tixConfiguration.idServerUrl + "connect/token",
                request,
                OAuthAccessToken::class.java
        ).body
    }
}