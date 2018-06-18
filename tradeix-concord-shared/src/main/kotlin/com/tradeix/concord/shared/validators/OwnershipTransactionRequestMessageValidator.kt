package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messages.OwnershipTransactionRequestMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.isNotEmpty
import com.tradeix.concord.shared.validation.extensions.isValidSecureHash
import com.tradeix.concord.shared.validation.extensions.isValidX500Name
import com.tradeix.concord.shared.validation.extensions.validateWith

class OwnershipTransactionRequestMessageValidator
    : ObjectValidator<OwnershipTransactionRequestMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<OwnershipTransactionRequestMessage>) {
        validationBuilder.property(OwnershipTransactionRequestMessage::assets, {
            it.isNotEmpty()
        })

        validationBuilder.collection(OwnershipTransactionRequestMessage::assets, {
            it.validateWith(InvoiceRequestMessageValidator())
        })

        validationBuilder.collection(OwnershipTransactionRequestMessage::attachments, {
            it.isValidSecureHash()
        })

        validationBuilder.collection(OwnershipTransactionRequestMessage::observers, {
            it.isValidX500Name()
        })
    }
}