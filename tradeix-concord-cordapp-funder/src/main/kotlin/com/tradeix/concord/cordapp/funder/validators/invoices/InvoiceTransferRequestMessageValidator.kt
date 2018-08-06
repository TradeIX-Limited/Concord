package com.tradeix.concord.cordapp.funder.validators.invoices

import com.tradeix.concord.cordapp.funder.messages.invoices.InvoiceTransferRequestMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.isNotNullEmptyOrBlank
import com.tradeix.concord.shared.validation.extensions.isValidX500Name

class InvoiceTransferRequestMessageValidator : ObjectValidator<InvoiceTransferRequestMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<InvoiceTransferRequestMessage>) {

        validationBuilder.property(InvoiceTransferRequestMessage::externalId) {
            it.isNotNullEmptyOrBlank()
        }

        validationBuilder.property(InvoiceTransferRequestMessage::owner) {
            it.isNotNullEmptyOrBlank()
            it.isValidX500Name()
        }
    }
}