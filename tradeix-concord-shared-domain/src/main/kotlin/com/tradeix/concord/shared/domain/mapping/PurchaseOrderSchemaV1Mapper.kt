package com.tradeix.concord.shared.domain.mapping

import com.tradeix.concord.shared.domain.schemas.PurchaseOrderSchemaV1
import com.tradeix.concord.shared.domain.states.PurchaseOrderState
import com.tradeix.concord.shared.mapper.Mapper

class PurchaseOrderSchemaV1Mapper
    : Mapper<PurchaseOrderState, PurchaseOrderSchemaV1.PersistentPurchaseOrderSchemaV1>() {

    override fun map(source: PurchaseOrderState): PurchaseOrderSchemaV1.PersistentPurchaseOrderSchemaV1 {
        return PurchaseOrderSchemaV1.PersistentPurchaseOrderSchemaV1(
                linearId = source.linearId.id,
                linearExternalId = source.linearId.externalId.toString(),
                owner = source.owner,
                buyer = source.buyer,
                supplier = source.supplier,
                reference = source.reference,
                currency = source.amount.token.currencyCode,
                amount = source.amount.toDecimal(),
                earliestShipment = source.earliestShipment,
                latestShipment = source.latestShipment,
                portOfShipment = source.portOfShipment,
                descriptionOfGoods = source.descriptionOfGoods,
                deliveryTerms = source.deliveryTerms
        )
    }
}