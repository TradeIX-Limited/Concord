package com.tradeix.concord.shared.client.messages.fundingresponses

import net.corda.core.serialization.CordaSerializable
import java.math.BigDecimal

@CordaSerializable
data class FundingResponseResponseMessage(
        val externalId: String,
        val fundingRequestExternalId: String? = null,
        val invoiceExternalIds: Collection<String>,
        val supplier: String,
        val funder: String,
        val purchaseValue: BigDecimal,
        val currency: String,
        val advanceInvoiceValue: BigDecimal,
        val discountValue: BigDecimal,
        val baseRate: BigDecimal,
        val status: String
)