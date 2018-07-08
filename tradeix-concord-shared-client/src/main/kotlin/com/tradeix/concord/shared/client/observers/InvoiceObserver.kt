package com.tradeix.concord.shared.client.observers

import com.google.gson.GsonBuilder
import com.tradeix.concord.shared.client.components.Address
import com.tradeix.concord.shared.client.components.OAuthAccessTokenProvider
import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.client.http.HttpClient
import com.tradeix.concord.shared.cordapp.mapping.invoices.InvoiceResponseMapper
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.extensions.getConfiguredSerializer
import com.tradeix.concord.shared.messages.TransactionResponseMessage
import com.tradeix.concord.shared.messages.invoices.InvoiceBatchUploadResponseMessage
import com.tradeix.concord.shared.services.VaultService

class InvoiceObserver(address: Address, rpc: RPCConnectionProvider, tokenProvider: OAuthAccessTokenProvider) {

    private val repository = VaultService.fromCordaRPCOps<InvoiceState>(rpc.proxy)
    private val client = HttpClient("http://${address.host}:${address.port}/", tokenProvider)
    private val serializer = GsonBuilder().getConfiguredSerializer()
    private val mapper = InvoiceResponseMapper()

    fun observe() {
        repository.observe {
            val item = mapper.map(it.state.data)
            val json = serializer.toJson(InvoiceBatchUploadResponseMessage(listOf(item)))

            client.post("import/invoices", json, Unit::class.java)
        }
    }
}