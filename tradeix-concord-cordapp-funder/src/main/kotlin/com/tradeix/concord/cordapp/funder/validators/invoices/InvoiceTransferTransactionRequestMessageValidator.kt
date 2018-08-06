package com.tradeix.concord.cordapp.funder.validators.invoices

import com.tradeix.concord.cordapp.funder.messages.invoices.InvoiceTransferTransactionRequestMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.isValidSecureHash
import com.tradeix.concord.shared.validation.extensions.isValidX500Name
import com.tradeix.concord.shared.validation.extensions.validateWith

class InvoiceTransferTransactionRequestMessageValidator : ObjectValidator<InvoiceTransferTransactionRequestMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<InvoiceTransferTransactionRequestMessage>) {

        validationBuilder.collection(InvoiceTransferTransactionRequestMessage::assets) {
            it.validateWith(InvoiceTransferRequestMessageValidator())
        }

        validationBuilder.collection(InvoiceTransferTransactionRequestMessage::attachments) {
            it.isValidSecureHash()
        }

        validationBuilder.collection(InvoiceTransferTransactionRequestMessage::observers) {
            it.isValidX500Name()
        }
    }
}
