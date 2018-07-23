package com.tradeix.concord.tests.unit.flows.invoices

import com.tradeix.concord.shared.cordapp.flows.CollectSignaturesResponderFlow
import com.tradeix.concord.shared.mockdata.MockInvoices
import com.tradeix.concord.shared.mockdata.MockInvoices.createMockInvoiceTransfers
import com.tradeix.concord.shared.mockdata.ParticipantType
import com.tradeix.concord.tests.unit.flows.FlowTest
import net.corda.testing.node.StartedMockNode
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class InvoiceTransferFlowTests : FlowTest() {

    override fun configureNode(node: StartedMockNode, type: ParticipantType) {
        node.registerInitiatedFlow(CollectSignaturesResponderFlow::class.java)
    }

    override fun initialize() {
        InvoiceFlows.issue(
                network = network,
                initiator = supplier1.node,
                message = MockInvoices.createMockInvoices(
                        count = 3,
                        buyer = buyer1.name,
                        supplier = supplier1.name,
                        observers = listOf(funder1.name, funder2.name, funder3.name)
                )
        )
    }

    @Test
    fun `Invoice transfer flow should be signed by the initiator`() {
        val transaction = InvoiceFlows.transfer(
                network = network,
                initiator = supplier1.node,
                message = createMockInvoiceTransfers(
                        count = 3,
                        owner = funder1.name
                )
        )

        transaction.verifySignaturesExcept(buyer1.publicKey, funder1.publicKey)
    }

    @Test
    fun `Invoice transfer flow should be signed by the acceptor`() {
        val transaction = InvoiceFlows.transfer(
                network = network,
                initiator = supplier1.node,
                message = createMockInvoiceTransfers(
                        count = 3,
                        owner = funder1.name
                )
        )

        transaction.verifySignaturesExcept(supplier1.publicKey)
    }

    @Test
    fun `Invoice transfer flow records a transaction in all counter-party vaults`() {
        val transaction = InvoiceFlows.transfer(
                network = network,
                initiator = supplier1.node,
                message = createMockInvoiceTransfers(
                        count = 3,
                        owner = funder1.name
                )
        )

        listOf(supplier1.node, buyer1.node, funder1.node).forEach {
            assertEquals(transaction, it.services.validatedTransactions.getTransaction(transaction.id))
        }
    }

    @Test
    fun `Invoice transfer flow has an equal number of inputs and outputs`() {
        val transaction = InvoiceFlows.transfer(
                network = network,
                initiator = supplier1.node,
                message = createMockInvoiceTransfers(
                        count = 3,
                        owner = funder1.name
                )
        )

        listOf(supplier1.node, buyer1.node, funder1.node).forEach {
            val recordedTransaction = it.services.validatedTransactions.getTransaction(transaction.id) ?: fail()
            assertEquals(recordedTransaction.tx.inputs.size, recordedTransaction.tx.outputs.size)
        }
    }
}