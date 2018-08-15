package com.tradeix.concord.cordapp.funder.client.responder.services

import com.google.gson.GsonBuilder
import com.tradeix.concord.cordapp.funder.client.responder.components.TIXAuthenticatedHeaderProvider
import com.tradeix.concord.cordapp.funder.client.responder.components.TIXConfiguration
import com.tradeix.concord.cordapp.funder.mappers.invoices.InvoiceImportNotificationMapper
import com.tradeix.concord.cordapp.funder.messages.invoices.InvoiceImportNotificationMessage
import com.tradeix.concord.cordapp.funder.messages.invoices.InvoiceImportNotificationRequestMessage
import com.tradeix.concord.cordapp.funder.messages.invoices.InvoiceImportNotificationResponseMessage
import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.client.http.HttpClient
import com.tradeix.concord.shared.client.services.ObserverService
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.extensions.getConfiguredSerializer
import com.tradeix.concord.shared.services.VaultService
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger
import org.springframework.http.HttpEntity
import org.springframework.stereotype.Component
import java.util.*
import kotlin.concurrent.timer

@Component
class InvoiceNotificationService(
        private val rpcConnectionProvider: RPCConnectionProvider,
        private val tixConfiguration: TIXConfiguration,
        private val tixAuthenticatedHeaderProvider: TIXAuthenticatedHeaderProvider
) : ObserverService {

    companion object {
        private val logger: Logger = loggerFor<InvoiceNotificationService>()
    }

    private val vaultService = VaultService.fromCordaRPCOps<InvoiceState>(rpcConnectionProvider.proxy)
    private val client = HttpClient()
    private val serializer = GsonBuilder().getConfiguredSerializer()
    private val mapper = InvoiceImportNotificationMapper()
    private val invoices = mutableListOf<InvoiceImportNotificationMessage>()

    private var vaultObserverTimer: Timer? = null

    override fun start() {
        vaultService.observe {
            logger.info("Observed invoice with externalId '${it.state.data.linearId.externalId}'. ")

            vaultObserverTimer?.cancel()
            invoices.add(mapper.map(it.state.data))

            vaultObserverTimer = timer(period = tixConfiguration.vaultObserverTimeout, action = {
                try {
                    val json = serializer.toJson(InvoiceImportNotificationRequestMessage(invoices))
                    val url = tixConfiguration.webApiUrl + "v1/import/invoices"
                    val entity = HttpEntity(json, tixAuthenticatedHeaderProvider.headers)
                    val response = client.post<InvoiceImportNotificationResponseMessage>(url, entity)

                    logger.info("POST to '$url' returned status code '${response.statusCode}'.")
                    logger.info("Invoice import succeeded with batch upload ID '${response.body.batchUploadId}'.")
                } catch (ex: Exception) {
                    logger.error("Failed to POST invoice.\n${ex.message}.")
                } finally {
                    invoices.clear() // TODO : What happens if there was an exception thrown?
                    vaultObserverTimer?.cancel()
                }
            })
        }
    }
}