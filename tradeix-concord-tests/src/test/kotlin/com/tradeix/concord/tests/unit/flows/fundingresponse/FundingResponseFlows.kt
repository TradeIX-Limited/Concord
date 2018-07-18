package com.tradeix.concord.tests.unit.flows.fundingresponse

import com.tradeix.concord.cordapp.funder.flows.FundingResponseIssuanceInitiatorFlow
import com.tradeix.concord.cordapp.supplier.flows.fundingresponses.FundingResponseAcceptanceFlow
import com.tradeix.concord.cordapp.supplier.flows.fundingresponses.FundingResponseRejectionFlow
import com.tradeix.concord.shared.messages.fundingresponse.FundingResponseAcceptanceRequestMessage
import com.tradeix.concord.shared.messages.fundingresponse.FundingResponseRejectionRequestMessage
import com.tradeix.concord.shared.messages.fundingresponse.FundingResponseRequestMessage
import net.corda.core.transactions.SignedTransaction
import net.corda.core.utilities.getOrThrow
import net.corda.testing.node.MockNetwork
import net.corda.testing.node.StartedMockNode

object FundingResponseFlows {
    fun issue(
            network: MockNetwork,
            initiator: StartedMockNode,
            message: FundingResponseRequestMessage): SignedTransaction {
        val future = initiator.startFlow(FundingResponseIssuanceInitiatorFlow(message))
        network.runNetwork()
        return future.getOrThrow()
    }

    fun accept(
            network: MockNetwork,
            initiator: StartedMockNode,
            message: FundingResponseAcceptanceRequestMessage): SignedTransaction {
        val future = initiator.startFlow(FundingResponseAcceptanceFlow(message))
        network.runNetwork()
        return future.getOrThrow()
    }

    fun reject(
            network: MockNetwork,
            initiator: StartedMockNode,
            message: FundingResponseRejectionRequestMessage): SignedTransaction {
        val future = initiator.startFlow(FundingResponseRejectionFlow(message))
        network.runNetwork()
        return future.getOrThrow()
    }
}