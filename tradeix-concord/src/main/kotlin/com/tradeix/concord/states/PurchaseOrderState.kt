package com.tradeix.concord.states

import com.tradeix.concord.interfaces.OwnerState
import com.tradeix.concord.messages.rabbit.purchaseorder.PurchaseOrderIssuanceRequestMessage
import com.tradeix.concord.schemas.PurchaseOrderSchemaV1
import net.corda.core.contracts.*
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.CordaX500Name
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import net.corda.core.schemas.QueryableState
import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class PurchaseOrderState(
        override val linearId: UniqueIdentifier,
        override val owner: AbstractParty,
        val buyer: AbstractParty,
        val supplier: AbstractParty,
        val conductor: AbstractParty,
        val reference: String,
        val amount: Amount<Currency>,
        val created: Instant,
        val earliestShipment: Instant,
        val latestShipment: Instant,
        val portOfShipment: String,
        val descriptionOfGoods: String,
        val deliveryTerms: String
) : LinearState, OwnerState, QueryableState {
    override val participants: List<AbstractParty> get() = listOf(owner, buyer, supplier, conductor)

//    override fun withNewOwner(newOwner: AbstractParty): CommandAndState =
//            CommandAndState(PurchaseOrderContract.Commands.ChangeOwner(), this.copy(owner = newOwner))

    override fun generateMappedObject(schema: MappedSchema): PersistentState {
        return when (schema) {
            is PurchaseOrderSchemaV1 -> PurchaseOrderSchemaV1.PersistentPurchaseOrderSchemaV1(
                    linearId = linearId.id,
                    externalId = linearId.externalId.toString(),
                    owner = owner,
                    buyer = buyer,
                    supplier = supplier,
                    conductor = conductor,
                    reference = reference,
                    value = amount.toDecimal(),
                    currency = amount.token.currencyCode,
                    created = created,
                    earliestShipment = earliestShipment,
                    latestShipment = latestShipment,
                    portOfShipment = portOfShipment,
                    descriptionOfGoods = descriptionOfGoods,
                    deliveryTerms = deliveryTerms
            )
            else -> throw IllegalArgumentException("Unrecognised schemas $schema")
        }
    }

    override fun supportedSchemas(): Iterable<MappedSchema> = listOf(PurchaseOrderSchemaV1)

    fun changeOwner(newOwner: AbstractParty): PurchaseOrderState = copy(owner = newOwner)

    fun toMessage() : PurchaseOrderIssuanceRequestMessage =
            PurchaseOrderIssuanceRequestMessage(
                    correlationId = "",
                    tryCount = 0,
                    externalId= linearId.externalId.toString(),
                    attachmentId = "",
                    buyer = buyer.nameOrNull(),
                    supplier = supplier.nameOrNull(),
                    conductor = conductor.nameOrNull(),
                    owner = owner.nameOrNull(),
                    reference= reference,
                    value = amount.toDecimal(),
                    currency = amount.token.currencyCode,
                    created = created,
                    earliestShipment = earliestShipment,
                    latestShipment = latestShipment,
                    portOfShipment = portOfShipment,
                    descriptionOfGoods = descriptionOfGoods,
                    deliveryTerms = deliveryTerms
            )
}