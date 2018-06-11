package com.tradeix.concord.shared.cordapp.mapping

import com.tradeix.concord.shared.cordapp.mapping.invoices.InvoiceAmendmentMapperConfiguration
import com.tradeix.concord.shared.cordapp.mapping.invoices.InvoiceIssuanceMapperConfiguration
import com.tradeix.concord.shared.cordapp.mapping.purchaseorders.PurchaseOrderAmendmentMapperConfiguration
import com.tradeix.concord.shared.cordapp.mapping.purchaseorders.PurchaseOrderIssuanceMapperConfiguration
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.mapper.Mapper
import com.tradeix.concord.shared.mapper.MapperConfiguration
import com.tradeix.concord.shared.messages.invoices.InvoiceMessage

fun Mapper.registerInvoiceMappers() {

    this.addConfiguration("issuance", InvoiceIssuanceMapperConfiguration())
    this.addConfiguration("amendment", InvoiceAmendmentMapperConfiguration())

    this.addConfiguration("response", object : MapperConfiguration<InvoiceState, InvoiceMessage>() {
        override fun map(source: InvoiceState): InvoiceMessage {
            return InvoiceMessage(
                    externalId = source.linearId.externalId,
                    buyer = source.buyer?.nameOrNull()?.toString(),
                    supplier = source.supplier.nameOrNull()?.toString(),
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
    })
}

fun Mapper.registerPurchaseOrderMappers() {
    this.addConfiguration("issuance", PurchaseOrderIssuanceMapperConfiguration())
    this.addConfiguration("amendment", PurchaseOrderAmendmentMapperConfiguration())
}