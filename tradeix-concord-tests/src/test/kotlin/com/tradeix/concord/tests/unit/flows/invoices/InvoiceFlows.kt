package com.tradeix.concord.tests.unit.flows.invoices

import com.tradeix.concord.cordapp.supplier.flows.invoices.InvoiceAmendmentInitiatorFlow
import com.tradeix.concord.cordapp.supplier.flows.invoices.InvoiceCancellationInitiatorFlow
import com.tradeix.concord.cordapp.supplier.flows.invoices.InvoiceIssuanceInitiatorFlow
import com.tradeix.concord.cordapp.supplier.flows.invoices.InvoiceTransferInitiatorFlow
import com.tradeix.concord.cordapp.supplier.messages.invoices.InvoiceCancellationTransactionRequestMessage
import com.tradeix.concord.cordapp.supplier.messages.invoices.InvoiceTransactionRequestMessage
import com.tradeix.concord.cordapp.supplier.messages.invoices.InvoiceTransferTransactionRequestMessage
import net.corda.core.transactions.SignedTransaction
import net.corda.core.utilities.getOrThrow
import net.corda.testing.node.MockNetwork
import net.corda.testing.node.StartedMockNode

object InvoiceFlows {
    fun issue(
            network: MockNetwork,
            initiator: StartedMockNode,
            message: InvoiceTransactionRequestMessage): SignedTransaction {
        val future = initiator.startFlow(InvoiceIssuanceInitiatorFlow(message))
        network.runNetwork()
        return future.getOrThrow()
    }

    fun amend(
            network: MockNetwork,
            initiator: StartedMockNode,
            message: InvoiceTransactionRequestMessage): SignedTransaction {
        val future = initiator.startFlow(InvoiceAmendmentInitiatorFlow(message))
        network.runNetwork()
        return future.getOrThrow()
    }

    fun cancel(
            network: MockNetwork,
            initiator: StartedMockNode,
            message: InvoiceCancellationTransactionRequestMessage): SignedTransaction {
        val future = initiator.startFlow(InvoiceCancellationInitiatorFlow(message))
        network.runNetwork()
        return future.getOrThrow()
    }

    fun transfer(
            network: MockNetwork,
            initiator: StartedMockNode,
            message: InvoiceTransferTransactionRequestMessage): SignedTransaction {
        val future = initiator.startFlow(InvoiceTransferInitiatorFlow(message))
        network.runNetwork()
        return future.getOrThrow()
    }
}