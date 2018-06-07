package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messages.purchaseorders.PurchaseOrderRequestMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.*
import java.math.BigDecimal

class PurchaseOrderRequestMessageValidator : ObjectValidator<PurchaseOrderRequestMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<PurchaseOrderRequestMessage>) {

        validationBuilder.property(PurchaseOrderRequestMessage::externalId, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(PurchaseOrderRequestMessage::buyer, {
            it.isValidX500Name()
        })

        validationBuilder.property(PurchaseOrderRequestMessage::supplier, {
            it.isNotNullEmptyOrBlank()
            it.isValidX500Name()
        })

        validationBuilder.property(PurchaseOrderRequestMessage::reference, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(PurchaseOrderRequestMessage::value, {
            it.isNotNull()
            it.isGreaterThan(BigDecimal.ZERO)
        })

        validationBuilder.property(PurchaseOrderRequestMessage::currency, {
            it.isNotNullEmptyOrBlank()
            it.isValidCurrencyCode()
        })

        validationBuilder.property(PurchaseOrderRequestMessage::created, {
            it.isNotNull()
        })

        validationBuilder.property(PurchaseOrderRequestMessage::earliestShipment, {
            it.isNotNull()
        })

        validationBuilder.property(PurchaseOrderRequestMessage::latestShipment, {
            it.isNotNull()
        })

        validationBuilder.property(PurchaseOrderRequestMessage::portOfShipment, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(PurchaseOrderRequestMessage::descriptionOfGoods, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(PurchaseOrderRequestMessage::deliveryTerms, {
            it.isNotNullEmptyOrBlank()
        })
    }
}