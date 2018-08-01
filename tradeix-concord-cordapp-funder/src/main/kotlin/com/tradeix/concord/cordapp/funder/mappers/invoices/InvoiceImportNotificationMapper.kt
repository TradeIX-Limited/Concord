package com.tradeix.concord.cordapp.funder.mappers.invoices

import com.tradeix.concord.cordapp.funder.messages.invoices.InvoiceImportNotificationMessage
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.mapper.Mapper
import net.corda.core.contracts.hash
import java.time.LocalDateTime

class InvoiceImportNotificationMapper : Mapper<InvoiceState, InvoiceImportNotificationMessage>() {

    override fun map(source: InvoiceState): InvoiceImportNotificationMessage {
        return InvoiceImportNotificationMessage(
                networkInvoiceUId = source.linearId.externalId.toString(),
                invoiceVersion = source.invoiceVersion,
                invoiceVersionDate = LocalDateTime.now(), // TODO : REVIEW MAPPING
                buyerRef = source.buyer.companyReference!!,
                supplierRef = source.supplier.companyReference!!,
                invoiceNumber = source.invoiceNumber,
                invoiceCurrency = source.amount.token.currencyCode,
                invoiceDate = source.invoiceDate,
                invoiceDueDate = source.dueDate,
                invoiceAmount = source.amount.toDecimal(),
                cashPaidToDate = source.invoicePayments.toDecimal(),
                totalOutstanding = source.totalOutstanding.toDecimal(),
                reference = source.reference,
                expectedSettlementDate = source.settlementDate, // TODO : REVIEW MAPPING
                invoicePaidDate = null, // TODO : NOT MAPPED!
                siteId = source.siteId,
                cancelled = "N", // TODO : NOT MAPPED!
                closeDate = null, // TODO : NOT MAPPED!
                hash = source.hash().toString(),
                shippingCompanyId = "", // TODO : NOT MAPPED!
                trackingNumber = "", // TODO : NOT MAPPED!
                purchaseOrderNumber = "" // TODO : NOT MAPPED!
        )
    }
}