package com.tradeix.concord.shared.domain.states

import net.corda.core.contracts.*
import net.corda.core.flows.FlowLogicRefFactory
import net.corda.core.identity.AbstractParty
import java.time.LocalDateTime
import java.util.*

data class FundingRequestState(
        override val linearId: UniqueIdentifier,
        val invoiceIds: Iterable<UniqueIdentifier>,
        val supplier: AbstractParty,
        val funders: Iterable<AbstractParty>,
        val created: LocalDateTime,
        val updated: LocalDateTime,
        val expires: LocalDateTime,
        val totalInvoiceValue: Amount<Currency>
) : LinearState {
    override val participants: List<AbstractParty> get() = funders + supplier
}