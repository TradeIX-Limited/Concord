package com.tradeix.concord.shared.domain.states

import com.tradeix.concord.shared.domain.contracts.PurchaseOrderContract
import net.corda.core.contracts.*
import net.corda.core.identity.AbstractParty
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
) : LinearState, OwnableState {

    override val participants: List<AbstractParty> get() = listOf(owner, buyer, supplier, conductor)

    override fun withNewOwner(newOwner: AbstractParty): CommandAndState {
        return CommandAndState(PurchaseOrderContract.Commands.ChangeOwner(), this.copy(owner = newOwner))
    }
}