package com.tradeix.concord.validators

import com.tradeix.concord.messages.TradeAssetOwnershipRequestMessage

class TradeAssetOwnershipRequestMessageValidator(message: TradeAssetOwnershipRequestMessage)
    : MessageValidator<TradeAssetOwnershipRequestMessage>(message) {

    companion object {
        private val EX_CORRELATION_ID_MSG = "Correlation ID is required for an ownership transaction."
        private val EX_EXTERNAL_IDS_MSG = "External ID is required for an ownership transaction."
        private val EX_NEW_OWNER_MSG = "New owner is required for an ownership transaction."
    }

    override fun getValidationErrorMessages(): ArrayList<String> {
        val result = ArrayList<String>()

        message.correlationId ?: result.add(EX_CORRELATION_ID_MSG)
        message.externalIds ?: result.add(EX_EXTERNAL_IDS_MSG)
        message.newOwner ?: result.add(EX_NEW_OWNER_MSG)

        return result
    }
}