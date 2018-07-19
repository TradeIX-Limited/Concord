package com.tradeix.concord.cordapp.funder.validators.fundingresponses

import com.tradeix.concord.cordapp.funder.messages.fundingresponses.FundingResponseIssuanceRequestMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.*
import java.math.BigDecimal

class FundingResponseIssuanceRequestMessageValidator
    : ObjectValidator<FundingResponseIssuanceRequestMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<FundingResponseIssuanceRequestMessage>) {

        validationBuilder.property(FundingResponseIssuanceRequestMessage::externalId) {
            it.isNotNullEmptyOrBlank()
        }

        validationBuilder.property(FundingResponseIssuanceRequestMessage::invoiceExternalIds) {
            it.isNotNull()
            it.isNotEmpty()
        }

        validationBuilder.collection(FundingResponseIssuanceRequestMessage::invoiceExternalIds) {
            it.isNotNullEmptyOrBlank()
        }

        validationBuilder.property(FundingResponseIssuanceRequestMessage::supplier) {
            it.isNotNull()
            it.isValidX500Name()
        }

        validationBuilder.property(FundingResponseIssuanceRequestMessage::funder) {
            it.isValidX500Name()
        }

        validationBuilder.property(FundingResponseIssuanceRequestMessage::purchaseValue) {
            it.isNotNull()
            it.isGreaterThan(BigDecimal.ZERO)
        }

        validationBuilder.property(FundingResponseIssuanceRequestMessage::currency) {
            it.isNotNull()
            it.isValidCurrencyCode()
        }

        validationBuilder.property(FundingResponseIssuanceRequestMessage::advanceInvoiceValue) {
            it.isNotNull()
            it.isGreaterThan(BigDecimal.ZERO)
        }

        validationBuilder.property(FundingResponseIssuanceRequestMessage::discountValue) {
            it.isNotNull()
            it.isGreaterThan(BigDecimal.ZERO)
        }

        validationBuilder.property(FundingResponseIssuanceRequestMessage::baseRate) {
            it.isNotNull()
            it.isGreaterThan(BigDecimal.ZERO)
        }
    }
}