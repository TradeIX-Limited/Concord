package com.tradeix.concord.shared.domain.schemas

import net.corda.core.identity.AbstractParty
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

object InvoiceEligibilitySchema

object InvoiceEligibilitySchemaV1 : MappedSchema(
        schemaFamily = InvoiceEligibilitySchema.javaClass,
        version = 1,
        mappedTypes = listOf(InvoiceEligibilitySchema::class.java)) {

    @Entity
    @Table(name = "invoice_eligibility_states")
    class PersistentInvoiceEligibilitySchemaV1(
            @Column(name = "linear_id")
            val linearId: UUID,

            @Column(name = "linear_external_id")
            val linearExternalId: String,

            @Column(name = "invoice_id")
            val invoiceId: UUID,

            @Column(name = "invoice_external_id")
            val invoiceExternalId: String,

            @Column(name = "supplier")
            val supplier: AbstractParty,

            @Column(name = "funder")
            val funder: AbstractParty,

            @Column(name = "eligible")
            val eligible: Boolean
    ) : PersistentState()
}