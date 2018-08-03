package com.tradeix.concord.cordapp.funder.client.responder.services

import com.google.gson.GsonBuilder
import com.tradeix.concord.cordapp.funder.client.responder.components.TIXAuthenticatedHeaderProvider
import com.tradeix.concord.cordapp.funder.client.responder.components.TIXConfiguration
import com.tradeix.concord.cordapp.funder.mappers.fundingresponses.FundingResponseRejectionNotificationMapper
import com.tradeix.concord.cordapp.funder.messages.fundingresponses.FundingResponseRejectionNotificationMessage
import com.tradeix.concord.cordapp.funder.messages.fundingresponses.FundingResponseRejectionNotificationRequestMessage
import com.tradeix.concord.cordapp.funder.messages.fundingresponses.FundingResponseRejectionNotificationResponseMessage
import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.client.http.HttpClient
import com.tradeix.concord.shared.client.services.ObserverService
import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.extensions.getConfiguredSerializer
import com.tradeix.concord.shared.services.VaultService
import net.corda.core.utilities.loggerFor
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.config.Configurator
import org.slf4j.Logger
import org.springframework.http.HttpEntity
import java.util.*
import kotlin.concurrent.timer


class FundingResponseRejectionNotificationService(
        private val rpcConnectionProvider: RPCConnectionProvider,
        private val tixConfiguration: TIXConfiguration,
        private val tixAuthenticatedHeaderProvider: TIXAuthenticatedHeaderProvider
) : ObserverService {

    companion object {
        private val logger: Logger = loggerFor<FundingResponseRejectionNotificationService>()
    }

    private val vaultService = VaultService.fromCordaRPCOps<FundingResponseState>(rpcConnectionProvider.proxy)
    private val client = HttpClient()
    private val serializer = GsonBuilder().getConfiguredSerializer()
    private val mapper = FundingResponseRejectionNotificationMapper()
    private val fundingResponses = mutableListOf<FundingResponseRejectionNotificationMessage>()

    private var vaultObserverTimer: Timer? = null

    override fun start() {
        vaultService.observe {
            vaultObserverTimer?.cancel()

            fundingResponses.add(mapper.map(it.state.data))

            Configurator.setLevel(logger.name, Level.DEBUG)
            logger.debug("*** FUNDER FUNDING RESPONSE REJECTION NOTIFICATION SERVICE >> Funding Response external Id: " + it.state.data.linearId.externalId)

            vaultObserverTimer = timer(period = tixConfiguration.vaultObserverTimeout, action = {
                try {
                    val json = serializer.toJson(FundingResponseRejectionNotificationRequestMessage(fundingResponses))
                    val url = tixConfiguration.webApiUrl + "v1/fundingresponses/reject"
                    val response = client.post<FundingResponseRejectionNotificationResponseMessage>(
                            url,
                            HttpEntity(json, tixAuthenticatedHeaderProvider.headers))

                    logger.info("upload ID: ${response.body.batchUploadId}.")

                } catch (ex: Exception) {
                    logger.error(ex.message)
                } finally {
                    fundingResponses.clear() // TODO : What happens if there was an exception thrown?
                    vaultObserverTimer?.cancel()
                }
            })
        }
    }
}