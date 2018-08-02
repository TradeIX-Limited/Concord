package com.tradeix.concord.cordapp.funder.messages.fundingresponses

import com.fasterxml.jackson.annotation.JsonProperty
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class FundingResponseImportNotificationRequestMessage(@JsonProperty("Items") val items: Collection<FundingResponseImportNotificationMessage>)