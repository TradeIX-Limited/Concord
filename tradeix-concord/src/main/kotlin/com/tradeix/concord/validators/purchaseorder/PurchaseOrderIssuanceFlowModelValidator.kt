package com.tradeix.concord.validators.purchaseorder

import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderIssuanceFlowModel
import com.tradeix.concord.validators.Validator
import net.corda.core.crypto.SecureHash
import java.math.BigDecimal
import com.tradeix.concord.extensions.ArrayListExtensions.addWhen
import com.tradeix.concord.extensions.SecureHashExtensions.isValid
import java.time.Instant

class PurchaseOrderIssuanceFlowModelValidator(message: PurchaseOrderIssuanceFlowModel)
    : Validator<PurchaseOrderIssuanceFlowModel>(message) {

    companion object {
        private val EX_EXTERNAL_ID_REQUIRED = "Field 'externalId' is required."
        private val EX_SUPPLIER_REQUIRED = "Field 'supplier' is required."
        private val EX_REFERENCE_REQUIRED = "Field 'reference' is required."
        private val EX_VALUE_REQUIRED = "Field 'value' is required."
        private val EX_VALUE_NEGATIVE = "Field 'value' cannot be zero or negative."
        private val EX_CURRENCY_REQUIRED = "Field 'currency' is required."
        private val EX_CREATED_REQUIRED = "Field 'created' is required."
        private val EX_CREATED_FUTURE = "Field 'created' cannot be a future date."
        private val EX_EARLIEST_SHIPMENT_REQUIRED = "Field 'earliestShipment' is required."
        private val EX_LATEST_SHIPMENT_REQUIRED = "Field 'latestShipment' is required."
        private val EX_EARLIEST_LATEST_SHIPMENT = "Field 'earliestShipment' cannot be later than field 'latestShipment'."
        private val EX_PORT_OF_SHIPMENT_REQUIRED = "Field 'portOfShipment' is required."
        private val EX_DESCRIPTION_OF_GOODS_REQUIRED = "Field 'descriptionOfGoods' is required."
        private val EX_DELIVERY_TERMS_REQUIRED = "Field 'deliveryTerms' is required."
        private val EX_INVALID_ATTACHMENT = "Invalid Secure hash for attachment."
    }

    override fun validate() {
        errors.addWhen(message.externalId.isNullOrBlank(), EX_EXTERNAL_ID_REQUIRED)
        errors.addWhen(message.supplier == null, EX_SUPPLIER_REQUIRED)
        errors.addWhen(message.reference.isNullOrBlank(), EX_REFERENCE_REQUIRED)
        errors.addWhen(message.value == null, EX_VALUE_REQUIRED)
        errors.addWhen(message.value != null && message.value <= BigDecimal.ZERO, EX_VALUE_NEGATIVE)
        errors.addWhen(message.currency.isNullOrBlank(), EX_CURRENCY_REQUIRED)
        errors.addWhen(message.created == null, EX_CREATED_REQUIRED)
        errors.addWhen(message.created != null && message.created > Instant.now(), EX_CREATED_FUTURE)
        errors.addWhen(message.earliestShipment == null, EX_EARLIEST_SHIPMENT_REQUIRED)
        errors.addWhen(message.latestShipment == null, EX_LATEST_SHIPMENT_REQUIRED)

        errors.addWhen(
                message.earliestShipment != null &&
                message.latestShipment != null &&
                message.earliestShipment > message.latestShipment, EX_EARLIEST_LATEST_SHIPMENT)

        errors.addWhen(message.portOfShipment.isNullOrBlank(), EX_PORT_OF_SHIPMENT_REQUIRED)
        errors.addWhen(message.descriptionOfGoods.isNullOrBlank(), EX_DESCRIPTION_OF_GOODS_REQUIRED)
        errors.addWhen(message.deliveryTerms.isNullOrBlank(), EX_DELIVERY_TERMS_REQUIRED)
        errors.addWhen(message.attachmentId != null && !SecureHash.isValid(message.attachmentId), EX_INVALID_ATTACHMENT)
    }
}