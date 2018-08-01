package com.tradeix.concord.cordapp.funder.messages.fundingresponses

import com.fasterxml.jackson.annotation.JsonProperty
import net.corda.core.serialization.CordaSerializable
import java.time.LocalDateTime

@CordaSerializable
class FundingResponseImportMessage(
        @JsonProperty("BuyerRef") val buyerRef: String,
)