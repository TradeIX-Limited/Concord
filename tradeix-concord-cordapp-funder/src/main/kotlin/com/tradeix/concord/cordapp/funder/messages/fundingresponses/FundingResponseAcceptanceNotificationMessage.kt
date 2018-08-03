package com.tradeix.concord.cordapp.funder.messages.fundingresponses

import com.fasterxml.jackson.annotation.JsonProperty
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class FundingResponseAcceptanceNotificationMessage(
        @JsonProperty("FundingResponseId") val externalId: String, // TODO : Check TIX property
        @JsonProperty(" ") val transactionId: String // TODO : Check TIX property
)