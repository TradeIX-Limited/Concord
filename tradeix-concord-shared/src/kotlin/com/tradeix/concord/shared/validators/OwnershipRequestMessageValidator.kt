package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messages.OwnershipRequestMessage
import com.tradeix.concord.shared.validation.ObjectModelValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.isNotNullEmptyOrBlank
import com.tradeix.concord.shared.validation.isValidCordaX500Name

class OwnershipRequestMessageValidator : ObjectModelValidator<OwnershipRequestMessage>() {

    override fun onValidationBuilding(validationBuilder: ValidationBuilder<OwnershipRequestMessage>) {
        validationBuilder
                .property(OwnershipRequestMessage::externalId)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(OwnershipRequestMessage::owner)
                .isNotNullEmptyOrBlank()
                .isValidCordaX500Name()
    }
}