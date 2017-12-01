package com.tradeix.concord.validators

import com.tradeix.concord.flowmodels.TradeAssetCancellationFlowModel

class TradeAssetCancellationFlowModelValidator(message: TradeAssetCancellationFlowModel)
    : Validator<TradeAssetCancellationFlowModel>(message) {

    companion object {
        private val EX_EXTERNAL_ID_REQUIRED = "Field 'externalId' is required."
    }

    override fun validate() {
        message.externalId ?: errors.add(EX_EXTERNAL_ID_REQUIRED)
    }
}