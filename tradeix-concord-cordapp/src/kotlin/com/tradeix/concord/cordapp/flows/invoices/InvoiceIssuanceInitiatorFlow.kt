package com.tradeix.concord.cordapp.flows.invoices

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.shared.messages.invoices.InvoiceIssuanceRequestMessage
import net.corda.core.flows.FlowException
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.InitiatingFlow
import net.corda.core.flows.StartableByRPC
import net.corda.core.transactions.SignedTransaction

@InitiatingFlow
@StartableByRPC
abstract class InvoiceIssuanceInitiatorFlow(
        protected val message: InvoiceIssuanceRequestMessage) : FlowLogic<SignedTransaction>() {

    @Suspendable
    override fun call(): SignedTransaction {
        throw FlowException("You are not permitted to execute the invoice issuance initiator flow.")
    }
}