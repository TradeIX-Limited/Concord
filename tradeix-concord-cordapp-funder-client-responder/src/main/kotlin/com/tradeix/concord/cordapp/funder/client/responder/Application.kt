package com.tradeix.concord.cordapp.funder.client.responder

import com.tradeix.concord.cordapp.funder.client.responder.services.InvoiceObserverService
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScans

@ComponentScans(
        ComponentScan("com.tradeix.concord.shared.client.components"),
        ComponentScan("com.tradeix.concord.cordapp.funder.client.responder.components"),
        ComponentScan("com.tradeix.concord.cordapp.funder.client.responder.services")
)
@EnableAutoConfiguration
class Application(invoiceObserverService: InvoiceObserverService) {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }

    init {
        invoiceObserverService.start()
    }
}
