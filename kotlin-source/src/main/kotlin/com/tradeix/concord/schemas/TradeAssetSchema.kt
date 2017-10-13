package com.tradeix.concord.schemas

import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import java.math.BigDecimal
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

object TradeAssetSchema

object TradeAssetSchemaV1 : MappedSchema(
        schemaFamily = TradeAssetSchema.javaClass,
        version = 1,
        mappedTypes = listOf(PersistentTradeAssetSchemaV1::class.java)) {
    @Entity
    @Table(name = "trade_asset_states")
    class PersistentTradeAssetSchemaV1(
            @Column(name = "linear_id")
            var linearId: UUID,

            @Column(name = "value")
            var value: BigDecimal,

            @Column(name = "asset_id")
            var assetId: String,

            @Column(name = "status")
            var status: String,

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