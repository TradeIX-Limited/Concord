package com.tradeix.concord.messages

import java.util.UUID

data class TradeAssetCancellationRequestMessage(
        val linearId: UUID?) : RequestMessage() {

    companion object {
        private val EX_LINEAR_ID_MSG = "Linear ID is required for a cancellation transaction."
    }

    override fun getValidationErrors(): ArrayList<String> {
        val result = ArrayList<String>()

        linearId ?: result.add(EX_LINEAR_ID_MSG)

        return result
    }
}