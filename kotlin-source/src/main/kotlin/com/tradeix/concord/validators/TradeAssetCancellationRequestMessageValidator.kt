package com.tradeix.concord.validators

import com.tradeix.concord.messages.TradeAssetCancellationRequestMessage

class TradeAssetCancellationRequestMessageValidator(message: TradeAssetCancellationRequestMessage)
    : MessageValidator<TradeAssetCancellationRequestMessage>(message) {

    companion object {
        private val EX_CORRELATION_ID_MSG = "Correlation ID is required for a cancellation transaction."
        private val EX_EXTERNAL_ID_MSG = "External ID is required for a cancellation transaction."
    }

    override fun getValidationErrorMessages(): ArrayList<String> {
        val result = ArrayList<String>()

        message.correlationId ?: result.add(EX_CORRELATION_ID_MSG)
        message.externalId ?: result.add(EX_EXTERNAL_ID_MSG)

        return result
    }
}