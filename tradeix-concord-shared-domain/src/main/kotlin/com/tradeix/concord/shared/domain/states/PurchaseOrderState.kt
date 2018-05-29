package com.tradeix.concord.shared.domain.states

import com.tradeix.concord.shared.domain.contracts.PurchaseOrderContract
import net.corda.core.contracts.*
import net.corda.core.identity.AbstractParty
import java.time.LocalDateTime
import java.util.*

data class PurchaseOrderState(
        override val linearId: UniqueIdentifier,
        override val owner: AbstractParty,
        val buyer: AbstractParty,
        val supplier: AbstractParty,
        val reference: String,
        val amount: Amount<Currency>,
        val created: LocalDateTime,
        val earliestShipment: LocalDateTime,
        val latestShipment: LocalDateTime,
        val portOfShipment: String,
        val descriptionOfGoods: String,
        val deliveryTerms: String
) : LinearState, OwnableState {

    override val participants: List<AbstractParty> get() = listOf(owner, buyer, supplier)

    override fun withNewOwner(newOwner: AbstractParty): CommandAndState {
        return CommandAndState(PurchaseOrderContract.Commands.ChangeOwner(), this.copy(owner = newOwner))
    }
}