package com.tradeix.concord.flow

import com.tradeix.concord.state.PurchaseOrderState
import groovy.util.GroovyTestCase.assertEquals
import net.corda.node.internal.StartedNode
import net.corda.testing.node.MockNetwork
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*
import net.corda.core.contracts.UniqueIdentifier
import net.corda.finance.POUNDS
import net.corda.testing.*
import net.corda.core.identity.Party
import net.corda.core.utilities.getOrThrow

class PurchaseOrderIssuanceFlowTests {
    lateinit var net: MockNetwork
    lateinit var mockBuyerNode: StartedNode<MockNetwork.MockNode>
    lateinit var mockSupplierNode: StartedNode<MockNetwork.MockNode>
    lateinit var mockConductorNode: StartedNode<MockNetwork.MockNode>

    lateinit var mockBuyer: Party
    lateinit var mockSupplier: Party
    lateinit var mockConductor: Party

    @Before
    fun setup() {
        setCordappPackages("com.tradeix.concord.contract")
        net = MockNetwork()
        val nodes = net.createSomeNodes(3)
        mockBuyerNode = nodes.partyNodes[0]
        mockSupplierNode = nodes.partyNodes[1]
        mockConductorNode = nodes.partyNodes[2]

        mockBuyer = mockBuyerNode.info.chooseIdentity()
        mockSupplier = mockSupplierNode.info.chooseIdentity()
        mockConductor = mockConductorNode.info.chooseIdentity()

        nodes.partyNodes.forEach { it.registerInitiatedFlow(PurchaseOrderIssuanceFlow.Acceptor::class.java) }

        net.runNetwork()
    }

    @After
    fun tearDown() {
        unsetCordappPackages()
        net.stopNodes()
    }

    @Test
    fun `SignedTransaction returned by the flow is signed by the initiator`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val flow = PurchaseOrderIssuanceFlow.Initiator(
                linearId = linearId,
                amount = 1.POUNDS,
                buyer = mockBuyer,
                supplier = mockSupplier,
                conductor = mockConductor)
        val future = mockBuyerNode.services.startFlow(flow).resultFuture
        net.runNetwork()

        val signedTx = future.getOrThrow()
        signedTx.verifySignaturesExcept(mockSupplier.owningKey, mockConductor.owningKey)
    }

    @Test
    fun `SignedTransaction returned by the flow is signed by the acceptor`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val flow = PurchaseOrderIssuanceFlow.Initiator(
                linearId = linearId,
                amount = 1.POUNDS,
                buyer = mockBuyer,
                supplier = mockSupplier,
                conductor = mockConductor)
        val future = mockBuyerNode.services.startFlow(flow).resultFuture
        net.runNetwork()

        val signedTx = future.getOrThrow()
        signedTx.verifySignaturesExcept(mockBuyer.owningKey)
    }

    @Test
    fun `flow records a transaction in both parties' vaults`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val flow = PurchaseOrderIssuanceFlow.Initiator(
                linearId = linearId,
                amount = 1.POUNDS,
                buyer = mockBuyer,
                supplier = mockSupplier,
                conductor = mockConductor)
        val future = mockBuyerNode.services.startFlow(flow).resultFuture
        net.runNetwork()
        val signedTx = future.getOrThrow()

        // We check the recorded transaction in both vaults.
        for (node in listOf(mockBuyerNode, mockSupplierNode, mockConductorNode)) {
            assertEquals(signedTx, node.services.validatedTransactions.getTransaction(signedTx.id))
        }
    }

    @Test
    fun `recorded transaction has no inputs and a single output`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val flow = PurchaseOrderIssuanceFlow.Initiator(
                linearId = linearId,
                amount = 1.POUNDS,
                buyer = mockBuyer,
                supplier = mockSupplier,
                conductor = mockConductor)
        val future = mockBuyerNode.services.startFlow(flow).resultFuture
        net.runNetwork()
        val signedTx = future.getOrThrow()

        for (node in listOf(mockBuyerNode, mockSupplierNode, mockConductorNode)) {
            val recordedTx = node.services.validatedTransactions.getTransaction(signedTx.id)
            val txOutputs = recordedTx!!.tx.outputs
            assert(txOutputs.size == 1)

            val recordedState = txOutputs[0].data as PurchaseOrderState
            assertEquals(recordedState.purchaseOrder.amount, 1.POUNDS)
            assertEquals(recordedState.buyer, mockBuyer)
            assertEquals(recordedState.supplier, mockSupplier)
        }
    }
}
