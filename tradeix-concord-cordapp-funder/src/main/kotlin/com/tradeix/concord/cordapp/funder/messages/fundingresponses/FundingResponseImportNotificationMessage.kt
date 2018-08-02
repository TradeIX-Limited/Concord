package com.tradeix.concord.cordapp.funder.messages.fundingresponses

import com.fasterxml.jackson.annotation.JsonProperty
import net.corda.core.serialization.CordaSerializable
import java.math.BigDecimal

@CordaSerializable
data class FundingResponseImportNotificationMessage( // TODO : COMPLETE (JsonProperty)
        @JsonProperty("") val externalId: String,
        @JsonProperty("") val fundingRequestExternalId: String?,
        @JsonProperty("") val invoiceExternalIds: Collection<String>,
        @JsonProperty("") val supplier: String,
        @JsonProperty("Funder") val funder: String,
        @JsonProperty("InvoicePurchaseValue") val purchaseValue: BigDecimal,
        @JsonProperty("Currency") val currency: String,
        @JsonProperty("AdvancedInvoiceValue") val advanceInvoiceValue: BigDecimal,
        @JsonProperty("DiscountValue") val discountValue: BigDecimal,
        @JsonProperty("BaseRate") val baseRate: BigDecimal,
        @JsonProperty("") val status: String
)