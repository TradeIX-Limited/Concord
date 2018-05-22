package com.tradeix.concord.client.spring.receiver

import com.tradeix.concord.cordapp.configuration.mapping.registerInvoiceMappers
import com.tradeix.concord.shared.mapper.Mapper
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            initialize()
            SpringApplication.run(Application::class.java, *args)
        }

        fun initialize() {
            Mapper.registerInvoiceMappers()
        }
    }
}