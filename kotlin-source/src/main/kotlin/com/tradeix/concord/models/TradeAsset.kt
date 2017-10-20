package com.tradeix.concord.models

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
        PURCHASE_ORDER("Purchase Order")
    }
}