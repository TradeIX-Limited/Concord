package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messages.CancellationRequestMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.isNotNullEmptyOrBlank

class CancellationRequestMessageValidator : ObjectValidator<CancellationRequestMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<CancellationRequestMessage>) {

        validationBuilder.property(CancellationRequestMessage::externalId, {
            it.isNotNullEmptyOrBlank()
        })
    }
}