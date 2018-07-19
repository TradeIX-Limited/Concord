package com.tradeix.concord.shared.client.mappers

import com.tradeix.concord.shared.client.messages.invoices.InvoiceResponseMessage
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.mapper.Mapper

class InvoiceResponseMapper : Mapper<InvoiceState, InvoiceResponseMessage>() {

    override fun map(source: InvoiceState): InvoiceResponseMessage {
        return InvoiceResponseMessage(
                externalId = source.linearId.externalId!!,
                buyer = source.buyer.toString(),
                buyerCompanyReference = source.buyer.companyReference.toString(),
                supplier = source.supplier.toString(),
                supplierCompanyReference = source.supplier.companyReference.toString(),
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
                siteId = source.siteId,
                tradeDate = source.tradeDate,
                tradePaymentDate = source.tradePaymentDate
        )
    }
}