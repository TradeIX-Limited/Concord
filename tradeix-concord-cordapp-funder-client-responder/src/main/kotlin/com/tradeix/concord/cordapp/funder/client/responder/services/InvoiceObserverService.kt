package com.tradeix.concord.cordapp.funder.client.responder.services

import com.google.gson.GsonBuilder
import com.tradeix.concord.cordapp.funder.client.responder.components.TIXAuthenticatedHeaderProvider
import com.tradeix.concord.cordapp.funder.client.responder.components.TIXConfiguration
import com.tradeix.concord.cordapp.funder.mappers.invoices.InvoiceImportMapper
import com.tradeix.concord.cordapp.funder.messages.invoices.InvoiceImportMessage
import com.tradeix.concord.cordapp.funder.messages.invoices.InvoiceImportRequestMessage
import com.tradeix.concord.cordapp.funder.messages.invoices.InvoiceImportResponseMessage
import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.client.http.HttpClient
import com.tradeix.concord.shared.client.services.ObserverService
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.extensions.getConfiguredSerializer
import com.tradeix.concord.shared.services.VaultService
import net.corda.core.utilities.loggerFor
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.config.Configurator
import org.slf4j.Logger
import org.springframework.http.HttpEntity
import org.springframework.stereotype.Component
import java.util.*
import kotlin.concurrent.timer

@Component
class InvoiceObserverService(
        private val rpcConnectionProvider: RPCConnectionProvider,
        private val tixConfiguration: TIXConfiguration,
        private val tixAuthenticatedHeaderProvider: TIXAuthenticatedHeaderProvider
) : ObserverService {

    companion object {
        private val logger: Logger = loggerFor<InvoiceObserverService>()
    }

    private val vaultService = VaultService.fromCordaRPCOps<InvoiceState>(rpcConnectionProvider.proxy)
    private val client = HttpClient()
    private val serializer = GsonBuilder().getConfiguredSerializer()
    private val mapper = InvoiceImportMapper()
    private val invoices = mutableListOf<InvoiceImportMessage>()

    private var vaultObserverTimer: Timer? = null

    override fun start() {
        vaultService.observe {
            vaultObserverTimer?.cancel()

            invoices.add(mapper.map(it.state.data))

            Configurator.setLevel(logger.name, Level.DEBUG)
            logger.debug("*** FUNDER INVOICE OBSERVER SERVICE >> Invoice external Id: " + it.state.data.linearId.externalId)

            vaultObserverTimer = timer(period = tixConfiguration.vaultObserverTimeout, action = {
                try {
                    val json = serializer.toJson(InvoiceImportRequestMessage(invoices))
                    val url = tixConfiguration.webApiUrl + "v1/import/invoices"
                    val response = client.post<InvoiceImportResponseMessage>(
                            url,
                            HttpEntity(json, tixAuthenticatedHeaderProvider.headers))

                    logger.info("Batch upload ID: ${response.body.batchUploadId}.")
                } catch (ex: Exception) {
                    logger.error(ex.message)
                } finally {
                    invoices.clear() // TODO : What happens if there was an exception thrown?
                    vaultObserverTimer?.cancel()
                }
            })
        }
    }
}