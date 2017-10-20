package com.tradeix.concord.messages

import net.corda.core.identity.CordaX500Name
import org.junit.Test
import java.math.BigDecimal

class TradeAssetIssuanceRequestMessageTests {

    companion object {
        private val MOCK_IDENTITY = CordaX500Name("NewCo", "Ville", "GB")
    }

    @Test
    fun `Absence of supplier in message should result in error`() {
        val message = TradeAssetIssuanceRequestMessage(
                buyer = MOCK_IDENTITY,
                supplier = null,
                assetId = "MOCK_ASSET",
                value = BigDecimal.ONE,
                currency = "GBP"
        )

        assert(!message.isValid)
        assert(message.getValidationErrors().size == 1)
        assert(message.getValidationErrors().contains("Supplier is required for an issuance transaction"))
    }

    @Test
    fun `Absence of asset ID in message should result in error`() {
        val message = TradeAssetIssuanceRequestMessage(
                buyer = MOCK_IDENTITY,
                supplier = MOCK_IDENTITY,
                assetId = null,
                value = BigDecimal.ONE,
                currency = "GBP"
        )

        assert(!message.isValid)
        assert(message.getValidationErrors().size == 1)
        assert(message.getValidationErrors().contains("Asset ID is required for an issuance transaction"))
    }

    @Test
    fun `Absence of currency in message should result in error`() {
        val message = TradeAssetIssuanceRequestMessage(
                buyer = MOCK_IDENTITY,
                supplier = MOCK_IDENTITY,
                assetId = "MOCK_ASSET",
                value = BigDecimal.ONE,
                currency = null
        )

        assert(!message.isValid)
        assert(message.getValidationErrors().size == 1)
        assert(message.getValidationErrors().contains("Currency is required for an issuance transaction"))
    }

    @Test
    fun `Absence of value in message should result in error`() {
        val message = TradeAssetIssuanceRequestMessage(
                buyer = MOCK_IDENTITY,
                supplier = MOCK_IDENTITY,
                assetId = "MOCK_ASSET",
                value = null,
                currency = "GBP"
        )

        assert(!message.isValid)
        assert(message.getValidationErrors().size == 1)
        assert(message.getValidationErrors().contains("Value is required for an issuance transaction"))
    }

    @Test
    fun `Negative value in message should result in error`() {
        val message = TradeAssetIssuanceRequestMessage(
                buyer = MOCK_IDENTITY,
                supplier = MOCK_IDENTITY,
                assetId = "MOCK_ASSET",
                value = BigDecimal.ONE.negate(),
                currency = "GBP"
        )

        assert(!message.isValid)
        assert(message.getValidationErrors().size == 1)
        assert(message.getValidationErrors().contains("Value cannot be negative for an issuance transaction"))
    }
}