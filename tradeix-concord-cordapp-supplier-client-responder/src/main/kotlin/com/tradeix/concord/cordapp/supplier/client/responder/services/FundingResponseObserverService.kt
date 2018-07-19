package com.tradeix.concord.cordapp.supplier.client.responder.services

import com.google.gson.GsonBuilder
import com.tradeix.concord.cordapp.supplier.client.responder.components.ERPConfiguration
import com.tradeix.concord.cordapp.supplier.mappers.fundingresponses.FundingResponseNotificationMapper
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
class FundingResponseObserverService(
        private val rpcConnectionProvider: RPCConnectionProvider,
        private val erpConfiguration: ERPConfiguration
) : ObserverService {

    companion object {
        private val logger: Logger = loggerFor<FundingResponseObserverService>()
    }

    private val vaultService = VaultService.fromCordaRPCOps<FundingResponseState>(rpcConnectionProvider.proxy)
    private val client = HttpClient()
    private val serializer = GsonBuilder().getConfiguredSerializer()
    private val mapper = FundingResponseNotificationMapper()

    override fun start() {
        logger.info("Starting funding response observer...")

        vaultService.observe {
            try {
                val json = serializer.toJson(mapper.map(it.state.data))
                val url = erpConfiguration.url + "fundingresponses/whatever/this/is/meant/to/be/"
                val response = client.post<Any>(url, HttpEntity(json))

                logger.info(response.body.toString())
            } catch (ex: Exception) {
                logger.error(ex.message) // TODO : What happens if there was an exception thrown?
            }
        }
    }
}