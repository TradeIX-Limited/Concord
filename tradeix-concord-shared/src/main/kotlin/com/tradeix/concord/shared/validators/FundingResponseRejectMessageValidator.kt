package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messages.fundingresponse.FundingResponseRejectionRequestMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.isNotNullEmptyOrBlank

class FundingResponseRejectMessageValidator
    : ObjectValidator<FundingResponseRejectionRequestMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<FundingResponseRejectionRequestMessage>) {

        validationBuilder.property(FundingResponseRejectionRequestMessage::externalId, {
            it.isNotNullEmptyOrBlank()
        })
    }
}
