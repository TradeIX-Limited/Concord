package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messagecontracts.InvoiceMessage
import com.tradeix.concord.shared.messages.invoices.InvoiceIssuanceRequestMessage
import com.tradeix.concord.shared.validation.*
import java.math.BigDecimal

class InvoiceMessageValidator : ObjectModelValidator<InvoiceMessage>() {

    override fun onValidationBuilding(validationBuilder: ValidationBuilder<InvoiceMessage>) {
        validationBuilder
                .property(InvoiceMessage::externalId)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(InvoiceMessage::attachmentId)
                .isValidSecureHash()

        validationBuilder
                .property(InvoiceMessage::buyer)
                .isValidCordaX500Name()

        validationBuilder
                .property(InvoiceMessage::supplier)
                .isValidCordaX500Name()

        validationBuilder
                .property(InvoiceMessage::conductor)
                .isValidCordaX500Name()

        validationBuilder
                .property(InvoiceMessage::invoiceVersion)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(InvoiceMessage::invoiceVersionDate)
                .isNotNull()

        validationBuilder
                .property(InvoiceMessage::tixInvoiceVersion)
                .isNotNull()

        validationBuilder
                .property(InvoiceMessage::invoiceNumber)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(InvoiceMessage::invoiceType)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(InvoiceMessage::reference)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(InvoiceMessage::dueDate)
                .isNotNull()

        validationBuilder
                .property(InvoiceMessage::amount)
                .isNotNull()
                .isGreaterThan(BigDecimal.ZERO)

        validationBuilder
                .property(InvoiceMessage::totalOutstanding)
                .isNotNull()
                .isGreaterThanOrEqualTo(BigDecimal.ZERO)

        validationBuilder
                .property(InvoiceMessage::created)
                .isNotNull()

        validationBuilder
                .property(InvoiceMessage::updated)
                .isNotNull()

        validationBuilder
                .property(InvoiceMessage::expectedSettlementDate)
                .isNotNull()

        validationBuilder
                .property(InvoiceMessage::invoiceDate)
                .isNotNull()

        validationBuilder
                .property(InvoiceMessage::status)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(InvoiceMessage::rejectionReason)
                .isNotEmpty()
                .isNotBlank()

        validationBuilder
                .property(InvoiceMessage::eligibleValue)
                .isNotNull()

        validationBuilder
                .property(InvoiceMessage::invoicePurchaseValue)
                .isNotNull()

        validationBuilder
                .property(InvoiceMessage::invoicePayments)
                .isNotNull()

        validationBuilder
                .property(InvoiceMessage::invoiceDilutions)
                .isNotNull()

        validationBuilder
                .property(InvoiceMessage::cancelled)
                .isNotNull()

        validationBuilder
                .property(InvoiceMessage::originationNetwork)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(InvoiceMessage::currency)
                .isNotNull()
                .isValidCurrencyCode()

        validationBuilder
                .property(InvoiceMessage::siteId)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(InvoiceMessage::purchaseOrderNumber)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(InvoiceMessage::purchaseOrderId)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(InvoiceMessage::composerProgramId)
                .isNotNull()
    }
}