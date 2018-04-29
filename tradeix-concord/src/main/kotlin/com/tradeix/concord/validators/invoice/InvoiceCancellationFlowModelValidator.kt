package com.tradeix.concord.validators.invoice

import com.tradeix.concord.flowmodels.invoice.InvoiceCancellationFlowModel
import com.tradeix.concord.validators.Validator

class InvoiceCancellationFlowModelValidator(message: InvoiceCancellationFlowModel)
    : Validator<InvoiceCancellationFlowModel>(message) {

    companion object {
        private val EX_EXTERNAL_ID_REQUIRED = "Field 'externalId' is required."
    }

    override fun validate() {
        message.externalId ?: errors.add(EX_EXTERNAL_ID_REQUIRED)
    }
}