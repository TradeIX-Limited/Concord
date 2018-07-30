package com.tradeix.concord.shared.cordapp.flows

import co.paralleluniverse.fibers.Suspendable
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.FlowSession
import net.corda.core.flows.InitiatedBy
import net.corda.core.flows.SignTransactionFlow
import net.corda.core.transactions.SignedTransaction
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.config.Configurator

@InitiatedBy(CollectSignaturesInitiatorFlow::class)
class CollectSignaturesResponderFlow(private val flowSession: FlowSession) : FlowLogic<SignedTransaction>() {

    @Suspendable
    override fun call(): SignedTransaction {
        Configurator.setLevel(logger.name, Level.DEBUG)
        logger.debug("Signatures Collection Responder")
        return subFlow(object : SignTransactionFlow(flowSession) {
            override fun checkTransaction(stx: SignedTransaction) {
            }
        })
    }
}