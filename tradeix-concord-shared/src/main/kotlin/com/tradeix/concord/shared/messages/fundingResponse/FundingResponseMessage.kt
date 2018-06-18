package com.tradeix.concord.shared.messages.fundingResponse

import net.corda.core.serialization.CordaSerializable
import java.math.BigDecimal
import java.time.LocalDateTime

@CordaSerializable
data class FundingResponseMessage(
        val externalId: String,
        val linearExternalId: String, //TODO: Is this required ?
        val supplier: String,
        val funder: String,
        val invoiceNumber: String,
        val purchaseValue: BigDecimal,
        val currency: String,
        val status: String
)


