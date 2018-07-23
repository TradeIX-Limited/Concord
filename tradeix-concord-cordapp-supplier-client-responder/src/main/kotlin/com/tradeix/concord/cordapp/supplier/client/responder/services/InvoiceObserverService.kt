package com.tradeix.concord.cordapp.supplier.client.responder.services

import com.google.gson.GsonBuilder
import com.tradeix.concord.cordapp.supplier.client.responder.components.ERPConfiguration
import com.tradeix.concord.cordapp.supplier.mappers.invoices.InvoiceIssuanceConfirmationMapper
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

@Component
class InvoiceObserverService(
        private val rpcConnectionProvider: RPCConnectionProvider,
        private val erpConfiguration: ERPConfiguration
) : ObserverService {

    companion object {
        private val logger: Logger = loggerFor<InvoiceObserverService>()
    }

    private val vaultService = VaultService.fromCordaRPCOps<InvoiceState>(rpcConnectionProvider.proxy)
    private val client = HttpClient()
    private val serializer = GsonBuilder().getConfiguredSerializer()
    private val mapper = InvoiceIssuanceConfirmationMapper()

    override fun start() {
        logger.info("Starting invoice observer...")

        vaultService.observe {
            try {
                val json = serializer.toJson(mapper.map(it))
                val url = erpConfiguration.url + "restlet.nl?script=customscript_confirmation_of_issuance&deploy=1"
                val response = client.post<Any>(url, HttpEntity(json))

                logger.info(response.body.toString())
            } catch (ex: Exception) {
                logger.error(ex.message) // TODO : What happens if there was an exception thrown?
            }
        }
    }
}