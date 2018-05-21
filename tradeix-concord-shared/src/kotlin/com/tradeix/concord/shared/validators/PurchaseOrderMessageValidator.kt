package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messagecontracts.PurchaseOrderMessage
import com.tradeix.concord.shared.validation.*
import java.math.BigDecimal

class PurchaseOrderMessageValidator : ObjectModelValidator<PurchaseOrderMessage>() {

    override fun onValidationBuilding(validationBuilder: ValidationBuilder<PurchaseOrderMessage>) {
        validationBuilder
                .property(PurchaseOrderMessage::externalId)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(PurchaseOrderMessage::attachmentId)
                .isValidSecureHash()

        validationBuilder
                .property(PurchaseOrderMessage::buyer)
                .isValidCordaX500Name()

        validationBuilder
                .property(PurchaseOrderMessage::supplier)
                .isNotNullEmptyOrBlank()
                .isValidCordaX500Name()

        validationBuilder
                .property(PurchaseOrderMessage::conductor)
                .isValidCordaX500Name()

        validationBuilder
                .property(PurchaseOrderMessage::reference)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(PurchaseOrderMessage::value)
                .isNotNull()
                .isGreaterThan(BigDecimal.ZERO)

        validationBuilder
                .property(PurchaseOrderMessage::currency)
                .isNotNullEmptyOrBlank()
                .isValidCurrencyCode()

        validationBuilder
                .property(PurchaseOrderMessage::created)
                .isNotNull()

        validationBuilder
                .property(PurchaseOrderMessage::earliestShipment)
                .isNotNull()

        validationBuilder
                .property(PurchaseOrderMessage::latestShipment)
                .isNotNull()

        validationBuilder
                .property(PurchaseOrderMessage::portOfShipment)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(PurchaseOrderMessage::descriptionOfGoods)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(PurchaseOrderMessage::deliveryTerms)
                .isNotNullEmptyOrBlank()
    }
}