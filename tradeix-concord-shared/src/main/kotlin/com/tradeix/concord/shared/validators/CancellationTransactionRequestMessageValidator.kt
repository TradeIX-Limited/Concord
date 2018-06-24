package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messages.CancellationTransactionRequestMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.isNotEmpty
import com.tradeix.concord.shared.validation.extensions.isValidSecureHash
import com.tradeix.concord.shared.validation.extensions.isValidX500Name
import com.tradeix.concord.shared.validation.extensions.validateWith

class CancellationTransactionRequestMessageValidator
    : ObjectValidator<CancellationTransactionRequestMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<CancellationTransactionRequestMessage>) {

        validationBuilder.property(CancellationTransactionRequestMessage::assets, {
            it.isNotEmpty()
        })

        validationBuilder.collection(CancellationTransactionRequestMessage::assets, {
            it.validateWith(CancellationRequestMessageValidator())
        })

        validationBuilder.collection(CancellationTransactionRequestMessage::attachments, {
            it.isValidSecureHash()
        })

        validationBuilder.collection(CancellationTransactionRequestMessage::observers, {
            it.isValidX500Name()
        })
    }
}