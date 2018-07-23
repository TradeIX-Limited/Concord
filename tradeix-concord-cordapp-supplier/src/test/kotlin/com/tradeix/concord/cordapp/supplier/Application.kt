package com.tradeix.concord.cordapp.supplier

import net.corda.core.identity.CordaX500Name
import net.corda.core.utilities.getOrThrow
import net.corda.testing.driver.DriverParameters
import net.corda.testing.driver.PortAllocation
import net.corda.testing.driver.driver
import net.corda.testing.node.User

class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val parameters = DriverParameters(
                    isDebug = true,
                    waitForAllNodesToFinish = true,
                    extraCordappPackagesToScan = listOf("com.tradeix.concord.shared.domain"),
                    portAllocation = PortAllocation.Incremental(10004),
                    debugPortAllocation = PortAllocation.Incremental(5005)
            )

            driver(parameters) {

                // Start the test supplier node
                startNode(
                        providedName = CordaX500Name("Supplier 1", "London", "GB"),
                        rpcUsers = listOf(User("guest", "letmein", permissions = setOf("ALL")))
                ).getOrThrow()
            }
        }
    }
}