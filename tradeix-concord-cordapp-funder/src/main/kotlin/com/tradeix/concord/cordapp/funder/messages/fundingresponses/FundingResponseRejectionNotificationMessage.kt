package com.tradeix.concord.cordapp.funder.messages.fundingresponses

import com.fasterxml.jackson.annotation.JsonProperty
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class FundingResponseRejectionNotificationMessage(
        @JsonProperty("FundingResponseId") val externalId: String // TODO : Check TIX property
)