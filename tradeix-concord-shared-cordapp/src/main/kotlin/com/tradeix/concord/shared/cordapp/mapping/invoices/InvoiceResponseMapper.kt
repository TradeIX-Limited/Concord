package com.tradeix.concord.shared.cordapp.mapping.invoices

import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.mapper.Mapper
import com.tradeix.concord.shared.messages.invoices.InvoiceResponseMessage

class InvoiceResponseMapper : Mapper<InvoiceState, InvoiceResponseMessage>() {

    override fun map(source: InvoiceState): InvoiceResponseMessage {
        return InvoiceResponseMessage(
                externalId = source.linearId.externalId.toString(),
                buyer = source.buyer?.nameOrNull().toString(),
                supplier = source.supplier.nameOrNull().toString(),
                invoiceNumber = source.invoiceNumber,
                reference = source.reference,
                dueDate = source.dueDate,
                amount = source.amount.toDecimal(),
                totalOutstanding = source.totalOutstanding.toDecimal(),
                settlementDate = source.settlementDate,
                invoiceDate = source.invoiceDate,
                invoicePayments = source.invoicePayments.toDecimal(),
                invoiceDilutions = source.invoiceDilutions.toDecimal(),
                originationNetwork = source.originationNetwork,
                currency = source.amount.token.currencyCode,
                siteId = source.siteId
        )
    }
}