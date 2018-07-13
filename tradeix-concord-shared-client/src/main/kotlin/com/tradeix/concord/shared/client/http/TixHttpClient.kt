package com.tradeix.concord.shared.client.http

import com.tradeix.concord.shared.client.components.OAuthAccessTokenProvider
import com.tradeix.concord.shared.client.components.TIXConfiguration
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger
import org.springframework.http.*
import org.springframework.web.client.RestTemplate


class TixHttpClient(
        private val tixConfiguration: TIXConfiguration,
        private val tokenProvider: OAuthAccessTokenProvider) {

    companion object {
        private val logger: Logger = loggerFor<TixHttpClient>()
    }

    private val client = RestTemplate()

    private val headers: HttpHeaders
        get() {
            val headers = HttpHeaders()

            headers.contentType = MediaType.APPLICATION_JSON
            headers.accept = listOf(MediaType.APPLICATION_JSON)
            headers.cacheControl = CacheControl.noCache().headerValue

            headers.add("Authorization", "bearer ${tokenProvider.accessToken.accessToken}")
            headers.add("TradeIX-ComposerProgramIdentifier", tixConfiguration.composerProgramId)
            headers.add("TradeIX-OnBehalfOf", tixConfiguration.onBehalfOf)

            return headers
        }

    fun <T> post(url: String, json: String, returnType: Class<T>): ResponseEntity<T> {
        logger.info("TixHttpClient.post to ${tixConfiguration.webApiUrl + url}.")
        return client.exchange(
                tixConfiguration.webApiUrl + url,
                HttpMethod.POST,
                HttpEntity(json, headers),
                returnType
        )
    }
}