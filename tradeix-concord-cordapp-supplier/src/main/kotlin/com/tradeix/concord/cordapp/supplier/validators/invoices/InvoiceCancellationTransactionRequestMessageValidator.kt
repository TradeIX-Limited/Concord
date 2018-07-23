package com.tradeix.concord.cordapp.supplier.validators.invoices

import com.tradeix.concord.cordapp.supplier.messages.invoices.InvoiceCancellationTransactionRequestMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.isValidSecureHash
import com.tradeix.concord.shared.validation.extensions.isValidX500Name
import com.tradeix.concord.shared.validation.extensions.validateWith

class InvoiceCancellationTransactionRequestMessageValidator : ObjectValidator<InvoiceCancellationTransactionRequestMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<InvoiceCancellationTransactionRequestMessage>) {

        validationBuilder.collection(InvoiceCancellationTransactionRequestMessage::assets) {
            it.validateWith(InvoiceTransactionRequestMessageValidator())
        }

        validationBuilder.collection(InvoiceCancellationTransactionRequestMessage::attachments) {
            it.isValidSecureHash()
        }

        validationBuilder.collection(InvoiceCancellationTransactionRequestMessage::observers) {
            it.isValidX500Name()
        }
    }
}