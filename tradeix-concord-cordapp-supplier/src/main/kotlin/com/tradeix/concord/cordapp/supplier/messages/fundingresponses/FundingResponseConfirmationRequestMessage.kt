package com.tradeix.concord.cordapp.supplier.messages.fundingresponses

import com.tradeix.concord.shared.messages.BankAccountRequestMessage
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class FundingResponseConfirmationRequestMessage(
        val externalId: String? = null,
        val bankAccount: BankAccountRequestMessage? = null
)