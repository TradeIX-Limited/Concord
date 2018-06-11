package com.tradeix.concord.shared.cordapp.flows

import co.paralleluniverse.fibers.Suspendable
import net.corda.core.flows.CollectSignaturesFlow
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.FlowSession
import net.corda.core.flows.InitiatingFlow
import net.corda.core.identity.Party
import net.corda.core.transactions.SignedTransaction
import net.corda.core.utilities.ProgressTracker

@InitiatingFlow
class CollectSignaturesInitiatorFlow(
        private val transaction: SignedTransaction,
        private val counterparties: Iterable<Party>,
        private val tracker: ProgressTracker
) : FlowLogic<SignedTransaction>() {

    @Suspendable
    override fun call(): SignedTransaction {
        val flowSessions = mutableListOf<FlowSession>()
        counterparties.forEach { flowSessions.add(initiateFlow(it)) }
        return subFlow(CollectSignaturesFlow(transaction, flowSessions, tracker))
    }
}