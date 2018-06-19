package com.tradeix.concord.shared.messages.fundingresponse

import net.corda.core.serialization.CordaSerializable
import java.math.BigDecimal

@CordaSerializable
data class FundingResponseRequestMessage(
        val externalId: String?,
        val fundingRequestExternalId: String?,
        val invoiceExternalIds: Iterable<String>?,
        val supplier: String?,
        val funder: String?,
        val purchaseValue: BigDecimal?,
        val currency: String?
)
