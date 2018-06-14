package com.tradeix.concord.shared.domain.schemas

import net.corda.core.identity.AbstractParty
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import java.math.BigDecimal
import java.time.LocalDateTime
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
    @Table(name = "purchase_order_states")
    class PersistentPurchaseOrderSchemaV1(
            @Column(name = "linear_id")
            val linearId: UUID,

            @Column(name = "linear_external_id")
            val linearExternalId: String,

            @Column(name = "owner")
            val owner: AbstractParty,

            @Column(name = "buyer")
            val buyer: AbstractParty,

            @Column(name = "supplier")
            val supplier: AbstractParty,

            @Column(name = "reference")
            val reference: String,

            @Column(name = "currency")
            val currency: String,

            @Column(name = "amount")
            val amount: BigDecimal,

            @Column(name = "earliest_shipment")
            val earliestShipment: LocalDateTime,

            @Column(name = "latest_shipment")
            val latestShipment: LocalDateTime,

            @Column(name = "port_of_shipment")
            val portOfShipment: String,

            @Column(name = "description_of_goods")
            val descriptionOfGoods: String,

            @Column(name = "delivery_terms")
            val deliveryTerms: String
    ) : PersistentState()
}