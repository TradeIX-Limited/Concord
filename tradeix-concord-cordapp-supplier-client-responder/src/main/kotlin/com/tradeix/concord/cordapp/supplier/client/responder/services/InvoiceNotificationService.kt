package com.tradeix.concord.cordapp.supplier.client.responder.services

import com.google.gson.GsonBuilder
import com.tradeix.concord.cordapp.supplier.client.responder.components.ERPConfiguration
import com.tradeix.concord.cordapp.supplier.mappers.invoices.InvoiceIssuanceConfirmationNotificationMapper
import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.client.http.HttpClient
import com.tradeix.concord.shared.client.services.ObserverService
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.extensions.getConfiguredSerializer
import com.tradeix.concord.shared.services.VaultService
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component

@Component
class InvoiceNotificationService(
        private val rpcConnectionProvider: RPCConnectionProvider,
        private val erpConfiguration: ERPConfiguration
) : ObserverService {

    companion object {
        private val logger: Logger = loggerFor<InvoiceNotificationService>()
    }

    private val vaultService = VaultService.fromCordaRPCOps<InvoiceState>(rpcConnectionProvider.proxy)
    private val client = HttpClient()
    private val serializer = GsonBuilder().getConfiguredSerializer()
    private val mapper = InvoiceIssuanceConfirmationNotificationMapper()

    override fun start() {
        vaultService.observe {
            logger.info("Observed invoice with externalId '${it.state.data.linearId.externalId}'. ")

            try {
                val json = serializer.toJson(mapper.map(it))
                val url = erpConfiguration.url + "restlet.nl?script=customscript_confirmation_of_issuance&deploy=1"
                val response = client.post<Any>(url, HttpEntity(json, createClientHeaders()))

                logger.info("POST to '$url' returned status code '${response.statusCode}'.")
            } catch (ex: Exception) {
                logger.error("Failed to POST invoice.\n${ex.message}.")
            }
        }
    }

    private fun createClientHeaders(): HttpHeaders {
        val headers = HttpHeaders()

        headers.add("Realm", erpConfiguration.realm)
        headers.add("Consumer Key", erpConfiguration.consumerKey)
        headers.add("Consumer Secret", erpConfiguration.consumerSecret)
        headers.add("Token", erpConfiguration.token)
        headers.add("Token Secret", erpConfiguration.tokenSecret)

        return headers
    }
}