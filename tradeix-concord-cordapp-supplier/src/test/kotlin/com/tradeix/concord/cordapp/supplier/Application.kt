package com.tradeix.concord.cordapp.supplier

import com.tradeix.concord.shared.mockdata.MockCordaX500Names.SUPPLIER_1_NAME
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
                    portAllocation = PortAllocation.Incremental(10000),
                    debugPortAllocation = PortAllocation.Incremental(5006)
            )

            driver(parameters) {

                // Start the test supplier node
                startNode(
                        providedName = SUPPLIER_1_NAME,
                        rpcUsers = listOf(User("user1", "test", permissions = setOf("ALL")))
                ).getOrThrow()
            }
        }
    }
}