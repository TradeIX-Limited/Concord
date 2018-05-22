package com.tradeix.concord.cordapp.supplier

import net.corda.core.identity.CordaX500Name
import net.corda.core.utilities.getOrThrow
import net.corda.testing.driver.DriverParameters
import net.corda.testing.driver.driver
import net.corda.testing.node.User

class Application {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            driver(DriverParameters(isDebug = true, waitForAllNodesToFinish = true)) {
                startWebserver(startNode(
                        providedName = CordaX500Name("TradeIX Test Supplier 1", "New York", "US"),
                        rpcUsers = listOf(User("rpc_supplier", "rpc_password_123", permissions = setOf("ALL")))
                ).getOrThrow())
            }
        }
    }
}