package com.tradeix.concord.client.spring.receiver

import com.tradeix.concord.cordapp.configuration.mapping.registerInvoiceMappers
import com.tradeix.concord.shared.mapper.Mapper
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}