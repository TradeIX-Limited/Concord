package com.tradeix.concord.shared.cordapp.flows

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.shared.extensions.flowSessionsFor
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.InitiatingFlow
import net.corda.core.flows.SendTransactionFlow
import net.corda.core.identity.Party
import net.corda.core.transactions.SignedTransaction
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.config.Configurator

@InitiatingFlow
class ObserveTransactionInitiatorFlow(
        private val transaction: SignedTransaction,
        private val counterparties: Iterable<Party>
) : FlowLogic<Unit>() {

    @Suspendable
    override fun call() {
        Configurator.setLevel(logger.name, Level.DEBUG)
        logger.debug("Initiating Transaction Observation")
        val flowSessions = flowSessionsFor(counterparties)
        flowSessions.forEach { subFlow(SendTransactionFlow(it, transaction)) }
    }
}