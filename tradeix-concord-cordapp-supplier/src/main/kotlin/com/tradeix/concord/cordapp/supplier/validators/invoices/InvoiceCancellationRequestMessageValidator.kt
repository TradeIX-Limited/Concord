package com.tradeix.concord.cordapp.supplier.validators.invoices

import com.tradeix.concord.cordapp.supplier.messages.invoices.InvoiceCancellationRequestMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.isNotNullEmptyOrBlank

class InvoiceCancellationRequestMessageValidator : ObjectValidator<InvoiceCancellationRequestMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<InvoiceCancellationRequestMessage>) {

        validationBuilder.property(InvoiceCancellationRequestMessage::externalId) {
            it.isNotNullEmptyOrBlank()
        }
    }
}