package com.tradeix.concord.cordapp.supplier.validators.fundingresponses

import com.tradeix.concord.cordapp.supplier.messages.fundingresponses.FundingResponseConfirmationRequestMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.isNotNull
import com.tradeix.concord.shared.validation.extensions.isNotNullEmptyOrBlank

class FundingResponseConfirmationRequestMessageAcceptanceValidator
    : ObjectValidator<FundingResponseConfirmationRequestMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<FundingResponseConfirmationRequestMessage>) {

        validationBuilder.property(FundingResponseConfirmationRequestMessage::externalId) {
            it.isNotNullEmptyOrBlank()
        }

        validationBuilder.property(FundingResponseConfirmationRequestMessage::bankAccount) {
            it.isNotNull()
        }
    }
}