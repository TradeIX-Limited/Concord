package com.tradeix.concord.cordapp.funder.client.responder.components

import org.springframework.http.CacheControl
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component

@Component
class TIXAuthenticatedHeaderProvider(
        private val tixConfiguration: TIXConfiguration,
        private val oAuthAccessTokenProvider: OAuthAccessTokenProvider) {

    val headers: HttpHeaders
        get() {
            val headers = HttpHeaders()

            headers.contentType = MediaType.APPLICATION_JSON
            headers.accept = listOf(MediaType.APPLICATION_JSON)
            headers.cacheControl = CacheControl.noCache().headerValue

            headers.add("Authorization", "bearer ${oAuthAccessTokenProvider.token.accessToken}")
            headers.add("TradeIX-ComposerProgramIdentifier", tixConfiguration.composerProgramId)
            headers.add("TradeIX-OnBehalfOf", tixConfiguration.onBehalfOf)

            return headers
        }
}