package com.tradeix.concord.shared.domain.states

import com.tradeix.concord.shared.domain.enumerations.FundingResponseStatus
import net.corda.core.contracts.Amount
import net.corda.core.contracts.LinearState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.AbstractParty
import java.util.*

data class FundingResponseState(
        override val linearId: UniqueIdentifier,
        val fundingRequestId: UniqueIdentifier,
        val supplier: AbstractParty,
        val funder: AbstractParty,
        val purchaseValue: Amount<Currency>,
        val status: FundingResponseStatus
) : LinearState {

    override val participants: List<AbstractParty> get() = listOf(supplier, funder)

}