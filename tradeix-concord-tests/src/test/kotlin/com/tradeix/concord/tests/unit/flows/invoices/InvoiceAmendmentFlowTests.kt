package com.tradeix.concord.tests.unit.flows.invoices

import com.tradeix.concord.shared.cordapp.flows.CollectSignaturesResponderFlow
import com.tradeix.concord.shared.cordapp.flows.ObserveTransactionResponderFlow
import com.tradeix.concord.shared.mockdata.MockInvoices.createMockInvoices
import com.tradeix.concord.shared.mockdata.ParticipantType
import com.tradeix.concord.tests.unit.flows.FlowTest
import net.corda.testing.node.StartedMockNode
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class InvoiceAmendmentFlowTests : FlowTest() {

    override fun configureNode(node: StartedMockNode, type: ParticipantType) {
        if (type == ParticipantType.BUYER) {
            node.registerInitiatedFlow(CollectSignaturesResponderFlow::class.java)
        }

        if (type == ParticipantType.FUNDER) {
            node.registerInitiatedFlow(ObserveTransactionResponderFlow::class.java)
        }
    }

    override fun initialize() {
        InvoiceFlowTestHelper.issue(
                network = network,
                initiator = supplier1.node, message = createMockInvoices(
                count = 3,
                buyer = buyer1.name,
                supplier = supplier1.name,
                observers = listOf(funder1.name, funder2.name, funder3.name)
        ))
    }

    @Test
    fun `Invoice amendment flow should be signed by the initiator`() {
        val transaction = InvoiceFlowTestHelper.amend(
                network = network,
                initiator = supplier1.node, message = createMockInvoices(
                count = 3,
                buyer = buyer1.name,
                supplier = supplier1.name,
                observers = listOf(funder1.name, funder2.name, funder3.name)
        ))

        transaction.verifySignaturesExcept(buyer1.publicKey)
    }

    @Test
    fun `Invoice amendment flow should be signed by the acceptor`() {
        val transaction = InvoiceFlowTestHelper.amend(
                network = network,
                initiator = supplier1.node, message = createMockInvoices(
                count = 3,
                buyer = buyer1.name,
                supplier = supplier1.name,
                observers = listOf(funder1.name, funder2.name, funder3.name)
        ))

        transaction.verifySignaturesExcept(supplier1.publicKey)
    }

    @Test
    fun `Invoice amendment flow records a transaction in all counter-party vaults`() {
        val transaction = InvoiceFlowTestHelper.amend(
                network = network,
                initiator = supplier1.node,
                message = createMockInvoices(
                        count = 3,
                        buyer = buyer1.name,
                        supplier = supplier1.name,
                        observers = listOf(funder1.name, funder2.name, funder3.name)
                )
        )

        listOf(supplier1.node, buyer1.node, funder1.node, funder2.node, funder3.node).forEach {
            assertEquals(transaction, it.services.validatedTransactions.getTransaction(transaction.id))
        }
    }

    @Test
    fun `Invoice amendment flow has an equal number of inputs and outputs`() {
        val transaction = InvoiceFlowTestHelper.amend(
                network = network,
                initiator = supplier1.node,
                message = createMockInvoices(
                        count = 3,
                        buyer = buyer1.name,
                        supplier = supplier1.name,
                        observers = listOf(funder1.name, funder2.name, funder3.name)
                )
        )

        listOf(supplier1.node, buyer1.node, funder1.node, funder2.node, funder3.node).forEach {
            val recordedTransaction = it.services.validatedTransactions.getTransaction(transaction.id) ?: fail()
            assertEquals(recordedTransaction.tx.inputs.size, recordedTransaction.tx.outputs.size)
        }
    }
}