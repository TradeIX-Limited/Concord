package com.tradeix.concord.cordapp.supplier.validators.invoices

import com.tradeix.concord.cordapp.supplier.messages.invoices.InvoiceTransactionRequestMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.*

class InvoiceTransactionRequestMessageValidator : ObjectValidator<InvoiceTransactionRequestMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<InvoiceTransactionRequestMessage>) {

        validationBuilder.property(InvoiceTransactionRequestMessage::assets) {
            it.isNotEmpty()
            it.distinct({invoice -> invoice.externalId}, "externalId")
        }

        validationBuilder.collection(InvoiceTransactionRequestMessage::assets) {
            it.validateWith(InvoiceRequestMessageValidator())
        }

        validationBuilder.collection(InvoiceTransactionRequestMessage::attachments) {
            it.isValidSecureHash()
        }

        validationBuilder.collection(InvoiceTransactionRequestMessage::observers) {
            it.isValidX500Name()
        }
    }
}