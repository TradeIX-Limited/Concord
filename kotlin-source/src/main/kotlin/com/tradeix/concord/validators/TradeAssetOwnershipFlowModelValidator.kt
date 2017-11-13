package com.tradeix.concord.validators

import com.tradeix.concord.flowmodels.TradeAssetOwnershipFlowModel

class TradeAssetOwnershipFlowModelValidator(message: TradeAssetOwnershipFlowModel)
    : Validator<TradeAssetOwnershipFlowModel>(message) {

    companion object {
        private val EX_EXTERNAL_IDS_REQUIRED = "Field 'externalIds' is required."
        private val EX_EXTERNAL_IDS_NOT_EMPTY = "Field 'externalIds' must have at least one value."
        private val EX_NEW_OWNER_REQUIRED = "Field 'newOwner' is required."
    }

    override fun validate() {
        message.externalIds ?: errors.add(EX_EXTERNAL_IDS_REQUIRED)
        message.newOwner ?: errors.add(EX_NEW_OWNER_REQUIRED)

        if (message.externalIds != null && message.externalIds.isEmpty()) {
            errors.add(EX_EXTERNAL_IDS_NOT_EMPTY)
        }
    }
}