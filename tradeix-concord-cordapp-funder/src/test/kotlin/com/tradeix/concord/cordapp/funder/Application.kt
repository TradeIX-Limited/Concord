package com.tradeix.concord.cordapp.funder

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
                    portAllocation = PortAllocation.Incremental(10100),
                    debugPortAllocation = PortAllocation.Incremental(5106)
            )

            driver(parameters) {

                // Start the test funder node
                startNode(
                        providedName = CordaX500Name("Funder 1", "Madrid", "ES"),
                        rpcUsers = listOf(User("guest", "letmein", permissions = setOf("ALL")))
                ).getOrThrow()
            }
        }
    }
}