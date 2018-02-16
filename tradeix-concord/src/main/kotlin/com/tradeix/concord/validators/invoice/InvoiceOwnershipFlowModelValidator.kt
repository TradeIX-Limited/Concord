package com.tradeix.concord.validators.invoice

import com.tradeix.concord.flowmodels.invoice.InvoiceOwnershipFlowModel
import com.tradeix.concord.validators.Validator

class InvoiceOwnershipFlowModelValidator(message: InvoiceOwnershipFlowModel)
    : Validator<InvoiceOwnershipFlowModel>(message) {
    companion object {
        private val EX_EXTERNAL_IDS_REQUIRED = "Field 'externalId' is required."
        private val EX_NEW_OWNER_REQUIRED = "Field 'newOwner' is required."
    }
    override fun validate() {
        message.externalId ?: errors.add(EX_EXTERNAL_IDS_REQUIRED)
        message.newOwner ?: errors.add(EX_NEW_OWNER_REQUIRED)
    }
}