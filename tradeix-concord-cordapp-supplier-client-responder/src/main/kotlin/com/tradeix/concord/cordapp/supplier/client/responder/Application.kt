package com.tradeix.concord.cordapp.supplier.client.responder

import com.tradeix.concord.cordapp.supplier.client.responder.services.FundingResponseNotificationService
import com.tradeix.concord.cordapp.supplier.client.responder.services.InvoiceNotificationService
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
        invoiceNotificationService: InvoiceNotificationService,
        fundingResponseNotificationService: FundingResponseNotificationService) {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }

    init {
        invoiceNotificationService.start()
        fundingResponseNotificationService.start()
    }
}
