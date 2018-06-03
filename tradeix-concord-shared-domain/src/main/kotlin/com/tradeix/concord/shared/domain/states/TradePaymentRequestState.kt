package com.tradeix.concord.shared.domain.states

import com.tradeix.concord.shared.models.accounts.BankAccount
import net.corda.core.contracts.Amount
import net.corda.core.contracts.LinearState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.AbstractParty
import java.util.*

data class TradePaymentRequestState(
        override val linearId: UniqueIdentifier,
        val fundingResponseId: UniqueIdentifier,
        val supplier: AbstractParty,
        val funder: AbstractParty,
        val account: BankAccount,
        val amount: Amount<Currency>
) : LinearState {

    override val participants: List<AbstractParty> get() = listOf(supplier, funder)

}