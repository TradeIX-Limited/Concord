package com.tradeix.concord.shared.messages.fundingresponse

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class FundingResponseAcceptanceRequestMessage(val externalId: String? = null)