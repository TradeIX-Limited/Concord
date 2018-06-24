package com.tradeix.concord.shared.messages.fundingresponse

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class FundingResponseRejectionRequestMessage(val externalId: String? = null)