package com.tradeix.concord.shared.domain.states

import com.tradeix.concord.shared.domain.contracts.InvoiceContract
import net.corda.core.contracts.*
import net.corda.core.identity.AbstractParty
import java.time.LocalDateTime
import java.util.*

data class InvoiceState(
        override val linearId: UniqueIdentifier,
        override val owner: AbstractParty,
        val buyer: AbstractParty?,
        val supplier: AbstractParty,
        val invoiceNumber: String,
        val reference: String,
        val dueDate: LocalDateTime,
        val amount: Amount<Currency>,
        val totalOutstanding: Amount<Currency>,
        val settlementDate: LocalDateTime,
        val invoiceDate: LocalDateTime,
        val invoicePayments: Amount<Currency>,
        val invoiceDilutions: Amount<Currency>,
        val originationNetwork: String,
        val siteId: String
) : LinearState, OwnableState {

    override val participants: List<AbstractParty> get() = listOfNotNull(owner, buyer, supplier)

    override fun withNewOwner(newOwner: AbstractParty): CommandAndState {
        return CommandAndState(InvoiceContract.ChangeOwner(), this.copy(owner = newOwner))
    }
}