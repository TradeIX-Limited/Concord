package com.tradeix.concord.shared.cordapp.flows.invoices

import com.tradeix.concord.shared.messages.TransactionRequestMessage
import com.tradeix.concord.shared.messages.invoices.InvoiceRequestMessage
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.InitiatingFlow
import net.corda.core.transactions.SignedTransaction

@InitiatingFlow
abstract class InvoiceIssuanceInitiatorFlow(
        protected val message: TransactionRequestMessage<InvoiceRequestMessage>
) : FlowLogic<SignedTransaction>()