package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messages.purchaseorders.PurchaseOrderRequestMessage
import com.tradeix.concord.shared.validation.*
import java.math.BigDecimal

class PurchaseOrderRequestMessageValidator : ObjectModelValidator<PurchaseOrderRequestMessage>() {

    override fun onValidationBuilding(validationBuilder: ValidationBuilder<PurchaseOrderRequestMessage>) {
        validationBuilder
                .property(PurchaseOrderRequestMessage::externalId)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(PurchaseOrderRequestMessage::attachmentId)
                .isValidSecureHash()

        validationBuilder
                .property(PurchaseOrderRequestMessage::buyer)
                .isValidCordaX500Name()

        validationBuilder
                .property(PurchaseOrderRequestMessage::supplier)
                .isNotNullEmptyOrBlank()
                .isValidCordaX500Name()

        validationBuilder
                .property(PurchaseOrderRequestMessage::reference)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(PurchaseOrderRequestMessage::value)
                .isNotNull()
                .isGreaterThan(BigDecimal.ZERO)

        validationBuilder
                .property(PurchaseOrderRequestMessage::currency)
                .isNotNullEmptyOrBlank()
                .isValidCurrencyCode()

        validationBuilder
                .property(PurchaseOrderRequestMessage::created)
                .isNotNull()

        validationBuilder
                .property(PurchaseOrderRequestMessage::earliestShipment)
                .isNotNull()

        validationBuilder
                .property(PurchaseOrderRequestMessage::latestShipment)
                .isNotNull()

        validationBuilder
                .property(PurchaseOrderRequestMessage::portOfShipment)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(PurchaseOrderRequestMessage::descriptionOfGoods)
                .isNotNullEmptyOrBlank()

        validationBuilder
                .property(PurchaseOrderRequestMessage::deliveryTerms)
                .isNotNullEmptyOrBlank()
    }
}