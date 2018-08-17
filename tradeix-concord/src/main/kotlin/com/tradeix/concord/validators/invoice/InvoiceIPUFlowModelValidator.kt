package com.tradeix.concord.validators.invoice

import com.tradeix.concord.flowmodels.invoice.InvoiceIPUFlowModel
import com.tradeix.concord.validators.Validator

class InvoiceIPUFlowModelValidator(message: InvoiceIPUFlowModel)
    : Validator<InvoiceIPUFlowModel>(message) {

    companion object {
        private val EX_EXTERNAL_ID_REQUIRED = "Field 'externalId' is required."
    }

    override fun validate() {
        message.externalId ?: errors.add(EX_EXTERNAL_ID_REQUIRED)
    }
}