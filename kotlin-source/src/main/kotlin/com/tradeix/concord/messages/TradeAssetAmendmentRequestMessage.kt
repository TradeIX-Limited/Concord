package com.tradeix.concord.messages

import net.corda.core.serialization.CordaSerializable
import java.math.BigDecimal
import java.util.*
import kotlin.collections.ArrayList

@CordaSerializable
data class TradeAssetAmendmentRequestMessage(
        val linearId: UUID?,
        val assetId: String?,
        val value: BigDecimal?,
        val currency: String?) : RequestMessage() {

    companion object {
        private val EX_LINEAR_ID_MSG = "Linear ID is required for an amendment transaction."
    }

    override fun getValidationErrors(): ArrayList<String> {
        val result = ArrayList<String>()

        linearId ?: result.add(EX_LINEAR_ID_MSG)

        return result
    }
}