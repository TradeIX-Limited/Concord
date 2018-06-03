package com.tradeix.concord.cordapp.supplier.client.responder

import com.google.gson.GsonBuilder
import com.tradeix.concord.shared.client.components.Address
import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.client.http.HttpClient
import com.tradeix.concord.shared.cordapp.mapping.registerInvoiceMappers
import com.tradeix.concord.shared.data.VaultRepository
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.extensions.getConfiguredSerializer
import com.tradeix.concord.shared.mapper.Mapper
import com.tradeix.concord.shared.messages.TransactionResponseMessage
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScans

@ComponentScans(
        ComponentScan("com.tradeix.concord.shared.client.components"),
        ComponentScan("com.tradeix.concord.cordapp.supplier.client.receiver.controllers"))
@SpringBootApplication
class Application(address: Address, rpc: RPCConnectionProvider) {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Mapper.registerInvoiceMappers()
            SpringApplication.run(Application::class.java, *args)
        }
    }

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
