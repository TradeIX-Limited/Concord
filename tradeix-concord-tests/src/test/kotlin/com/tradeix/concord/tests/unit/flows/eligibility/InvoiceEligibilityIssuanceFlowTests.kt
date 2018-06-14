package com.tradeix.concord.tests.unit.flows.eligibility

import com.tradeix.concord.shared.cordapp.flows.CollectSignaturesResponderFlow
import com.tradeix.concord.shared.mockdata.MockInvoiceEligibility.createMockInvoiceEligibility
import com.tradeix.concord.shared.mockdata.ParticipantType
import com.tradeix.concord.tests.unit.flows.FlowTest
import net.corda.testing.node.StartedMockNode
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class InvoiceEligibilityIssuanceFlowTests : FlowTest() {

    override fun configureNode(node: StartedMockNode, type: ParticipantType) {
        if (type == ParticipantType.SUPPLIER) {
            node.registerInitiatedFlow(CollectSignaturesResponderFlow::class.java)
        }
    }

    @Test
    fun `Invoice eligibility issuance flow should be signed by the funder`() {
        val transaction = InvoiceEligibilityTestFlowHelper.issue(
                network = network,
                initiator = funder1.node,
                message = createMockInvoiceEligibility(
                        count = 3,
                        supplier = supplier1.name
                )
        )

        transaction.verifySignaturesExcept(supplier1.publicKey)
    }

    @Test
    fun `Invoice eligibility issuance flow should be signed by the supplier`() {
        val transaction = InvoiceEligibilityTestFlowHelper.issue(
                network = network,
                initiator = funder1.node,
                message = createMockInvoiceEligibility(
                        count = 3,
                        supplier = supplier1.name
                )
        )

        transaction.verifySignaturesExcept(funder1.publicKey)
    }

    @Test
    fun `Invoice eligibility issuance flow records a transaction in all counter-party vaults`() {
        val transaction = InvoiceEligibilityTestFlowHelper.issue(
                network = network,
                initiator = funder1.node,
                message = createMockInvoiceEligibility(
                        count = 3,
                        supplier = supplier1.name
                )
        )

        listOf(funder1.node, supplier1.node).forEach {
            assertEquals(transaction, it.services.validatedTransactions.getTransaction(transaction.id))
        }
    }

    @Test
    fun `Invoice eligibility issuance flow has zero inputs and more than one output`() {
        val transaction = InvoiceEligibilityTestFlowHelper.issue(
                network = network,
                initiator = funder1.node,
                message = createMockInvoiceEligibility(
                        count = 3,
                        supplier = supplier1.name
                )
        )

        listOf(funder1.node, supplier1.node).forEach {
            val recordedTransaction = it.services.validatedTransactions.getTransaction(transaction.id) ?: fail()
            assertEquals(0, recordedTransaction.tx.inputs.size)
            assertEquals(3, recordedTransaction.tx.outputs.size)
        }
    }
}