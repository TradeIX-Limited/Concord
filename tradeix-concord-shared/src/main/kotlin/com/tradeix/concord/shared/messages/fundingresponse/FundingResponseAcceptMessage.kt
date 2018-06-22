package com.tradeix.concord.shared.messages.fundingresponse

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class FundingResponseAcceptMessage(
        val externalId: String? = null,
        val fundingResponseExternalId: String? = null
)