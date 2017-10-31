package com.tradeix.concord.validators

import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage
import com.tradeix.concord.models.TradeAsset
import net.corda.core.crypto.SecureHash
import java.math.BigDecimal

class TradeAssetIssuanceRequestMessageValidator(message: TradeAssetIssuanceRequestMessage)
    : MessageValidator<TradeAssetIssuanceRequestMessage>(message) {

    companion object {
        private val EX_CORRELATION_ID_MSG = "Correlation ID is required for an issuance transaction."
        private val EX_EXTERNAL_ID_MSG = "External ID is required for an issuance transaction."
        private val EX_STATUS_MSG = "Status is required for an issuance transaction."
        private val EX_INVALID_STATUS_MSG = "Status is required for an issuance transaction."
        private val EX_SUPPLIER_MSG = "Supplier is required for an issuance transaction."
        private val EX_CURRENCY_MSG = "Currency is required for an issuance transaction."
        private val EX_VALUE_NEG_MSG = "Value cannot be negative for an issuance transaction."
        private val EX_VALUE_MSG = "Value is required for an issuance transaction."
        private val EX_ATTACHMENT_HASH_MSG = "Invalid Secure hash for Supporting Document."
    }

    override fun getValidationErrorMessages(): ArrayList<String> {
        val result = ArrayList<String>()

        message.correlationId ?: result.add(EX_CORRELATION_ID_MSG)
        message.externalId ?: result.add(EX_EXTERNAL_ID_MSG)
        message.supplier ?: result.add(EX_SUPPLIER_MSG)
        message.status ?: result.add(EX_STATUS_MSG)
        message.value ?: result.add(EX_VALUE_MSG)
        message.currency ?: result.add(EX_CURRENCY_MSG)

        if(message.status != null) {
            try {
                TradeAsset.TradeAssetStatus.valueOf(message.status)
            } catch (ex: Throwable) {
                result.add(EX_INVALID_STATUS_MSG)
            }
        }

        if (message.value != null && message.value < BigDecimal.ZERO) {
            result.add(EX_VALUE_NEG_MSG)
        }

        if (message.attachmentId != null) {
            try {
                SecureHash.parse(message.attachmentId)
            } catch (e: IllegalArgumentException) {
                result.add(EX_ATTACHMENT_HASH_MSG)
            }
        }

        return result
    }
}