package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messages.invoices.InvoiceRequestMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.*
import java.math.BigDecimal

class InvoiceRequestMessageValidator : ObjectValidator<InvoiceRequestMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<InvoiceRequestMessage>) {

        validationBuilder.property(InvoiceRequestMessage::externalId, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(InvoiceRequestMessage::buyer, {
            it.isValidX500Name()
        })

        validationBuilder.property(InvoiceRequestMessage::supplier, {
            it.isValidX500Name()
        })

        validationBuilder.property(InvoiceRequestMessage::invoiceNumber, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(InvoiceRequestMessage::reference, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(InvoiceRequestMessage::dueDate, {
            it.isNotNull()
        })

        validationBuilder.property(InvoiceRequestMessage::amount, {
            it.isNotNull()
            it.isGreaterThan(BigDecimal.ZERO)
        })

        validationBuilder.property(InvoiceRequestMessage::totalOutstanding, {
            it.isNotNull()
            it.isGreaterThanOrEqualTo(BigDecimal.ZERO)
        })

        validationBuilder.property(InvoiceRequestMessage::settlementDate, {
            it.isNotNull()
        })

        validationBuilder.property(InvoiceRequestMessage::invoiceDate, {
            it.isNotNull()
        })

        validationBuilder.property(InvoiceRequestMessage::invoicePayments, {
            it.isNotNull()
        })

        validationBuilder.property(InvoiceRequestMessage::invoiceDilutions, {
            it.isNotNull()
        })

        validationBuilder.property(InvoiceRequestMessage::originationNetwork, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(InvoiceRequestMessage::currency, {
            it.isNotNull()
            it.isValidCurrencyCode()
        })

        validationBuilder.property(InvoiceRequestMessage::siteId, {
            it.isNotNullEmptyOrBlank()
        })
    }
}