package com.tradeix.concord.shared.domain.states

import com.tradeix.concord.shared.domain.contracts.PurchaseOrderContract
import com.tradeix.concord.shared.domain.mapping.PurchaseOrderSchemaV1Mapper
import com.tradeix.concord.shared.domain.schemas.PurchaseOrderSchemaV1
import net.corda.core.contracts.*
import net.corda.core.identity.AbstractParty
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import net.corda.core.schemas.QueryableState
import java.time.LocalDateTime
import java.util.*

data class PurchaseOrderState(
        override val linearId: UniqueIdentifier,
        override val owner: AbstractParty,
        val buyer: AbstractParty,
        val supplier: AbstractParty,
        val reference: String,
        val amount: Amount<Currency>,
        val earliestShipment: LocalDateTime,
        val latestShipment: LocalDateTime,
        val portOfShipment: String,
        val descriptionOfGoods: String,
        val deliveryTerms: String
) : LinearState, OwnableState, QueryableState {

    override val participants: List<AbstractParty> get() = listOf(owner, buyer, supplier)

    override fun withNewOwner(newOwner: AbstractParty): CommandAndState {
        return CommandAndState(PurchaseOrderContract.ChangeOwner(), this.copy(owner = newOwner))
    }

    override fun generateMappedObject(schema: MappedSchema): PersistentState {
        return when (schema) {
            is PurchaseOrderSchemaV1 -> PurchaseOrderSchemaV1Mapper().map(this)
            else -> throw IllegalArgumentException("Unrecognised schemas $schema")
        }
    }

    override fun supportedSchemas(): Iterable<MappedSchema> {
        return listOf(PurchaseOrderSchemaV1)
    }
}