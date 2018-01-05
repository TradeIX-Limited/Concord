package com.tradeix.concord.validators.tradeasset

import com.tradeix.concord.flowmodels.tradeasset.TradeAssetCancellationFlowModel
import com.tradeix.concord.validators.Validator

class TradeAssetCancellationFlowModelValidator(message: TradeAssetCancellationFlowModel)
    : Validator<TradeAssetCancellationFlowModel>(message) {

    companion object {
        private val EX_EXTERNAL_ID_REQUIRED = "Field 'externalId' is required."
    }

    override fun validate() {
        message.externalId ?: errors.add(EX_EXTERNAL_ID_REQUIRED)
    }
}