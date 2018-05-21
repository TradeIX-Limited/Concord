package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messagecontracts.CancellationMessage
import com.tradeix.concord.shared.validation.ObjectModelValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.isNotNullEmptyOrBlank

class CancellationMessageValidator : ObjectModelValidator<CancellationMessage>() {

    override fun onValidationBuilding(validationBuilder: ValidationBuilder<CancellationMessage>) {
        validationBuilder
                .property(CancellationMessage::externalId)
                .isNotNullEmptyOrBlank()
    }
}