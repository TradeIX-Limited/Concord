package com.tradeix.concord.shared.messages.fundingresponse

import net.corda.core.serialization.CordaSerializable
import java.math.BigDecimal

@CordaSerializable
data class FundingResponseRequestMessage(
        val externalId: String? = null,
        val fundingRequestExternalId: String? = null,
        val invoiceExternalIds: Iterable<String>? = emptyList(),
        val supplier: String? = null,
        val funder: String? = null,
        val purchaseValue: BigDecimal? = null,
        val currency: String? = null
)
