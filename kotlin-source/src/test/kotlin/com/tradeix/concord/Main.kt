package com.tradeix.concord

import com.tradeix.concord.services.messaging.TixMessageSubscriptionStartup
import net.corda.core.identity.CordaX500Name
import net.corda.core.utilities.getOrThrow
import net.corda.node.services.transactions.ValidatingNotaryService
import net.corda.nodeapi.User
import net.corda.nodeapi.internal.ServiceInfo
import net.corda.testing.driver.driver

fun main(args: Array<String>) {
    // No permissions required as we are not invoking flows.
    val user = User("user1", "test", permissions = setOf())
    // Create RabbitMQ subscriptions
    TixMessageSubscriptionStartup.exec()
    driver(isDebug = true) {

       // Notary
        startNode(
                providedName = CordaX500Name("R3Net", "London", "GB"),
                advertisedServices = setOf(ServiceInfo(ValidatingNotaryService.type))
        )

        // Conductor
        startWebserver(
                startNode(
                        providedName = CordaX500Name("TradeIX", "London", "GB"),
                        rpcUsers = listOf(user)
                ).getOrThrow()
        )

        // Buyer 1
        startWebserver(
                startNode(
                        providedName = CordaX500Name("Exxon Mobil", "Irving Texas", "US"),
                        rpcUsers = listOf(user)
                ).getOrThrow()
        )

        // Buyer 2
        startWebserver(
                startNode(
                        providedName = CordaX500Name("Boeing", "Chicago Illinois", "US"),
                        rpcUsers = listOf(user)
                ).getOrThrow()
        )

        // Supplier 1
        startWebserver(
                startNode(
                        providedName = CordaX500Name("Royal Dutch Shell", "The Hague", "NL"),
                        rpcUsers = listOf(user)
                ).getOrThrow()
        )

        // Supplier 2
        startWebserver(
                startNode(
                        providedName = CordaX500Name("General Electric", "Boston Massachusetts", "US"),
                        rpcUsers = listOf(user)
                ).getOrThrow()
        )

        // Funder 1
        startWebserver(
                startNode(
                        providedName = CordaX500Name("BNY Mellon", "New York", "US"),
                        rpcUsers = listOf(user)
                ).getOrThrow()
        )

        // Funder 2
        startWebserver(
                startNode(
                        providedName = CordaX500Name("JP Morgan Chase", "New York", "US"),
                        rpcUsers = listOf(user)
                ).getOrThrow()
        )

        waitForAllNodesToFinish()
    }
}
