package com.tradeix.concord.cordapp.flows.invoices

import co.paralleluniverse.fibers.Suspendable
import net.corda.core.flows.FlowException
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.FlowSession
import net.corda.core.flows.InitiatedBy
import net.corda.core.transactions.SignedTransaction

@InitiatedBy(InvoiceIssuanceInitiatorFlow::class)
abstract class InvoiceIssuanceAcceptorFlow(
        protected val flowSession: FlowSession) : FlowLogic<SignedTransaction>() {

    @Suspendable
    override fun call(): SignedTransaction {
        throw FlowException("You are not permitted to execute the invoice issuance acceptor flow.")
    }
}