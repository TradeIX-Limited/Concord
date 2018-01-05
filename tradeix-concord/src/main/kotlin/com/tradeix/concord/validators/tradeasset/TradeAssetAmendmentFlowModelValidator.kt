package com.tradeix.concord.validators.tradeasset

import com.tradeix.concord.flowmodels.tradeasset.TradeAssetAmendmentFlowModel
import com.tradeix.concord.validators.Validator

class TradeAssetAmendmentFlowModelValidator(message: TradeAssetAmendmentFlowModel)
    : Validator<TradeAssetAmendmentFlowModel>(message) {

    companion object {
        private val EX_EXTERNAL_ID_REQUIRED = "Field 'externalId' is required."
    }

    override fun validate() {
        message.externalId ?: errors.add(EX_EXTERNAL_ID_REQUIRED)
    }
}