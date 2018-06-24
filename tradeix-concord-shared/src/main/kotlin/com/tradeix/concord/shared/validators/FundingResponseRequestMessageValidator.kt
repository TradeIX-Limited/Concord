package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messages.fundingresponse.FundingResponseRequestMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.*
import java.math.BigDecimal

class FundingResponseRequestMessageValidator
    : ObjectValidator<FundingResponseRequestMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<FundingResponseRequestMessage>) {

        validationBuilder.property(FundingResponseRequestMessage::externalId, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(FundingResponseRequestMessage::invoiceExternalIds, {
            it.isNotNull()
            it.isNotEmpty()
        })

        validationBuilder.collection(FundingResponseRequestMessage::invoiceExternalIds, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(FundingResponseRequestMessage::supplier, {
            it.isNotNull()
            it.isValidX500Name()
        })

        validationBuilder.property(FundingResponseRequestMessage::funder, {
            it.isValidX500Name()
        })

        validationBuilder.property(FundingResponseRequestMessage::purchaseValue, {
            it.isNotNull()
            it.isGreaterThan(BigDecimal.ZERO)
        })

        validationBuilder.property(FundingResponseRequestMessage::currency, {
            it.isNotNull()
            it.isValidCurrencyCode()
        })
    }
}
