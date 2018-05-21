package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messagecontracts.OwnershipMessage
import com.tradeix.concord.shared.validation.ObjectModelValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.isNotNullEmptyOrBlank
import com.tradeix.concord.shared.validation.isValidCordaX500Name

class OwnershipMessageValidator : ObjectModelValidator<OwnershipMessage>() {

    override fun onValidationBuilding(validationBuilder: ValidationBuilder<OwnershipMessage>) {
        validationBuilder
                .property(OwnershipMessage::externalId)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(OwnershipMessage::owner)
                .isNotNullEmptyOrBlank()
                .isValidCordaX500Name()
    }
}