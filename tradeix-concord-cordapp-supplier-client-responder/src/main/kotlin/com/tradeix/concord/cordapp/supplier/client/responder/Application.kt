package com.tradeix.concord.cordapp.supplier.client.responder

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan

@ComponentScan("com.tradeix.concord.shared.client.components")
@EnableAutoConfiguration
class Application {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }
}
