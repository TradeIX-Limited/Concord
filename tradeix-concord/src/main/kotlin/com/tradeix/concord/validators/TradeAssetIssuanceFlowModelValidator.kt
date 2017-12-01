package com.tradeix.concord.validators

import com.tradeix.concord.flowmodels.TradeAssetIssuanceFlowModel
import com.tradeix.concord.models.TradeAsset
import net.corda.core.crypto.SecureHash
import java.math.BigDecimal

class TradeAssetIssuanceFlowModelValidator(message: TradeAssetIssuanceFlowModel)
    : Validator<TradeAssetIssuanceFlowModel>(message) {

    companion object {
        private val EX_EXTERNAL_ID_REQUIRED = "Field 'externalId' is required."
        private val EX_STATUS_REQUIRED = "Field 'status' is required."
        private val EX_STATUS_INVALID = "Invalid status value."
        private val EX_SUPPLIER_REQUIRED = "Field 'supplier' is required."
        private val EX_CURRENCY_REQUIRED = "Field 'currency' is required."
        private val EX_VALUE_REQUIRED = "Field 'value' is required."
        private val EX_VALUE_NEGATIVE = "Field 'value' cannot be negative."
        private val EX_INVALID_ATTACHMENT = "Invalid Secure hash for attachment."
    }

    override fun validate() {
        message.externalId ?: errors.add(EX_EXTERNAL_ID_REQUIRED)
        message.supplier ?: errors.add(EX_SUPPLIER_REQUIRED)
        message.status ?: errors.add(EX_STATUS_REQUIRED)
        message.value ?: errors.add(EX_VALUE_REQUIRED)
        message.currency ?: errors.add(EX_CURRENCY_REQUIRED)

        if (message.status != null) {
            try {
                TradeAsset.TradeAssetStatus.valueOf(message.status)
            } catch (ex: Throwable) {
                errors.add(EX_STATUS_INVALID)
            }
        }

        if (message.value != null && message.value < BigDecimal.ZERO) {
            errors.add(EX_VALUE_NEGATIVE)
        }

        if (message.attachmentId != null) {
            try {
                SecureHash.parse(message.attachmentId)
            } catch (e: IllegalArgumentException) {
                errors.add(EX_INVALID_ATTACHMENT)
            }
        }
    }
}