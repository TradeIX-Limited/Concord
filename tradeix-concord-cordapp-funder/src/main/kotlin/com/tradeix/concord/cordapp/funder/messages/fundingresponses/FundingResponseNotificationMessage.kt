package com.tradeix.concord.cordapp.funder.messages.fundingresponses

import com.fasterxml.jackson.annotation.JsonProperty
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class FundingResponseNotificationMessage(
        @JsonProperty("FundingResponseUniqueId") val externalId: String,
        @JsonProperty("Status") val status: String,
        @JsonProperty("ExceptionReason") val exceptionReason: String
)