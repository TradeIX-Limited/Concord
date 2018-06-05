package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messages.invoices.InvoiceRequestMessage
import com.tradeix.concord.shared.validation.*
import java.math.BigDecimal

class InvoiceRequestMessageValidator : ObjectModelValidator<InvoiceRequestMessage>() {

    override fun onValidationBuilding(validationBuilder: ValidationBuilder<InvoiceRequestMessage>) {
        validationBuilder
                .property(InvoiceRequestMessage::externalId)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(InvoiceRequestMessage::buyer)
                .isValidCordaX500Name()

        validationBuilder
                .property(InvoiceRequestMessage::supplier)
                .isValidCordaX500Name()

        validationBuilder
                .property(InvoiceRequestMessage::invoiceNumber)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(InvoiceRequestMessage::reference)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(InvoiceRequestMessage::dueDate)
                .isNotNull()

        validationBuilder
                .property(InvoiceRequestMessage::amount)
                .isNotNull()
                .isGreaterThan(BigDecimal.ZERO)

        validationBuilder
                .property(InvoiceRequestMessage::totalOutstanding)
                .isNotNull()
                .isGreaterThanOrEqualTo(BigDecimal.ZERO)

        validationBuilder
                .property(InvoiceRequestMessage::settlementDate)
                .isNotNull()

        validationBuilder
                .property(InvoiceRequestMessage::invoiceDate)
                .isNotNull()

        validationBuilder
                .property(InvoiceRequestMessage::invoicePayments)
                .isNotNull()

        validationBuilder
                .property(InvoiceRequestMessage::invoiceDilutions)
                .isNotNull()

        validationBuilder
                .property(InvoiceRequestMessage::originationNetwork)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(InvoiceRequestMessage::currency)
                .isNotNull()
                .isValidCurrencyCode()

        validationBuilder
                .property(InvoiceRequestMessage::siteId)
                .isNotNullEmptyOrBlank()
    }
}