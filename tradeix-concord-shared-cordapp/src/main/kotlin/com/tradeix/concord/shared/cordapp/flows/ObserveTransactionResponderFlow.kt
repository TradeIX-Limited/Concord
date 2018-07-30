package com.tradeix.concord.shared.cordapp.flows

import co.paralleluniverse.fibers.Suspendable
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.FlowSession
import net.corda.core.flows.InitiatedBy
import net.corda.core.flows.ReceiveTransactionFlow
import net.corda.core.node.StatesToRecord
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.config.Configurator

@InitiatedBy(ObserveTransactionInitiatorFlow::class)
class ObserveTransactionResponderFlow(private val flowSession: FlowSession) : FlowLogic<Unit>() {

    @Suspendable
    override fun call() {
        Configurator.setLevel(logger.name, Level.DEBUG)
        logger.debug("Transaction Observation Responder")
        subFlow(ReceiveTransactionFlow(flowSession, true, StatesToRecord.ALL_VISIBLE))
    }
}