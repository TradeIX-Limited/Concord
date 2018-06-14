package com.tradeix.concord.shared.domain.mapping

import com.tradeix.concord.shared.domain.schemas.InvoiceEligibilitySchemaV1.PersistentInvoiceEligibilitySchemaV1
import com.tradeix.concord.shared.domain.states.InvoiceEligibilityState
import com.tradeix.concord.shared.mapper.Mapper

class InvoiceEligibilitySchemaV1Mapper
    : Mapper<InvoiceEligibilityState, PersistentInvoiceEligibilitySchemaV1>() {

    override fun map(source: InvoiceEligibilityState): PersistentInvoiceEligibilitySchemaV1 {
        return PersistentInvoiceEligibilitySchemaV1(
                linearId = source.linearId.id,
                linearExternalId = source.linearId.externalId.toString(),
                invoiceExternalId = source.invoiceExternalId,
                supplier = source.supplier,
                funder = source.supplier,
                eligible = source.eligible
        )
    }
}