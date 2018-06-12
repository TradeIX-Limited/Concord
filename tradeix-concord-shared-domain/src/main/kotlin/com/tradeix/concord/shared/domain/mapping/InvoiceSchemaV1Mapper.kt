package com.tradeix.concord.shared.domain.mapping

import com.tradeix.concord.shared.domain.schemas.InvoiceSchemaV1
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.mapper.Mapper

class InvoiceSchemaV1Mapper
    : Mapper<InvoiceState, InvoiceSchemaV1.PersistentInvoiceSchemaV1>() {

    override fun map(source: InvoiceState): InvoiceSchemaV1.PersistentInvoiceSchemaV1 {
        return InvoiceSchemaV1.PersistentInvoiceSchemaV1(
                linearId = source.linearId.id,
                linearExternalId = source.linearId.externalId.toString(),
                owner = source.owner,
                buyer = source.buyer,
                supplier = source.supplier,
                invoiceNumber = source.invoiceNumber,
                reference = source.reference,
                dueDate = source.dueDate,
                currency = source.amount.token.currencyCode,
                amount = source.amount.toDecimal(),
                totalOutstanding = source.totalOutstanding.toDecimal(),
                settlementDate = source.settlementDate,
                invoiceDate = source.invoiceDate,
                invoicePayments = source.invoicePayments.toDecimal(),
                invoiceDilutions = source.invoiceDilutions.toDecimal(),
                originationNetwork = source.originationNetwork,
                siteId = source.siteId
        )
    }
}