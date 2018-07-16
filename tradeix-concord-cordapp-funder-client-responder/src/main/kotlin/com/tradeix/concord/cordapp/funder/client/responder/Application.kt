package com.tradeix.concord.cordapp.funder.client.responder

import com.tradeix.concord.shared.client.components.OAuthAccessTokenProvider
import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.client.components.TixConfiguration
import com.tradeix.concord.shared.client.observers.InvoiceObserver
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan

@ComponentScan("com.tradeix.concord.shared.client.components")
@EnableAutoConfiguration
class Application(
        tixConfiguration: TixConfiguration,
        rpc: RPCConnectionProvider,
        tokenProvider: OAuthAccessTokenProvider) {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }

    private val invoiceObserver = InvoiceObserver(tixConfiguration, rpc, tokenProvider)

    init {
        invoiceObserver.observe()
    }
}
