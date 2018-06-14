package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messages.InvoiceEligibilityRequestMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.isNotNull
import com.tradeix.concord.shared.validation.extensions.isNotNullEmptyOrBlank
import com.tradeix.concord.shared.validation.extensions.isValidX500Name

class InvoiceEligibilityMessageValidator : ObjectValidator<InvoiceEligibilityRequestMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<InvoiceEligibilityRequestMessage>) {

        validationBuilder.property(InvoiceEligibilityRequestMessage::invoiceId, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(InvoiceEligibilityRequestMessage::supplier, {
            it.isNotNullEmptyOrBlank()
            it.isValidX500Name()
        })

        validationBuilder.property(InvoiceEligibilityRequestMessage::eligible, {
            it.isNotNull()
        })
    }
}