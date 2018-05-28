package com.tradeix.concord.cordapp.supplier.client.receiver

import com.tradeix.concord.shared.cordapp.mapping.registerInvoiceMappers
import com.tradeix.concord.shared.mapper.Mapper
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    Mapper.registerInvoiceMappers()
    SpringApplication.run(Application::class.java, *args)
}