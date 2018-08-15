package com.tradeix.concord.cordapp.funder.client.responder

import com.tradeix.concord.cordapp.funder.client.responder.services.FundingResponseNotificationService
import com.tradeix.concord.cordapp.funder.client.responder.services.InvoiceNotificationService
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger
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
class Application(
        invoiceNotificationService: InvoiceNotificationService,
        fundingResponseNotificationService: FundingResponseNotificationService) {

    companion object {
        private val logger: Logger = loggerFor<Application>()

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }

    init {
        logger.info("Starting the invoice notification service.")
        invoiceNotificationService.start()

        logger.info("Starting the funding response notification service.")
        fundingResponseNotificationService.start()
    }
}
