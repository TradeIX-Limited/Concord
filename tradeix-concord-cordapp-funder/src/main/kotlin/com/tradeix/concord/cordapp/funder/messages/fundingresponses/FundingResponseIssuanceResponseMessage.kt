package com.tradeix.concord.cordapp.funder.messages.fundingresponses

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class FundingResponseIssuanceResponseMessage(val transactionId: String, val externalId: String)