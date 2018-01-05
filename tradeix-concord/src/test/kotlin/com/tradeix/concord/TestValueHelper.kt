package com.tradeix.concord

import net.corda.core.contracts.Amount
import net.corda.core.contracts.UniqueIdentifier
import net.corda.testing.*
import java.math.BigDecimal
import java.util.*

object TestValueHelper {

    // State Identity
    val EXTERNAL_ID get() = "TEST_EXTERNAL_ID"
    val LINEAR_ID get() = UniqueIdentifier(EXTERNAL_ID)

    val EXTERNAL_IDS get() = listOf("TEST_EXTERNAL_ID_1", "TEST_EXTERNAL_ID_2", "TEST_EXTERNAL_ID_3")
    val LINEAR_IDS get() = EXTERNAL_IDS.map { UniqueIdentifier(it) }

    // Node Identity
    val BUYER get() = BOB
    val BUYER_PUBKEY get() = BOB_PUBKEY
    val SUPPLIER get() = ALICE
    val SUPPLIER_PUBKEY get() = ALICE_PUBKEY
    val FUNDER get() = DUMMY_BANK_A
    val FUNDER_PUBKEY get() = DUMMY_BANK_A_KEY.public
    val CONDUCTOR get() = CHARLIE
    val CONDUCTOR_PUBKEY get() = CHARLIE_PUBKEY

    // Values
    val POSITIVE_ONE get() = BigDecimal.ONE.setScale(2)
    val POSITIVE_TEN get() = BigDecimal.TEN.setScale(2)
    val NEGATIVE_ONE get() = BigDecimal.ONE.setScale(2).negate()
    val NEGATIVE_TEN get() = BigDecimal.TEN.setScale(2).negate()

    // Currency Codes
    val POUNDS get() = "GBP"
    val DOLLARS get() = "USD"
    val EUROS get() = "EUR"

    // Currencies
    val GBP get() = Currency.getInstance(POUNDS)
    val USD get() = Currency.getInstance(DOLLARS)
    val EUR get() = Currency.getInstance(EUROS)

    // Amounts
    val ONE_POUND get() = Amount.fromDecimal(POSITIVE_ONE, GBP)
    val TEN_POUNDS get() = Amount.fromDecimal(POSITIVE_TEN, GBP)
    val ONE_DOLLAR get() = Amount.fromDecimal(POSITIVE_ONE, USD)
    val TEN_DOLLARS get() = Amount.fromDecimal(POSITIVE_TEN, USD)
    val ONE_EURO get() = Amount.fromDecimal(POSITIVE_ONE, EUR)
    val TEN_EUROS get() = Amount.fromDecimal(POSITIVE_TEN, EUR)

    // Attachments
    val ATTACHMENT get() = "src/test/resources/good.jar"

    //Statuses
    val STATUS_INVOICE get() = "INVOICE"
    val STATUS_PURCHASE_ORDER get() = "PURCHASE_ORDER"
    val NOT_A_VALID_STATUS get() = "THIS_STATUS_IS_INVALID"

    // Hashes
    val NOT_A_VALID_HASH get() = "THIS_IS_NOT_A_VALID_HASH"
}