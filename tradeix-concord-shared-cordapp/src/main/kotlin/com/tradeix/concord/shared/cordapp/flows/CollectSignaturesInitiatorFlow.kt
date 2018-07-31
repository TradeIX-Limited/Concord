package com.tradeix.concord.shared.cordapp.flows

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.shared.extensions.flowSessionsFor
import net.corda.core.flows.CollectSignaturesFlow
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.InitiatingFlow
import net.corda.core.identity.Party
import net.corda.core.transactions.SignedTransaction
import net.corda.core.utilities.ProgressTracker
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.config.Configurator

@InitiatingFlow
class CollectSignaturesInitiatorFlow(
        private val transaction: SignedTransaction,
        private val counterparties: Iterable<Party>,
        private val tracker: ProgressTracker
) : FlowLogic<SignedTransaction>() {

    @Suspendable
    override fun call(): SignedTransaction {
        Configurator.setLevel(logger.name, Level.DEBUG)
        logger.debug("Initiating Signatures Collection")
        return subFlow(CollectSignaturesFlow(transaction, flowSessionsFor(counterparties), tracker))
    }
}