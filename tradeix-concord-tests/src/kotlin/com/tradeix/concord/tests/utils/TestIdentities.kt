package com.tradeix.concord.tests.utils

import net.corda.core.identity.CordaX500Name
import net.corda.testing.core.*

object TestIdentities {
    val BUYER_1 = TestIdentity(CordaX500Name("TradeIX Test Buyer 1", "London", "GB"))
    val SUPPLIER_1 = TestIdentity(CordaX500Name("TradeIX Test Supplier 1", "London", "GB"))
    val FUNDER_1 = TestIdentity(CordaX500Name("TradeIX Test Funder 1", "London", "GB"))
    val CONDUCTOR_1 = TestIdentity(CordaX500Name("TradeIX Test Conductor 1", "London", "GB"))

    val OBLIGOR_1 = TestIdentity(CordaX500Name("TradeIX Test Obligor 1", "London", "GB"))
    val OBLIGEE_1 = TestIdentity(CordaX500Name("TradeIX Test Obligee 1", "London", "GB"))
    val GUARANTOR_1 = TestIdentity(CordaX500Name("TradeIX Test Guarantor 1", "London", "GB"))
}