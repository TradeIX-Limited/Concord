package com.tradeix.concord.state

import com.tradeix.concord.model.PurchaseOrder
import com.tradeix.concord.schema.PurchaseOrderSchemaV1
import net.corda.core.contracts.LinearState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.AbstractParty
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import net.corda.core.schemas.QueryableState

data class PurchaseOrderState(
        override val linearId: UniqueIdentifier,
        val purchaseOrder: PurchaseOrder,
        val owner: AbstractParty,
        val buyer: AbstractParty,
        val supplier: AbstractParty,
        val conductor: AbstractParty): LinearState, QueryableState {

    override val participants: List<AbstractParty> get() = listOf(owner, buyer, supplier, conductor)

    override fun generateMappedObject(schema: MappedSchema): PersistentState {
        return when (schema) {
            is PurchaseOrderSchemaV1 -> PurchaseOrderSchemaV1.PersistentPurchaseOrder(
                    linearId = linearId.id,
                    value = purchaseOrder.amount.toDecimal(),
                    owner = owner.nameOrNull()!!.toString(),
                    buyer = buyer.nameOrNull()!!.toString(),
                    supplier = supplier.nameOrNull()!!.toString(),
                    conductor = conductor.nameOrNull()!!.toString()
            )
            else -> throw IllegalArgumentException("Unrecognised schema $schema")
        }
    }

    override fun supportedSchemas(): Iterable<MappedSchema> = listOf(PurchaseOrderSchemaV1)
}
