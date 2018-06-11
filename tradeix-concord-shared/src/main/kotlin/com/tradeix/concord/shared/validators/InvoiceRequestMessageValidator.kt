package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messages.invoices.InvoiceMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.*
import java.math.BigDecimal

class InvoiceRequestMessageValidator : ObjectValidator<InvoiceMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<InvoiceMessage>) {

        validationBuilder.property(InvoiceMessage::externalId, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(InvoiceMessage::buyer, {
            it.isValidX500Name()
        })

        validationBuilder.property(InvoiceMessage::supplier, {
            it.isValidX500Name()
        })

        validationBuilder.property(InvoiceMessage::invoiceNumber, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(InvoiceMessage::reference, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(InvoiceMessage::dueDate, {
            it.isNotNull()
        })

        validationBuilder.property(InvoiceMessage::amount, {
            it.isNotNull()
            it.isGreaterThan(BigDecimal.ZERO)
        })

        validationBuilder.property(InvoiceMessage::totalOutstanding, {
            it.isNotNull()
            it.isGreaterThanOrEqualTo(BigDecimal.ZERO)
        })

        validationBuilder.property(InvoiceMessage::settlementDate, {
            it.isNotNull()
        })

        validationBuilder.property(InvoiceMessage::invoiceDate, {
            it.isNotNull()
        })

        validationBuilder.property(InvoiceMessage::invoicePayments, {
            it.isNotNull()
        })

        validationBuilder.property(InvoiceMessage::invoiceDilutions, {
            it.isNotNull()
        })

        validationBuilder.property(InvoiceMessage::originationNetwork, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(InvoiceMessage::currency, {
            it.isNotNull()
            it.isValidCurrencyCode()
        })

        validationBuilder.property(InvoiceMessage::siteId, {
            it.isNotNullEmptyOrBlank()
        })
    }
}