package com.tradeix.concord.messages

import net.corda.core.contracts.Amount
import net.corda.core.identity.CordaX500Name
import net.corda.core.serialization.CordaSerializable
import java.lang.IllegalArgumentException
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

@CordaSerializable
data class TradeAssetIssuanceRequestMessage(
        val linearId: UUID = UUID.randomUUID(),
        val buyer: CordaX500Name?,
        val supplier: CordaX500Name?,
        val conductor: CordaX500Name = CONDUCTOR_X500_NAME,
        val assetId: String?,
        val value: BigDecimal?,
        val currency: String?) : RequestMessage() {

    companion object {
        private val CONDUCTOR_X500_NAME = CordaX500Name("TradeIX", "London", "GB")

        private val EX_SUPPLIER_MSG = "Supplier is required for an issuance transaction."
        private val EX_ASSET_ID_MSG = "Asset ID is required for an issuance transaction."
        private val EX_CURRENCY_MSG = "Currency is required for an issuance transaction."
        private val EX_VALUE_NEG_MSG = "Value cannot be negative for an issuance transaction."
        private val EX_VALUE_MSG = "Value is required for an issuance transaction."
    }

    val amount: Amount<Currency>
        get() = Amount.fromDecimal(
                displayQuantity = value ?: throw IllegalArgumentException(EX_VALUE_MSG),
                token = Currency.getInstance(currency ?: throw IllegalArgumentException(EX_CURRENCY_MSG))
        )

    override fun getValidationErrors(): ArrayList<String> {
        val result = ArrayList<String>()

        if (supplier == null) {
            result.add(EX_SUPPLIER_MSG)
        }

        if (assetId == null) {
            result.add(EX_ASSET_ID_MSG)
        }

        if (value == null) {
            result.add(EX_VALUE_MSG)
        }

        if (value != null && value < BigDecimal.ZERO) {
            result.add(EX_VALUE_NEG_MSG)
        }

        if (currency == null) {
            result.add(EX_CURRENCY_MSG)
        }

        return result
    }
}