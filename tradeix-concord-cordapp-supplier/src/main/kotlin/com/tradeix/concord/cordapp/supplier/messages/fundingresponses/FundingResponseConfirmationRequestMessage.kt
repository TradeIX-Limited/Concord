package com.tradeix.concord.cordapp.supplier.messages.fundingresponses

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class FundingResponseConfirmationRequestMessage(val externalId: String? = null)