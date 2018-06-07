package com.tradeix.concord.cordapp.funder.flows

import co.paralleluniverse.fibers.Suspendable
import com.tradeix.concord.shared.cordapp.flows.invoices.InvoiceIssuanceAcceptorFlow
import com.tradeix.concord.shared.cordapp.flows.invoices.InvoiceIssuanceInitiatorFlow
import net.corda.core.flows.FlowSession
import net.corda.core.flows.InitiatedBy
import net.corda.core.flows.ReceiveTransactionFlow
import net.corda.core.flows.StartableByRPC
import net.corda.core.node.StatesToRecord
import net.corda.core.transactions.SignedTransaction

@StartableByRPC
@InitiatedBy(InvoiceIssuanceInitiatorFlow::class)
class InvoiceIssuanceAcceptorFlow(flowSession: FlowSession) : InvoiceIssuanceAcceptorFlow(flowSession) {

    @Suspendable
    override fun call(): SignedTransaction {
        val flow = ReceiveTransactionFlow(
                otherSideSession = flowSession,
                checkSufficientSignatures = true,
                statesToRecord = StatesToRecord.ALL_VISIBLE
        )

        return subFlow(flow)
    }
}