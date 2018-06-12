package com.tradeix.concord.shared.domain.states

import com.tradeix.concord.shared.domain.mapping.InvoiceEligibilitySchemaV1Mapper
import com.tradeix.concord.shared.domain.schemas.InvoiceEligibilitySchemaV1
import net.corda.core.contracts.LinearState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.AbstractParty
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import net.corda.core.schemas.QueryableState

data class InvoiceEligibilityState(
        override val linearId: UniqueIdentifier,
        val invoiceExternalId: String,
        val supplier: AbstractParty,
        val funder: AbstractParty,
        val eligible: Boolean
) : LinearState, QueryableState {

    override val participants: List<AbstractParty> get() = listOf(supplier, funder)

    override fun generateMappedObject(schema: MappedSchema): PersistentState {
        return when (schema) {
            is InvoiceEligibilitySchemaV1 -> InvoiceEligibilitySchemaV1Mapper().map(this)
            else -> throw IllegalArgumentException("Unrecognised schemas $schema")
        }
    }

    override fun supportedSchemas(): Iterable<MappedSchema> {
        return listOf(InvoiceEligibilitySchemaV1)
    }
}