package com.tradeix.concord.messages

import com.tradeix.concord.models.TradeAsset
import net.corda.core.contracts.Amount
import net.corda.core.crypto.SecureHash
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
        val status: String?,
        val assetId: String?,
        val value: BigDecimal?,
        val currency: String?,
        val attachmentHash: String?) : RequestMessage() {

    companion object {
        private val CONDUCTOR_X500_NAME = CordaX500Name("TradeIX", "London", "GB")

        private val EX_STATUS_MSG = "Status is required for an issuance transaction."
        private val EX_INVALID_STATUS_MSG = "Status is required for an issuance transaction."
        private val EX_SUPPLIER_MSG = "Supplier is required for an issuance transaction."
        private val EX_ASSET_ID_MSG = "Asset ID is required for an issuance transaction."
        private val EX_CURRENCY_MSG = "Currency is required for an issuance transaction."
        private val EX_VALUE_NEG_MSG = "Value cannot be negative for an issuance transaction."
        private val EX_VALUE_MSG = "Value is required for an issuance transaction."
        private val EX_ATTACHMENT_HASH_MSG = "Invalid Secure hash for Supporting Document."
    }

    val amount: Amount<Currency>
        get() = Amount.fromDecimal(
                displayQuantity = value ?: throw IllegalArgumentException(EX_VALUE_MSG),
                token = Currency.getInstance(currency ?: throw IllegalArgumentException(EX_CURRENCY_MSG))
        )

    override fun getValidationErrors(): ArrayList<String> {
        val result = ArrayList<String>()

        supplier ?: result.add(EX_SUPPLIER_MSG)
        status ?: result.add(EX_STATUS_MSG)
        assetId ?: result.add(EX_ASSET_ID_MSG)
        value ?: result.add(EX_VALUE_MSG)
        currency ?: result.add(EX_CURRENCY_MSG)

        if(status != null) {
            try {
                TradeAsset.TradeAssetStatus.valueOf(status)
            } catch (ex: Throwable) {
                result.add(EX_INVALID_STATUS_MSG)
            }
        }

        if (value != null && value < BigDecimal.ZERO) {
            result.add(EX_VALUE_NEG_MSG)
        }

        if (attachmentHash != null) {
            try {
                SecureHash.parse(attachmentHash)
            } catch (e: IllegalArgumentException) {
                result.add(EX_ATTACHMENT_HASH_MSG)
            }
        }

        return result
    }
}