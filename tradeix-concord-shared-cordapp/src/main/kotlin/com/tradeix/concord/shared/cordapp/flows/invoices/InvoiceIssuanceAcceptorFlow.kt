package com.tradeix.concord.shared.cordapp.flows.invoices

import co.paralleluniverse.fibers.Suspendable
import net.corda.core.flows.*
import net.corda.core.transactions.SignedTransaction

@StartableByRPC
@InitiatedBy(InvoiceIssuanceInitiatorFlow::class)
open class InvoiceIssuanceAcceptorFlow(protected val flowSession: FlowSession) : FlowLogic<SignedTransaction>() {

    @Suspendable
    override fun call(): SignedTransaction {
        return subFlow(object : SignTransactionFlow(flowSession) {
            override fun checkTransaction(stx: SignedTransaction) {
                // TODO : Implement checks
            }
        })
    }
}