package com.tradeix.concord.cordapp.funder.client.receiver

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScans

@ComponentScans(
        ComponentScan("com.tradeix.concord.shared.client.components"),
        ComponentScan("com.tradeix.concord.cordapp.funder.client.receiver.controllers")
)
@SpringBootApplication
class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}