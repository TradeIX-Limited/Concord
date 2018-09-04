package com.tradeix.concord

import net.corda.core.identity.CordaX500Name
import net.corda.core.utilities.getOrThrow
import net.corda.testing.driver.DriverParameters
import net.corda.testing.driver.driver
import net.corda.testing.node.NotarySpec
import net.corda.testing.node.User

class Main {
    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            driver(DriverParameters(
                    isDebug = true,
                    notarySpecs = listOf(NotarySpec(name = CordaX500Name("Notary", "London", "GB"))),
                    startNodesInProcess = true,
                    waitForAllNodesToFinish = true,
                    extraCordappPackagesToScan = listOf(
                            "net.corda.businessnetworks.membership.states",
                            "net.corda.businessnetworks.membership.bno",
                            "net.corda.businessnetworks.membership.member"))) {

                listOf(
                        CordaX500Name("BNO", "New York", "US"),
                        CordaX500Name("TradeIX", "London", "GB"),
                        CordaX500Name("TradeIX Test Supplier 1", "New York", "US"),
                        CordaX500Name("TradeIX Test Supplier 2", "Paris", "FR"),
                        CordaX500Name("TradeIX Test Supplier 3", "Madris", "ES"),
                        CordaX500Name("TradeIX Test Fake Supplier", "Cape Town", "SA"),
                        CordaX500Name("TradeIX Test Buyer 1", "Tokyo", "JP"),
                        CordaX500Name("TradeIX Test Funder 1", "Amsterdam", "NL")
                ).map {
                    startWebserver(startNode(
                            providedName = it,
                            rpcUsers = listOf(User("user1", "test", permissions = setOf("ALL")))
                    ).getOrThrow())
                }
            }
        }
    }
}
