package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messages.fundingresponse.FundingResponseAcceptanceRequestMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.isNotNullEmptyOrBlank

class FundingResponseAcceptMessageValidator
    : ObjectValidator<FundingResponseAcceptanceRequestMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<FundingResponseAcceptanceRequestMessage>) {

        validationBuilder.property(FundingResponseAcceptanceRequestMessage::externalId, {
            it.isNotNullEmptyOrBlank()
        })
    }
}
