package com.tradeix.concord.schemas

import net.corda.core.identity.AbstractParty
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import java.math.BigDecimal
import java.time.Instant
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

object PurchaseOrderSchema

object PurchaseOrderSchemaV1 : MappedSchema(
        schemaFamily = PurchaseOrderSchema.javaClass,
        version = 1,
        mappedTypes = listOf(PersistentPurchaseOrderSchemaV1::class.java)) {
    @Entity
    @Table(name = "purchase_orders")
    class PersistentPurchaseOrderSchemaV1(
            @Column(name = "linear_id")
            var linearId: UUID,

            @Column(name = "external_id")
            var externalId: String,

            @Column(name = "owner")
            var owner: AbstractParty,

            @Column(name = "buyer")
            var buyer: AbstractParty,

            @Column(name = "supplier")
            var supplier: AbstractParty,

            @Column(name = "conductor")
            val conductor: AbstractParty,

            @Column(name = "reference")
            var reference: String,

            @Column(name = "value")
            var value: BigDecimal,

            @Column(name = "currency")
            var currency: String,

            @Column(name = "created")
            var created: Instant,

            @Column(name = "earliest_shipment")
            var earliestShipment: Instant,

            @Column(name = "latest_shipment")
            var latestShipment: Instant,

            @Column(name = "port_of_shipment")
            var portOfShipment: String,

            @Column(name = "description_of_goods")
            var descriptionOfGoods: String,

            @Column(name = "delivery_terms")
            var deliveryTerms: String
    ) : PersistentState()
}