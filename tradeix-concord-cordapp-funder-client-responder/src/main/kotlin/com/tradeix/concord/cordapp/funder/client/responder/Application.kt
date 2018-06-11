package com.tradeix.concord.cordapp.funder.client.responder

import com.google.gson.GsonBuilder
import com.tradeix.concord.shared.client.components.Address
import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.client.http.HttpClient
import com.tradeix.concord.shared.cordapp.mapping.registerInvoiceMappers
import com.tradeix.concord.shared.services.VaultService
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.extensions.getConfiguredSerializer
import com.tradeix.concord.shared.mapper.Mapper
import com.tradeix.concord.shared.messages.invoices.InvoiceMessage
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@ComponentScan("com.tradeix.concord.shared.client.components")
@SpringBootApplication
class Application(address: Address, rpc: RPCConnectionProvider) {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Mapper.registerInvoiceMappers()
            SpringApplication.run(Application::class.java, *args)
        }
    }

    private val repository = VaultService.fromCordaRPCOps<InvoiceState>(rpc.proxy)
    private val client = HttpClient("http://${address.host}:${address.port}/")
    private val serializer = GsonBuilder().getConfiguredSerializer()

    init {
        repository.observe {
            val response: InvoiceMessage = Mapper.map("response", it.state.data)
            client.post("Invoice/Notify", serializer.toJson(response))
        }
    }
}

