package com.tradeix.concord.cordapp.funder.client.responder.services

import com.google.gson.GsonBuilder
import com.tradeix.concord.cordapp.funder.client.responder.components.TIXAuthenticatedHeaderProvider
import com.tradeix.concord.cordapp.funder.client.responder.components.TIXConfiguration
import com.tradeix.concord.cordapp.funder.mappers.fundingresponses.FundingResponseNotificationMapper
import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.client.http.HttpClient
import com.tradeix.concord.shared.client.services.ObserverService
import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.extensions.getConfiguredSerializer
import com.tradeix.concord.shared.services.VaultService
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger
import org.springframework.http.HttpEntity
import org.springframework.stereotype.Component

@Component
class FundingResponseNotificationService(
        private val rpcConnectionProvider: RPCConnectionProvider,
        private val tixConfiguration: TIXConfiguration,
        private val tixAuthenticatedHeaderProvider: TIXAuthenticatedHeaderProvider
) : ObserverService {

    companion object {
        private val logger: Logger = loggerFor<FundingResponseNotificationService>()
    }

    private val vaultService = VaultService.fromCordaRPCOps<FundingResponseState>(rpcConnectionProvider.proxy)
    private val client = HttpClient()
    private val serializer = GsonBuilder().getConfiguredSerializer()
    private val mapper = FundingResponseNotificationMapper()

    override fun start() {
        vaultService.observe {
            logger.info("Observed funding response with externalId '${it.state.data.linearId.externalId}'.")

            try {
                val json = serializer.toJson(mapper.map(it.state.data))
                val url = tixConfiguration.webApiUrl + "v1/fundingresponses"
                val entity = HttpEntity(json, tixAuthenticatedHeaderProvider.headers)
                val response = client.post<Any>(url, entity)

                logger.info("POST to '$url' returned status code '${response.statusCode}'.")
            } catch (ex: Exception) {
                logger.error("Failed to POST funding response.\n${ex.message}.")
            }
        }
    }
}