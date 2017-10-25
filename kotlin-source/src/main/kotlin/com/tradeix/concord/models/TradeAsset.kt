package com.tradeix.concord.models

import com.tradeix.concord.exceptions.ValidationException
import net.corda.core.contracts.Amount
import net.corda.core.serialization.CordaSerializable
import java.util.*

@CordaSerializable
data class TradeAsset(
        val assetId: String,
        val status: TradeAssetStatus,
        val amount: Amount<Currency>) {

    @CordaSerializable
    enum class TradeAssetStatus(val description: String) {
        INVOICE("Invoice"),
        PURCHASE_ORDER("Purchase Order");
        companion object {
            private val map = TradeAssetStatus.values().associateBy (TradeAssetStatus::description)
            fun of(type: String) = map[type]?: throw ValidationException(validationErrors = arrayListOf("Incorrect Input - ${type}"))
            fun isValid(type: String) = map.containsKey(type)
        }
        override fun toString() = description
    }
}