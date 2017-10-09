package com.tradeix.concord.schema

import net.corda.core.contracts.Amount
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import java.math.BigDecimal
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

object PurchaseOrderSchema

object PurchaseOrderSchemaV1 : MappedSchema(
        schemaFamily = PurchaseOrderSchema.javaClass,
        version = 1,
        mappedTypes = listOf(PersistentPurchaseOrder::class.java)) {
    @Entity
    @Table(name = "purchase_order_states")
    class PersistentPurchaseOrder(
            @Column(name = "linear_id")
            var linearId: UUID,

            @Column(name = "value")
            var value: BigDecimal,

            @Column(name = "owner")
            var owner: String,

            @Column(name = "buyer")
            var buyer: String,

            @Column(name = "supplier")
            var supplier: String,

            @Column(name = "conductor")
            var conductor: String
    ) : PersistentState()
}