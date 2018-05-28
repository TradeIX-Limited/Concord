package com.tradeix.concord.shared.cordapp.flows.invoices

import net.corda.core.flows.FlowLogic
import net.corda.core.flows.FlowSession
import net.corda.core.flows.InitiatingFlow
import net.corda.core.flows.StartableByRPC
import net.corda.core.transactions.SignedTransaction

@InitiatingFlow
@StartableByRPC
abstract class InvoiceIssuanceAcceptorFlow(
        protected val flowSession: FlowSession
) : FlowLogic<SignedTransaction>()