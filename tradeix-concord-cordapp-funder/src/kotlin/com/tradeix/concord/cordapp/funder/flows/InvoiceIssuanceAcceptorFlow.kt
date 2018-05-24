package com.tradeix.concord.cordapp.funder.flows

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.shared.cordapp.invoices.InvoiceIssuanceInitiatorFlow
import com.tradeix.concord.shared.domain.states.InvoiceState
import net.corda.core.contracts.requireThat
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.FlowSession
import net.corda.core.flows.InitiatedBy
import net.corda.core.flows.SignTransactionFlow
import net.corda.core.transactions.SignedTransaction

@InitiatedBy(InvoiceIssuanceInitiatorFlow::class)
class InvoiceIssuanceAcceptorFlow(private val flowSession: FlowSession) : FlowLogic<SignedTransaction>() {

    @Suspendable
    override fun call(): SignedTransaction {
        val signTransactionFlow = object : SignTransactionFlow(flowSession) {
            override fun checkTransaction(stx: SignedTransaction) = requireThat {
                val output = stx.tx.outputs.single().data
                "This must be an invoice transaction." using (output is InvoiceState)
            }
        }

        return subFlow(signTransactionFlow)
    }
}