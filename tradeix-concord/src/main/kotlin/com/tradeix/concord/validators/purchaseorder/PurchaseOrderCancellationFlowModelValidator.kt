package com.tradeix.concord.validators.purchaseorder

import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderCancellationFlowModel
import com.tradeix.concord.validators.Validator

class PurchaseOrderCancellationFlowModelValidator(message: PurchaseOrderCancellationFlowModel)
    : Validator<PurchaseOrderCancellationFlowModel>(message) {

    companion object {
        private val EX_EXTERNAL_ID_REQUIRED = "Field 'externalId' is required."
    }

    override fun validate() {
        message.externalId ?: errors.add(EX_EXTERNAL_ID_REQUIRED)
    }
}