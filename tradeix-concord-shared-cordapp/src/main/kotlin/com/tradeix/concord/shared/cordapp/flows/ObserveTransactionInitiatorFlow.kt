package com.tradeix.concord.shared.cordapp.flows

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.shared.extensions.flowSessionsFor
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.InitiatingFlow
import net.corda.core.flows.SendTransactionFlow
import net.corda.core.identity.Party
import net.corda.core.transactions.SignedTransaction

@InitiatingFlow
class ObserveTransactionInitiatorFlow(
        private val transaction: SignedTransaction,
        private val counterparties: Iterable<Party>
) : FlowLogic<Unit>() {

    @Suspendable
    override fun call() {
        val flowSessions = flowSessionsFor(counterparties)
        flowSessions.forEach { subFlow(SendTransactionFlow(it, transaction)) }
    }
}