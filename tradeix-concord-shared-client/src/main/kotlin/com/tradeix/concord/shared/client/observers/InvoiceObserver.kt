package com.tradeix.concord.shared.client.observers

import com.google.gson.GsonBuilder
import com.tradeix.concord.shared.client.components.Address
import com.tradeix.concord.shared.client.components.OAuthAccessTokenProvider
import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.client.http.HttpClient
import com.tradeix.concord.shared.cordapp.mapping.invoices.InvoiceResponseMapper
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.extensions.getConfiguredSerializer
import com.tradeix.concord.shared.messages.invoices.InvoiceBatchUploadResponseMessage
import com.tradeix.concord.shared.services.VaultService
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger

class InvoiceObserver(address: Address, rpc: RPCConnectionProvider, tokenProvider: OAuthAccessTokenProvider) {

    companion object {
        private val logger: Logger = loggerFor<InvoiceObserver>()
    }

    private val repository = VaultService.fromCordaRPCOps<InvoiceState>(rpc.proxy)
    private val client = HttpClient("http://${address.host}:${address.port}/", tokenProvider)
    private val serializer = GsonBuilder().getConfiguredSerializer()
    private val mapper = InvoiceResponseMapper()

    fun observe() {
        repository.observe {
            try {
                val item = mapper.map(it.state.data)
                val json = serializer.toJson(InvoiceBatchUploadResponseMessage(listOf(item)))

                client.post("v1/import/invoices", json, Unit::class.java)
            } catch (ex: Exception) {
                logger.error(ex.message)
            }
        }
    }
}