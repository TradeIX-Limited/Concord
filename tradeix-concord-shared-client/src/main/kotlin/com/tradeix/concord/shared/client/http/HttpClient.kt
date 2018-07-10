package com.tradeix.concord.shared.client.http

import com.tradeix.concord.shared.client.components.OAuthAccessTokenProvider
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger
import org.springframework.http.*
import org.springframework.web.client.RestTemplate


class HttpClient(private val server: String, private val tokenProvider: OAuthAccessTokenProvider) {

    companion object {
        private val logger: Logger = loggerFor<HttpClient>()
    }

    private val client = RestTemplate()

    private val headers: HttpHeaders
        get() {
            val headers = HttpHeaders()

            headers.contentType = MediaType.APPLICATION_JSON

            headers.add("Accept", "*/*")
            headers.add("Authorization", "bearer ${tokenProvider.accessToken.accessToken}")

            return headers
        }

    fun <T> post(url: String, json: String, returnType: Class<T>): ResponseEntity<T> {
        logger.info("HttpClient.post to ${server + url}.")
        return client.exchange(server + url, HttpMethod.POST, HttpEntity(json, headers), returnType)
    }
}