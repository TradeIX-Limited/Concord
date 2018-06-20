package com.tradeix.concord.tests.unit.flows.fundingresponse

import com.tradeix.concord.cordapp.funder.flows.FundingResponseFlow
import com.tradeix.concord.shared.messages.fundingresponse.FundingResponseRequestMessage
import net.corda.core.transactions.SignedTransaction
import net.corda.core.utilities.getOrThrow
import net.corda.testing.node.MockNetwork
import net.corda.testing.node.StartedMockNode

object FundingResponseFlowTestHelper {

    fun issue(
            network: MockNetwork,
            initiator: StartedMockNode,
            message: FundingResponseRequestMessage): SignedTransaction {
        val future = initiator.startFlow(FundingResponseFlow(message))
        network.runNetwork()
        return future.getOrThrow()
    }

}