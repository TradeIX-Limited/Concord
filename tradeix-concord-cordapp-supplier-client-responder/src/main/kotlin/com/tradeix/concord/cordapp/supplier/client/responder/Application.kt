package com.tradeix.concord.cordapp.supplier.client.responder

import com.tradeix.concord.shared.client.components.Address
import com.tradeix.concord.shared.client.components.OAuthAccessTokenProvider
import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.client.observers.InvoiceObserver
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration

@EnableAutoConfiguration
class Application(address: Address, rpc: RPCConnectionProvider, tokenProvider: OAuthAccessTokenProvider) {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }

    private val invoiceObserver = InvoiceObserver(address, rpc, tokenProvider)

    init {
        invoiceObserver.observe()
    }
}
