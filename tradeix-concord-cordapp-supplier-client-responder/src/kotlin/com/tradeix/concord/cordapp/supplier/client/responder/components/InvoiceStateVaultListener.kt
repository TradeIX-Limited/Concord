package com.tradeix.concord.cordapp.supplier.client.responder.components

import com.google.gson.GsonBuilder
import com.tradeix.concord.cordapp.supplier.client.responder.Address
import com.tradeix.concord.cordapp.supplier.client.responder.RPCConnection
import com.tradeix.concord.shared.client.http.HttpClient
import com.tradeix.concord.shared.data.VaultRepository
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.extensions.getConfiguredSerializer
import com.tradeix.concord.shared.messages.TransactionResponseMessage
import org.springframework.web.bind.annotation.RestController

@RestController
class InvoiceStateVaultListener(address: Address, rpc: RPCConnection) {

    private val repository = VaultRepository.fromCordaRPCOps<InvoiceState>(rpc.proxy)
    private val client = HttpClient("http://${address.host}:${address.port}/")
    private val serializer = GsonBuilder().getConfiguredSerializer()

    init {
        repository.observe {
            client.post("Invoice/Notify", serializer.toJson(TransactionResponseMessage(
                    externalId = it.state.data.linearId.externalId.toString(),
                    transactionId = it.ref.txhash.toString()
            )))
        }
    }
}