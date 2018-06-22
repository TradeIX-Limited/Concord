package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messages.fundingresponse.FundingResponseRejectMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.isNotNullEmptyOrBlank

class FundingResponseRejectMessageValidator
    : ObjectValidator<FundingResponseRejectMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<FundingResponseRejectMessage>) {

        validationBuilder.property(FundingResponseRejectMessage::externalId, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(FundingResponseRejectMessage::fundingResponseExternalId, {
            it.isNotNullEmptyOrBlank()
        })

    }
}
