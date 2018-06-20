package com.tradeix.concord.tests.unit.flows.fundingresponse

import com.tradeix.concord.shared.cordapp.flows.CollectSignaturesResponderFlow
import com.tradeix.concord.shared.mockdata.MockFundingResponse.createFundingResponse
import com.tradeix.concord.shared.mockdata.ParticipantType
import com.tradeix.concord.tests.unit.flows.FlowTest
import net.corda.testing.node.StartedMockNode
import org.junit.Ignore
import kotlin.test.assertEquals
import kotlin.test.fail

class FundingResponseFlowTests : FlowTest() {

    override fun configureNode(node: StartedMockNode, type: ParticipantType) {
        node.registerInitiatedFlow(CollectSignaturesResponderFlow::class.java)
    }

    @Ignore
    fun `Funding Response flow should be signed by the initiator`() {
        val transaction = FundingResponseFlowTestHelper.issue(
                network = network,
                initiator = funder1.node,
                message = createFundingResponse(
                        funder = funder1.name,
                        supplier = supplier1.name
                )
        )

        transaction.verifyRequiredSignatures()
    }



    @Ignore
    fun `Funding Response flow records a transaction in all counter-party vaults`() {
        val transaction = FundingResponseFlowTestHelper.issue(
                network = network,
                initiator = funder1.node,
                message = createFundingResponse(
                        funder = funder1.name,
                        supplier = supplier1.name
                )
        )

        listOf(supplier1.node,  funder1.node).forEach {
            assertEquals(transaction, it.services.validatedTransactions.getTransaction(transaction.id))
        }
    }

    @Ignore
    fun `Funding Response flow has zero inputs and atleast one output`() {
        val transaction = FundingResponseFlowTestHelper.issue(
                network = network,
                initiator = funder1.node,
                message = createFundingResponse(
                        funder = funder1.name,
                        supplier = supplier1.name
                )
        )

        listOf(supplier1.node, funder1.node).forEach {
            val recordedTransaction = it.services.validatedTransactions.getTransaction(transaction.id) ?: fail()
            assertEquals(0, recordedTransaction.tx.inputs.size)
            assertEquals(1, recordedTransaction.tx.outputs.size)
        }
    }
}