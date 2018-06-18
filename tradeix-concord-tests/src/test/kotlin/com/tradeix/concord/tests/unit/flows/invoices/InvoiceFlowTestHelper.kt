package com.tradeix.concord.tests.unit.flows.invoices

import com.tradeix.concord.cordapp.supplier.flows.InvoiceAmendmentInitiatorFlow
import com.tradeix.concord.cordapp.supplier.flows.InvoiceCancellationInitiatorFlow
import com.tradeix.concord.cordapp.supplier.flows.InvoiceIssuanceInitiatorFlow
import com.tradeix.concord.shared.messages.CancellationTransactionRequestMessage
import com.tradeix.concord.shared.messages.InvoiceTransactionRequestMessage
import net.corda.core.transactions.SignedTransaction
import net.corda.core.utilities.getOrThrow
import net.corda.testing.node.MockNetwork
import net.corda.testing.node.StartedMockNode

object InvoiceFlowTestHelper {

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
            message: CancellationTransactionRequestMessage): SignedTransaction {
        val future = initiator.startFlow(InvoiceCancellationInitiatorFlow(message))
        network.runNetwork()
        return future.getOrThrow()
    }
}