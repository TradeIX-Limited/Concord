package com.tradeix.concord.shared.client.http

import com.tradeix.concord.shared.client.components.OAuthAccessTokenProvider
import org.springframework.http.*
import org.springframework.web.client.RestTemplate


class HttpClient(private val server: String, private val tokenProvider: OAuthAccessTokenProvider) {

    private val client = RestTemplate()

    private val headers: HttpHeaders
        get() {
            val headers = HttpHeaders()

            headers.contentType = MediaType.APPLICATION_JSON

            headers.add("Accept", "*/*")
            headers.add("Authorization", "bearer ${tokenProvider.accessToken}")

            return headers
        }

    fun <T> post(url: String, json: String, returnType: Class<T>): ResponseEntity<T> {
        return client.exchange(server + url, HttpMethod.POST, HttpEntity(json, headers), returnType)
    }
}