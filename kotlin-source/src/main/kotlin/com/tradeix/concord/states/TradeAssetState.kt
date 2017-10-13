package com.tradeix.concord.states

import com.tradeix.concord.interfaces.OwnerState
import com.tradeix.concord.models.TradeAsset
import com.tradeix.concord.schemas.TradeAssetSchemaV1
import net.corda.core.contracts.LinearState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.Party
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import net.corda.core.schemas.QueryableState

data class TradeAssetState(
        override val linearId: UniqueIdentifier,
        val tradeAsset: TradeAsset,
        override val owner: Party,
        val buyer: Party,
        val supplier: Party,
        val conductor: Party) : OwnerState, LinearState, QueryableState {

    override val participants: List<AbstractParty> get() = listOf(owner, buyer, supplier, conductor)

    override fun generateMappedObject(schema: MappedSchema): PersistentState {
        return when (schema) {
            is TradeAssetSchemaV1 -> TradeAssetSchemaV1.PersistentTradeAssetSchemaV1(
                    linearId = linearId.id,
                    assetId = tradeAsset.assetId,
                    status = tradeAsset.status,
                    value = tradeAsset.amount.toDecimal(),
                    owner = owner.name.toString(),
                    buyer = buyer.name.toString(),
                    supplier = supplier.name.toString(),
                    conductor = conductor.name.toString()
            )
            else -> throw IllegalArgumentException("Unrecognised schemas $schema")
        }
    }

    override fun supportedSchemas(): Iterable<MappedSchema> = listOf(TradeAssetSchemaV1)
}
