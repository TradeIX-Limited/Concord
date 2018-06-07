package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messages.OwnershipRequestMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.isNotNullEmptyOrBlank
import com.tradeix.concord.shared.validation.extensions.isValidX500Name

class OwnershipRequestMessageValidator : ObjectValidator<OwnershipRequestMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<OwnershipRequestMessage>) {

        validationBuilder.property(OwnershipRequestMessage::externalId, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(OwnershipRequestMessage::owner, {
            it.isNotNullEmptyOrBlank()
            it.isValidX500Name()
        })

    }
}