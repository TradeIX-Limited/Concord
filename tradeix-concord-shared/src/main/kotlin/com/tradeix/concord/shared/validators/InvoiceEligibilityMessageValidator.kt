package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messages.InvoiceEligibilityMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.isNotNull
import com.tradeix.concord.shared.validation.extensions.isNotNullEmptyOrBlank
import com.tradeix.concord.shared.validation.extensions.isValidX500Name

class InvoiceEligibilityMessageValidator : ObjectValidator<InvoiceEligibilityMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<InvoiceEligibilityMessage>) {

        validationBuilder.property(InvoiceEligibilityMessage::invoiceId, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(InvoiceEligibilityMessage::supplier, {
            it.isNotNullEmptyOrBlank()
            it.isValidX500Name()
        })

        validationBuilder.property(InvoiceEligibilityMessage::eligible, {
            it.isNotNull()
        })
    }
}