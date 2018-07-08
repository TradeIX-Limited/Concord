package com.tradeix.concord.shared.cordapp.mapping.invoices

import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.mapper.Mapper
import com.tradeix.concord.shared.messages.invoices.InvoiceResponseMessage
import net.corda.core.contracts.hash
import java.time.LocalDateTime

class InvoiceResponseMapper : Mapper<InvoiceState, InvoiceResponseMessage>() {

    override fun map(source: InvoiceState): InvoiceResponseMessage {
        return InvoiceResponseMessage(
                networkInvoiceUId = source.linearId.externalId.toString(),
                invoiceVersion = "", // TODO : NOT MAPPED!
                invoiceVersionDate = LocalDateTime.now(), // TODO : REVIEW MAPPING
                buyerRef = source.buyer.toString(),
                supplierRef = source.supplier.toString(),
                invoiceNumber = "", // TODO : NOT MAPPED!
                invoiceCurrency = source.amount.token.currencyCode,
                invoiceDate = source.invoiceDate,
                invoiceDueDate = source.dueDate,
                invoiceAmount = source.amount.toDecimal(),
                cashPaidToDate = source.invoicePayments.toDecimal(),
                totalOutstanding = source.totalOutstanding.toDecimal(),
                reference = source.reference,
                expectedSettlementDate = source.settlementDate, // TODO : REVIEW MAPPING
                invoicePaidDate = LocalDateTime.now(), // TODO : NOT MAPPED!
                siteId = source.siteId,
                cancelled = false.toString(), // TODO : NOT MAPPED!
                closeDate = LocalDateTime.now(), // TODO : NOT MAPPED!
                hash = source.hash().toString(),
                shippingCompanyId = "", // TODO : NOT MAPPED!
                trackingNumber = "", // TODO : NOT MAPPED!
                purchaseOrderNumber = "" // TODO : NOT MAPPED!
        )
    }
}