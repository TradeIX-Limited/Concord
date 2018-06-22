package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messages.fundingresponse.FundingResponseAcceptMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.*

class FundingResponseAcceptMessageValidator
    : ObjectValidator<FundingResponseAcceptMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<FundingResponseAcceptMessage>) {

        validationBuilder.property(FundingResponseAcceptMessage::externalId, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(FundingResponseAcceptMessage::fundingResponseExternalId, {
            it.isNotNullEmptyOrBlank()
        })

    }
}
