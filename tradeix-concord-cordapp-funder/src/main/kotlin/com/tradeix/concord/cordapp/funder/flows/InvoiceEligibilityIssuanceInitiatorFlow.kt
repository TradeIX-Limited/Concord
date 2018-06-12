package com.tradeix.concord.cordapp.funder.flows

import com.tradeix.concord.shared.messages.InvoiceEligibilityTransactionRequestMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.isNotEmpty
import com.tradeix.concord.shared.validation.extensions.validateWith
import com.tradeix.concord.shared.validators.InvoiceEligibilityMessageValidator

class InvoiceEligibilityIssuanceInitiatorFlow : ObjectValidator<InvoiceEligibilityTransactionRequestMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<InvoiceEligibilityTransactionRequestMessage>) {

        validationBuilder.property(InvoiceEligibilityTransactionRequestMessage::assets, {
            it.isNotEmpty()
        })

        validationBuilder.collection(InvoiceEligibilityTransactionRequestMessage::assets, {
            it.validateWith(InvoiceEligibilityMessageValidator())
        })
    }
}