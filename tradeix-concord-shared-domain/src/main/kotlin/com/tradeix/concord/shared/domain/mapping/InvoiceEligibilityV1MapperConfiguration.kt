package com.tradeix.concord.shared.domain.mapping

import com.tradeix.concord.shared.domain.schemas.InvoiceEligibilitySchemaV1
import com.tradeix.concord.shared.domain.states.InvoiceEligibilityState
import com.tradeix.concord.shared.mapper.MapperConfiguration

class InvoiceEligibilityV1MapperConfiguration
    : MapperConfiguration<InvoiceEligibilityState, InvoiceEligibilitySchemaV1.PersistentInvoiceEligibilitySchemaV1>() {

    override fun map(source: InvoiceEligibilityState): InvoiceEligibilitySchemaV1.PersistentInvoiceEligibilitySchemaV1 {
        return InvoiceEligibilitySchemaV1.PersistentInvoiceEligibilitySchemaV1(
                linearId = source.linearId.id,
                linearExternalId = source.linearId.externalId.toString(),
                invoiceId = source.invoiceId.id,
                invoiceExternalId = source.invoiceId.externalId.toString(),
                supplier = source.supplier,
                funder = source.supplier,
                eligible = source.eligible
        )
    }
}