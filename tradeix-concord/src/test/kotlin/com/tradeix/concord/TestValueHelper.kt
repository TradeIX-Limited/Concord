package com.tradeix.concord

import net.corda.core.contracts.Amount
import net.corda.core.contracts.UniqueIdentifier
import net.corda.testing.core.*
import java.math.BigDecimal
import java.time.Instant
import java.util.*

object TestValueHelper {

    // State Identity
    val EXTERNAL_ID get() = "TEST_EXTERNAL_ID"
    val LINEAR_ID get() = UniqueIdentifier(EXTERNAL_ID)

    val EXTERNAL_IDS get() = listOf("TEST_EXTERNAL_ID_1", "TEST_EXTERNAL_ID_2", "TEST_EXTERNAL_ID_3")
    val LINEAR_IDS get() = EXTERNAL_IDS.map { UniqueIdentifier(it) }

    // Node Identity
    val BUYER get() = TestIdentity(BOB_NAME)
    val BUYER_PUBKEY get() = BUYER.publicKey

    val SUPPLIER get() = TestIdentity(ALICE_NAME)
    val SUPPLIER_PUBKEY get() = SUPPLIER.publicKey

    val FUNDER get() = TestIdentity(DUMMY_BANK_A_NAME)
    val FUNDER_PUBKEY get() = FUNDER.publicKey

    val CONDUCTOR get() = TestIdentity(CHARLIE_NAME)
    val CONDUCTOR_PUBKEY get() = CONDUCTOR.publicKey

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

    // PurchaseOrder State Identity
    val PURCHASE_ORDER_EXTERNAL_IDS
        get() = listOf(
                "PURCHASE_ORDER_TEST_EXTERNAL_ID_1",
                "PURCHASE_ORDER_TEST_EXTERNAL_ID_2",
                "PURCHASE_ORDER_TEST_EXTERNAL_ID_3"
        )

    // Purchase Order References
    val PURCHASE_ORDER_REFERENCE = "PURCHASE_ORDER_REFERENCE"

    // Dates & Times
    val DATE_INSTANT_01 = Instant.parse("2000-01-01T00:00:00Z")
    val DATE_INSTANT_02 = Instant.parse("2000-01-02T00:00:00Z")
    val DATE_INSTANT_03 = Instant.parse("2000-01-03T00:00:00Z")
    val DATE_INSTANT_04 = Instant.parse("2000-01-04T00:00:00Z")
    val DATE_INSTANT_05 = Instant.parse("2000-01-05T00:00:00Z")
    val DATE_INSTANT_06 = Instant.parse("2000-01-06T00:00:00Z")
    val DATE_INSTANT_07 = Instant.parse("2000-01-07T00:00:00Z")
    val DATE_INSTANT_08 = Instant.parse("2000-01-08T00:00:00Z")
    val DATE_INSTANT_09 = Instant.parse("2000-01-09T00:00:00Z")

    // Shipping Information
    val PORT_OF_SHIPMENT = "PORT_OF_SHIPMENT"
    val DESCRIPTION_OF_GOODS = "DESCRIPTION_OF_GOODS"
    val DELIVERY_TERMS = "DELIVERY_TERMS"

    // Invoice Helper Data
    val INVOICE_VERSION = "INVOICE_VERSION"
    val TIX_INVOICE_VERSION = 1
    val INVOICE_NUMBER = "INVOICE_NUMBER"
    val INVOICE_TYPE = "INVOICE_TYPE"
    val REFERENCE = "REFERENCE"
    val OFFER_ID = 1
    val STATUS = "STATUS"
    val REJECTION_REASON = "REJECTION_REASON"
    val CANCELLED = false
    val ORIGINATION_NETWORK = "ORIGINATION_NETWORK"
    val HASH = "HASH"
    val SITE_ID = "SITE_ID"
    val PURCHASE_ORDER_NUMBER = "PURCHASE_ORDER_NUMBER"
    val PURCHASE_ORDER_ID = "PURCHASE_ORDER_ID"
    val COMPOSER_PROGRAM_ID = 1
}