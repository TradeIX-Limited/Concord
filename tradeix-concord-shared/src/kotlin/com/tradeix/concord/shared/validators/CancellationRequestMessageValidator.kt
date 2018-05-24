package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messages.CancellationRequestMessage
import com.tradeix.concord.shared.validation.ObjectModelValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.isNotNullEmptyOrBlank

class CancellationRequestMessageValidator : ObjectModelValidator<CancellationRequestMessage>() {

    override fun onValidationBuilding(validationBuilder: ValidationBuilder<CancellationRequestMessage>) {
        validationBuilder
                .property(CancellationRequestMessage::externalId)
                .isNotNullEmptyOrBlank()
    }
}