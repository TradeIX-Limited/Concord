package com.tradeix.concord

import net.corda.core.identity.CordaX500Name
import net.corda.core.utilities.getOrThrow
import net.corda.node.services.transactions.ValidatingNotaryService
import net.corda.nodeapi.User
import net.corda.nodeapi.internal.ServiceInfo
import net.corda.testing.driver.driver

class Main {
    companion object {

        // RPC Users
        private val USER = User("user1", "test", permissions = setOf())

        // Nodes
        private val NOTARY = CordaX500Name("R3Net", "London", "GB")
        private val CONDUCTOR = CordaX500Name("TradeIX", "London", "GB")

        private val BUYER_1 = CordaX500Name("Exxon Mobil", "Irving", "US")
        private val BUYER_2 = CordaX500Name("Boeing", "Chicago", "US")

        private val SUPPLIER_1 = CordaX500Name("Royal Dutch Shell", "The Hague", "NL")
        private val SUPPLIER_2 = CordaX500Name("General Electric", "Boston", "US")

        private val FUNDER_1 = CordaX500Name("BNY Mellon", "New York", "US")
        private val FUNDER_2 = CordaX500Name("JP Morgan Chase", "New York", "US")


        @JvmStatic
        fun main(args: Array<String>) {
            driver(isDebug = true) {

                // Notary
                startNode(
                        providedName = NOTARY,
                        advertisedServices = setOf(ServiceInfo(ValidatingNotaryService.type)))

                val peerNodes = listOf(
                        CONDUCTOR,
                        BUYER_1,
                        BUYER_2,
                        SUPPLIER_1,
                        SUPPLIER_2,
                        FUNDER_1,
                        FUNDER_2
                )

                peerNodes.forEach {
                    startWebserver(startNode(
                            providedName = it,
                            rpcUsers = listOf(USER)).getOrThrow())
                }

                waitForAllNodesToFinish()
            }
        }
    }
}
