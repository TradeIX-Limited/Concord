package com.tradeix.concord.cordapp.supplier.client.responder

import com.tradeix.concord.cordapp.supplier.client.responder.services.FundingResponseObserverService
import com.tradeix.concord.cordapp.supplier.client.responder.services.InvoiceObserverService
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScans

@ComponentScans(
        ComponentScan("com.tradeix.concord.shared.client.components"),
        ComponentScan("com.tradeix.concord.cordapp.supplier.client.responder.components"),
        ComponentScan("com.tradeix.concord.cordapp.supplier.client.responder.services")
)
@EnableAutoConfiguration
class Application(
        invoiceObserverService: InvoiceObserverService,
        fundingResponseObserverService: FundingResponseObserverService) {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }

    init {
        invoiceObserverService.start()
        fundingResponseObserverService.start()
    }
}
