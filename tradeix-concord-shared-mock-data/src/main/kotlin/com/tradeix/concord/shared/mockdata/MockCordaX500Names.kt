package com.tradeix.concord.shared.mockdata

import net.corda.core.identity.CordaX500Name

object MockCordaX500Names {

    val NOTARY_NAME = CordaX500Name("Notary", "London", "GB")
    val TRADEIX_NAME = CordaX500Name("TradeIX", "London", "GB")

    val SUPPLIER_1_NAME = CordaX500Name("TradeIX Mock Supplier 1", "London", "GB")
    val SUPPLIER_2_NAME = CordaX500Name("TradeIX Mock Supplier 2", "Paris", "FR")
    val SUPPLIER_3_NAME = CordaX500Name("TradeIX Mock Supplier 3", "New York", "US")

    val BUYER_1_NAME = CordaX500Name("TradeIX Mock Buyer 1", "Edinburgh", "GB")
    val BUYER_2_NAME = CordaX500Name("TradeIX Mock Buyer 2", "Nice", "FR")
    val BUYER_3_NAME = CordaX500Name("TradeIX Mock Buyer 3", "Los Angeles", "US")

    val FUNDER_1_NAME = CordaX500Name("TradeIX Mock Funder 1", "Madrid", "ES")
    val FUNDER_2_NAME = CordaX500Name("TradeIX Mock Funder 2", "Tokyo", "JP")
    val FUNDER_3_NAME = CordaX500Name("TradeIX Mock Funder 3", "Cape Town", "SA")

    val OBLIGOR_1_NAME = CordaX500Name("TradeIX Mock Obligor 1", "Johannesburg", "SA")
    val OBLIGOR_2_NAME = CordaX500Name("TradeIX Mock Obligor 2", "Okinawa", "JP")
    val OBLIGOR_3_NAME = CordaX500Name("TradeIX Mock Obligor 3", "Athens", "GR")

    val OBLIGEE_1_NAME = CordaX500Name("TradeIX Mock Obligee 1", "Mumbai", "IN")
    val OBLIGEE_2_NAME = CordaX500Name("TradeIX Mock Obligee 2", "Perth", "AU")
    val OBLIGEE_3_NAME = CordaX500Name("TradeIX Mock Obligee 3", "Aukland", "NZ")

    val GUARANTOR_1_NAME = CordaX500Name("TradeIX Mock Guarantor 1", "Shanghai", "CN")
    val GUARANTOR_2_NAME = CordaX500Name("TradeIX Mock Guarantor 2", "Ireland", "IR")
    val GUARANTOR_3_NAME = CordaX500Name("TradeIX Mock Guarantor 3", "Libya", "LY")
}