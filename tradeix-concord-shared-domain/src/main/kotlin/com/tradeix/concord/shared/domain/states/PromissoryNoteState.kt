package com.tradeix.concord.shared.domain.states

import com.tradeix.concord.shared.domain.models.Address
import com.tradeix.concord.shared.domain.models.VerbalAmount
import net.corda.core.contracts.*
import net.corda.core.identity.AbstractParty
import java.time.LocalDateTime
import java.util.*

data class PromissoryNoteState(
        override val linearId: UniqueIdentifier,
        override val owner: AbstractParty,
        val obligor: AbstractParty,
        val obligee: AbstractParty,
        val guarantor: AbstractParty,
        val amount: Amount<Currency>,
        val placeOfIssue: Address,
        val dateOfIssue: LocalDateTime,
        val dateOfMaturity: LocalDateTime
) : LinearState, OwnableState {

    override val participants: List<AbstractParty> get() = listOf(owner, obligor, obligee, guarantor)

    val verbalAmount: String get() = VerbalAmount(amount.toDecimal()).toString()

    override fun withNewOwner(newOwner: AbstractParty): CommandAndState {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}