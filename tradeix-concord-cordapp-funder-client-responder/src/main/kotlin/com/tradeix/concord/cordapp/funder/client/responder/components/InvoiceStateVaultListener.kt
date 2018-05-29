package com.tradeix.concord.cordapp.funder.client.responder.components

import com.google.gson.GsonBuilder
import com.tradeix.concord.cordapp.funder.client.responder.Address
import com.tradeix.concord.cordapp.funder.client.responder.RPCConnection
import com.tradeix.concord.shared.client.http.HttpClient
import com.tradeix.concord.shared.data.VaultRepository
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.extensions.getConfiguredSerializer
import com.tradeix.concord.shared.mapper.Mapper
import com.tradeix.concord.shared.messages.TransactionResponseMessage
import com.tradeix.concord.shared.messages.invoices.InvoiceRequestMessage
import org.springframework.web.bind.annotation.RestController

@RestController
class InvoiceStateVaultListener(address: Address, rpc: RPCConnection) {

    private val repository = VaultRepository.fromCordaRPCOps<InvoiceState>(rpc.proxy)
    private val client = HttpClient("http://${address.host}:${address.port}/")
    private val serializer = GsonBuilder().getConfiguredSerializer()

    init {
        repository.observe {
            val response: InvoiceRequestMessage = Mapper.map("response", it.state.data)
            client.post("Invoice/Notify", serializer.toJson(response))
        }
    }
}