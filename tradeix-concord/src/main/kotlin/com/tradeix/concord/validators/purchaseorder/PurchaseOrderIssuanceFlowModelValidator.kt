package com.tradeix.concord.validators.purchaseorder

import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderIssuanceFlowModel
import com.tradeix.concord.validators.Validator
import net.corda.core.crypto.SecureHash
import java.math.BigDecimal

class PurchaseOrderIssuanceFlowModelValidator(message: PurchaseOrderIssuanceFlowModel)
    : Validator<PurchaseOrderIssuanceFlowModel>(message) {

    companion object {
        private val EX_EXTERNAL_ID_REQUIRED = "Field 'externalId' is required."
        private val EX_SUPPLIER_REQUIRED = "Field 'supplier' is required."
        private val EX_REFERENCE_REQUIRED = "Field 'reference' is required."
        private val EX_VALUE_REQUIRED = "Field 'value' is required."
        private val EX_VALUE_NEGATIVE = "Field 'value' cannot be negative."
        private val EX_CURRENCY_REQUIRED = "Field 'currency' is required."
        private val EX_CREATED_REQUIRED = "Field 'created' is required."
        private val EX_EARLIEST_SHIPMENT_REQUIRED = "Field 'earliestShipment' is required."
        private val EX_LATEST_SHIPMENT_REQUIRED = "Field 'latestShipment' is required."
        private val EX_PORT_OF_SHIPMENT_REQUIRED = "Field 'portOfShipment' is required."
        private val EX_DESCRIPTION_OF_GOODS_REQUIRED = "Field 'descriptionOfGoods' is required."
        private val EX_DELIVERY_TERMS_REQUIRED = "Field 'deliveryTerms' is required."
        private val EX_INVALID_ATTACHMENT = "Invalid Secure hash for attachment."
    }

    override fun validate() {
        message.externalId ?: errors.add(EX_EXTERNAL_ID_REQUIRED)
        message.supplier ?: errors.add(EX_SUPPLIER_REQUIRED)
        message.reference ?: errors.add(EX_REFERENCE_REQUIRED)
        message.value ?: errors.add(EX_VALUE_REQUIRED)
        message.currency ?: errors.add(EX_CURRENCY_REQUIRED)
        message.created ?: errors.add(EX_CREATED_REQUIRED)
        message.earliestShipment ?: errors.add(EX_EARLIEST_SHIPMENT_REQUIRED)
        message.latestShipment ?: errors.add(EX_LATEST_SHIPMENT_REQUIRED)
        message.portOfShipment ?: errors.add(EX_PORT_OF_SHIPMENT_REQUIRED)
        message.descriptionOfGoods ?: errors.add(EX_DESCRIPTION_OF_GOODS_REQUIRED)
        message.deliveryTerms ?: errors.add(EX_DELIVERY_TERMS_REQUIRED)

        if (message.value != null && message.value < BigDecimal.ZERO) {
            errors.add(EX_VALUE_NEGATIVE)
        }

        if (message.attachmentId != null) {
            try {
                SecureHash.parse(message.attachmentId)
            } catch (e: IllegalArgumentException) {
                errors.add(EX_INVALID_ATTACHMENT)
            }
        }
    }
}