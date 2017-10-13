package com.tradeix.concord.models

import net.corda.core.contracts.Amount
import net.corda.core.serialization.CordaSerializable
import java.util.*

@CordaSerializable
data class TradeAsset(
        val assetId: String,
        val status: String,
        val amount: Amount<Currency>) {
    companion object {
        val STATE_ISSUED = "ISSUED"
    }
}