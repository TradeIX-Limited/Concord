package com.tradeix.concord.shared.client.http

import net.corda.core.utilities.loggerFor
import org.slf4j.Logger
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

class HttpClient {

    companion object {
        @PublishedApi
        internal val logger: Logger = loggerFor<HttpClient>()
    }

    @PublishedApi
    internal val client = RestTemplate()

    inline fun <reified TResponse> post(
            url: String,
            entity: HttpEntity<*>): ResponseEntity<TResponse> {

        logger.info("HttpClient.post to $url.")
        return client.exchange(url, HttpMethod.POST, entity, TResponse::class.java)
    }
}