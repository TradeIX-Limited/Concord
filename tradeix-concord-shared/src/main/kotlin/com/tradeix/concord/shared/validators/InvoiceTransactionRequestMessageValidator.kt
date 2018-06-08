package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messages.InvoiceTransactionRequestMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.*

class InvoiceTransactionRequestMessageValidator : ObjectValidator<InvoiceTransactionRequestMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<InvoiceTransactionRequestMessage>) {

        validationBuilder.property(InvoiceTransactionRequestMessage::assets, {
            it.isNotEmpty()
        })

        validationBuilder.collection(InvoiceTransactionRequestMessage::assets, {
            it.validateWith(InvoiceRequestMessageValidator())
        })

        validationBuilder.collection(InvoiceTransactionRequestMessage::attachments, {
            it.isValidSecureHash()
        })

        validationBuilder.collection(InvoiceTransactionRequestMessage::observers, {
            it.isValidX500Name()
        })
    }
}