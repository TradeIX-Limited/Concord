package com.tradeix.concord.cordapp.supplier.messages.fundingresponses

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class FundingResponseConfirmationResponseMessage(val transactionId: String, val externalId: String)