package com.tradeix.concord.tests.unit.flows.eligibility

import com.tradeix.concord.cordapp.funder.flows.InvoiceEligibilityIssuanceInitiatorFlow
import com.tradeix.concord.shared.messages.InvoiceEligibilityTransactionRequestMessage
import net.corda.core.transactions.SignedTransaction
import net.corda.core.utilities.getOrThrow
import net.corda.testing.node.MockNetwork
import net.corda.testing.node.StartedMockNode

object InvoiceEligibilityTestFlowHelper {

    fun issue(
            network: MockNetwork,
            initiator: StartedMockNode,
            message: InvoiceEligibilityTransactionRequestMessage): SignedTransaction {
        val future = initiator.startFlow(InvoiceEligibilityIssuanceInitiatorFlow(message))
        network.runNetwork()
        return future.getOrThrow()
    }
}