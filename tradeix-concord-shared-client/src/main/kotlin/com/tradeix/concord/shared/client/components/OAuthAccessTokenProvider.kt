package com.tradeix.concord.shared.client.components

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

@Component
class OAuthAccessTokenProvider(private val configuration: OAuthConfiguration) {

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
        map.add("grant_type", configuration.grantType)
        map.add("username", configuration.username)
        map.add("password", configuration.password)
        map.add("scope", configuration.scope)
        map.add("client_id", configuration.clientId)
        map.add("client_secret", configuration.clientSecret)

        val request = HttpEntity<MultiValueMap<String, String>>(map, headers)
        return client.postForEntity(configuration.endpoint, request, OAuthAccessToken::class.java).body
    }
}