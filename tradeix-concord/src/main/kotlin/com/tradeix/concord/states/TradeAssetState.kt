package com.tradeix.concord.states

import com.tradeix.concord.interfaces.OwnerState
import com.tradeix.concord.messages.rabbit.tradeasset.TradeAssetIssuanceRequestMessage
import com.tradeix.concord.schemas.TradeAssetSchemaV1
import net.corda.core.contracts.Amount
import net.corda.core.contracts.LinearState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.AbstractParty
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import net.corda.core.schemas.QueryableState
import net.corda.core.serialization.CordaSerializable
import java.util.*

data class TradeAssetState(
        override val linearId: UniqueIdentifier,
        override val owner: AbstractParty,
        val buyer: AbstractParty,
        val supplier: AbstractParty,
        val conductor: AbstractParty,
        val status: TradeAssetStatus,
        val amount: Amount<Currency>
) : OwnerState, LinearState, QueryableState {

    @CordaSerializable
    enum class TradeAssetStatus(val description: String) {
        INVOICE("Invoice"),
        PURCHASE_ORDER("Purchase Order");

        override fun toString() = description
    }

    override val participants: List<AbstractParty> get() = listOf(owner, buyer, supplier, conductor)

    override fun generateMappedObject(schema: MappedSchema): PersistentState {
        return when (schema) {
            is TradeAssetSchemaV1 -> TradeAssetSchemaV1.PersistentTradeAssetSchemaV1(
                    externalId = linearId.externalId.toString(),
                    linearId = linearId.id,
                    status = status.description,
                    value = amount.toDecimal(),
                    owner = owner,
                    buyer = buyer,
                    supplier = supplier,
                    conductor = conductor
            )
            else -> throw IllegalArgumentException("Unrecognised schemas $schema")
        }
    }

    override fun supportedSchemas(): Iterable<MappedSchema> = listOf(TradeAssetSchemaV1)

    fun toMessage() : TradeAssetIssuanceRequestMessage =
            TradeAssetIssuanceRequestMessage(
                    correlationId = "",
                    tryCount = 0,
                    externalId = linearId.externalId,
                    buyer = buyer.nameOrNull(),
                    supplier = supplier.nameOrNull(),
                    conductor = conductor.nameOrNull()!!,
                    owner = owner.nameOrNull()!!,
                    status = status.description,
                    value = amount.toDecimal(),
                    currency = amount.token.currencyCode,
                    attachmentId = "")
}
