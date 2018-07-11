package com.tradeix.concord.shared.messages.fundingresponse

import net.corda.core.serialization.CordaSerializable
import java.math.BigDecimal

@CordaSerializable
data class FundingResponseRequestMessage(
        val externalId: String? = null,
        val fundingRequestExternalId: String? = null,
        val invoiceExternalIds: Collection<String>? = null,
        val supplier: String? = null,
        val funder: String? = null,
        val purchaseValue: BigDecimal? = null,
        val currency: String? = null,
        val advanceInvoiceValue: BigDecimal? = null,
        val discountValue: BigDecimal? = null,
        val baseRate: BigDecimal? = null
)
