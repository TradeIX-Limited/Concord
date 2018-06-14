package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messages.InvoiceEligibilityTransactionRequestMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.isNotEmpty
import com.tradeix.concord.shared.validation.extensions.validateWith

class InvoiceEligibilityTransactionRequestMessageValidator
    : ObjectValidator<InvoiceEligibilityTransactionRequestMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<InvoiceEligibilityTransactionRequestMessage>) {

        validationBuilder.property(InvoiceEligibilityTransactionRequestMessage::assets, {
            it.isNotEmpty()
        })

        validationBuilder.collection(InvoiceEligibilityTransactionRequestMessage::assets, {
            it.validateWith(InvoiceEligibilityMessageValidator())
        })
    }
}