package com.tradeix.concord.cordapp.supplier.messages

import com.fasterxml.jackson.annotation.JsonProperty
import net.corda.core.serialization.CordaSerializable
import java.math.BigDecimal
import java.time.LocalDateTime

@CordaSerializable
data class InvoiceImportMessage(
        @JsonProperty("NetworkInvoiceUID") val networkInvoiceUId: String,
        @JsonProperty("InvoiceVersion") val invoiceVersion: String,
        @JsonProperty("InvoiceVersionDate") val invoiceVersionDate: LocalDateTime?,
        @JsonProperty("BuyerRef") val buyerRef: String,
        @JsonProperty("SupplierRef") val supplierRef: String,
        @JsonProperty("InvoiceNumber") val invoiceNumber: String,
        @JsonProperty("InvoiceCurrency") val invoiceCurrency: String,
        @JsonProperty("InvoiceDate") val invoiceDate: LocalDateTime?,
        @JsonProperty("InvoiceDueDate") val invoiceDueDate: LocalDateTime?,
        @JsonProperty("InvoiceAmount") val invoiceAmount: BigDecimal?,
        @JsonProperty("CashPaidToDate") val cashPaidToDate: BigDecimal,
        @JsonProperty("TotalOutstanding") val totalOutstanding: BigDecimal,
        @JsonProperty("Reference") val reference: String,
        @JsonProperty("ExpectedSettlementDate") val expectedSettlementDate: LocalDateTime?,
        @JsonProperty("InvoicePaidDate") val invoicePaidDate: LocalDateTime?,
        @JsonProperty("SiteId") val siteId: String,
        @JsonProperty("Cancelled") val cancelled: String,
        @JsonProperty("CloseDate") val closeDate: LocalDateTime?,
        @JsonProperty("Hash") val hash: String,
        @JsonProperty("ShippingCompanyId") val shippingCompanyId: String,
        @JsonProperty("TrackingNumber") val trackingNumber: String,
        @JsonProperty("PONumber") val purchaseOrderNumber: String
)