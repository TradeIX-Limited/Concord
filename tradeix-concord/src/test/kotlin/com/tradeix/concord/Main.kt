package com.tradeix.concord

import net.corda.core.identity.CordaX500Name
import net.corda.core.utilities.getOrThrow
import net.corda.testing.driver.*
import net.corda.testing.node.User

class Main {
    companion object {

        // RPC Users
        private val USER = User("user1", "test", permissions = setOf("ALL"))

        // Nodes
        private val NOTARY = CordaX500Name("Controller", "London", "GB")
        private val CONDUCTOR = CordaX500Name("TradeIX", "London", "GB")

        private val TEST_NODE_1 = CordaX500Name("TradeIXFakeSupplier", "London", "GB")
        private val TEST_NODE_4 = CordaX500Name("TradeIXTestSupplier", "London", "GB")
        private val TEST_NODE_2 = CordaX500Name("TradeIXTestSupplier1", "London", "GB")
        private val TEST_NODE_3 = CordaX500Name("TradeIXTestSupplier2", "London", "GB")
        private val TEST_NODE_5 = CordaX500Name("TradeIXTestFunder", "London", "GB")
        private val TEST_NODE_6 = CordaX500Name("TradeIXTestBuyer", "London", "GB")

        @JvmStatic
        fun main(args: Array<String>) {
            driver(DriverParameters(isDebug = true, waitForAllNodesToFinish = true)) {

                // Notary
                startNode(NodeParameters(providedName = NOTARY))

                val names = listOf(
                        CONDUCTOR,
                        TEST_NODE_1,
                        TEST_NODE_2,
                        TEST_NODE_3,
                        TEST_NODE_4,
                        TEST_NODE_5,
                        TEST_NODE_6
                )

                names.map {
                    val node = startNode(
                            providedName = it,
                            rpcUsers = listOf(USER)
                    ).getOrThrow()

                    startWebserver(node)
                }
            }
        }
    }
}
