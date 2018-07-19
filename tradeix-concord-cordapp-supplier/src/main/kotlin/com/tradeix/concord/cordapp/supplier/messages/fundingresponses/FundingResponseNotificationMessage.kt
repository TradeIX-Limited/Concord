package com.tradeix.concord.cordapp.supplier.messages.fundingresponses

import net.corda.core.serialization.CordaSerializable
import java.math.BigDecimal

@CordaSerializable
data class FundingResponseNotificationMessage(
        val externalId: String,
        val fundingRequestExternalId: String?,
        val invoiceExternalIds: Collection<String>,
        val supplier: String,
        val funder: String,
        val purchaseValue: BigDecimal,
        val currency: String,
        val advanceInvoiceValue: BigDecimal,
        val discountValue: BigDecimal,
        val baseRate: BigDecimal
)