package com.tradeix.concord.tests.unit.flows.invoices

import com.tradeix.concord.cordapp.funder.flows.InvoiceIssuanceAcceptorFlow
import com.tradeix.concord.shared.mockdata.MockInvoices.createMockInvoices
import com.tradeix.concord.tests.unit.flows.FlowTest
import net.corda.testing.node.StartedMockNode
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class InvoiceIssuanceFlowTests : FlowTest() {

    override fun configureNode(node: StartedMockNode) {
        node.registerInitiatedFlow(InvoiceIssuanceAcceptorFlow::class.java)
    }

    @Test
    fun `Invoice issuance flow should be signed by the initiator`() {
        val transaction = InvoiceFlowTestHelper.issue(
                network = network,
                initiator = supplier.node,
                message = createMockInvoices(
                        count = 3,
                        buyer = buyer.name,
                        supplier = supplier.name,
                        observers = listOf(funder1.name, funder2.name, funder3.name)
                )
        )

        transaction.verifyRequiredSignatures()
    }

    @Test
    fun `Invoice issuance flow records a transaction in all counter-party vaults`() {
        val transaction = InvoiceFlowTestHelper.issue(
                network = network,
                initiator = supplier.node,
                message = createMockInvoices(
                        count = 3,
                        buyer = buyer.name,
                        supplier = supplier.name,
                        observers = listOf(funder1.name, funder2.name, funder3.name)
                )
        )

        listOf(supplier.node).forEach {
            assertEquals(transaction, it.services.validatedTransactions.getTransaction(transaction.id))
        }
    }

    @Test
    fun `Invoice issuance flow has zero inputs and a single output`() {
        val transaction = InvoiceFlowTestHelper.issue(
                network = network,
                initiator = supplier.node,
                message = createMockInvoices(
                        count = 3,
                        buyer = buyer.name,
                        supplier = supplier.name,
                        observers = listOf(funder1.name, funder2.name, funder3.name)
                )
        )

        listOf(supplier.node).forEach {
            val recordedTransaction = it.services.validatedTransactions.getTransaction(transaction.id) ?: fail()
            assertEquals(0, recordedTransaction.tx.inputs.size)
            assertEquals(1, recordedTransaction.tx.outputs.size)
        }
    }
}