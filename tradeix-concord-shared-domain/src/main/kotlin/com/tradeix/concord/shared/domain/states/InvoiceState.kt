package com.tradeix.concord.shared.domain.states

import com.tradeix.concord.shared.domain.contracts.InvoiceContract
import com.tradeix.concord.shared.domain.mapping.InvoiceSchemaV1Mapper
import com.tradeix.concord.shared.domain.schemas.InvoiceSchemaV1
import com.tradeix.concord.shared.models.Participant
import net.corda.core.contracts.*
import net.corda.core.identity.AbstractParty
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import net.corda.core.schemas.QueryableState
import java.time.LocalDateTime
import java.util.*

data class InvoiceState(
        override val linearId: UniqueIdentifier,
        override val owner: AbstractParty,
        val buyer: Participant,
        val supplier: Participant,
        val invoiceNumber: String,
        val invoiceVersion: String,
        val submitted: LocalDateTime,
        val reference: String,
        val dueDate: LocalDateTime,
        val amount: Amount<Currency>,
        val totalOutstanding: Amount<Currency>,
        val settlementDate: LocalDateTime,
        val invoiceDate: LocalDateTime,
        val invoicePayments: Amount<Currency>,
        val invoiceDilutions: Amount<Currency>,
        val originationNetwork: String,
        val siteId: String,
        val tradeDate: LocalDateTime?,              // TODO : Should this be optional?
        val tradePaymentDate: LocalDateTime?        // TODO : Should this be optional?
) : LinearState, OwnableState, QueryableState {

    override val participants: List<AbstractParty> get() = listOfNotNull(owner, buyer.party, supplier.party)

    override fun withNewOwner(newOwner: AbstractParty): CommandAndState {
        return CommandAndState(InvoiceContract.ChangeOwner(), this.copy(owner = newOwner))
    }

    override fun generateMappedObject(schema: MappedSchema): PersistentState {
        return when (schema) {
            is InvoiceSchemaV1 -> InvoiceSchemaV1Mapper().map(this)
            else -> throw IllegalArgumentException("Unrecognised schemas $schema")
        }
    }

    override fun supportedSchemas(): Iterable<MappedSchema> {
        return listOf(InvoiceSchemaV1)
    }
}