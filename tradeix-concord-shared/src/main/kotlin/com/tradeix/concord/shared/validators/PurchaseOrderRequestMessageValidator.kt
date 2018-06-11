package com.tradeix.concord.shared.validators

import com.tradeix.concord.shared.messages.purchaseorders.PurchaseOrderMessage
import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.ValidationBuilder
import com.tradeix.concord.shared.validation.extensions.*
import java.math.BigDecimal

class PurchaseOrderRequestMessageValidator : ObjectValidator<PurchaseOrderMessage>() {

    override fun validate(validationBuilder: ValidationBuilder<PurchaseOrderMessage>) {

        validationBuilder.property(PurchaseOrderMessage::externalId, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(PurchaseOrderMessage::buyer, {
            it.isValidX500Name()
        })

        validationBuilder.property(PurchaseOrderMessage::supplier, {
            it.isNotNullEmptyOrBlank()
            it.isValidX500Name()
        })

        validationBuilder.property(PurchaseOrderMessage::reference, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(PurchaseOrderMessage::value, {
            it.isNotNull()
            it.isGreaterThan(BigDecimal.ZERO)
        })

        validationBuilder.property(PurchaseOrderMessage::currency, {
            it.isNotNullEmptyOrBlank()
            it.isValidCurrencyCode()
        })

        validationBuilder.property(PurchaseOrderMessage::created, {
            it.isNotNull()
        })

        validationBuilder.property(PurchaseOrderMessage::earliestShipment, {
            it.isNotNull()
        })

        validationBuilder.property(PurchaseOrderMessage::latestShipment, {
            it.isNotNull()
        })

        validationBuilder.property(PurchaseOrderMessage::portOfShipment, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(PurchaseOrderMessage::descriptionOfGoods, {
            it.isNotNullEmptyOrBlank()
        })

        validationBuilder.property(PurchaseOrderMessage::deliveryTerms, {
            it.isNotNullEmptyOrBlank()
        })
    }
}