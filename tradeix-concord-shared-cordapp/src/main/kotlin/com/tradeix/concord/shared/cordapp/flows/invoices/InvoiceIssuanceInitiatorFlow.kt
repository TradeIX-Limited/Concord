package com.tradeix.concord.shared.cordapp.flows.invoices

import com.tradeix.concord.shared.messages.invoices.InvoiceRequestMessage
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.InitiatingFlow
import net.corda.core.flows.StartableByRPC
import net.corda.core.transactions.SignedTransaction

@InitiatingFlow
@StartableByRPC
abstract class InvoiceIssuanceInitiatorFlow(
        protected val message: InvoiceRequestMessage
) : FlowLogic<SignedTransaction>()