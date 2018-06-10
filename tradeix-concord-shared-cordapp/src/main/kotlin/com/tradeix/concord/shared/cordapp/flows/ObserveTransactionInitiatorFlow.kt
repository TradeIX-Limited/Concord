package com.tradeix.concord.shared.cordapp.flows

import co.paralleluniverse.fibers.Suspendable
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.FlowSession
import net.corda.core.flows.InitiatingFlow
import net.corda.core.flows.SendTransactionFlow
import net.corda.core.transactions.SignedTransaction

@InitiatingFlow
class ObserveTransactionInitiatorFlow(
        private val transaction: SignedTransaction,
        private val flowSessions: Collection<FlowSession>
) : FlowLogic<Unit>() {

    @Suspendable
    override fun call() {
        flowSessions.forEach { subFlow(SendTransactionFlow(it, transaction)) }
    }
}