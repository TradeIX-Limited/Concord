package com.tradeix.concord.flows

import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage
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
import java.math.BigDecimal
import kotlin.test.fail

class TradeAssetIssuanceFlowTests {
    lateinit var network: MockNetwork
    lateinit var mockBuyerNode: StartedNode<MockNetwork.MockNode>
    lateinit var mockSupplierNode: StartedNode<MockNetwork.MockNode>
    lateinit var mockConductorNode: StartedNode<MockNetwork.MockNode>

    lateinit var mockBuyer: Party
    lateinit var mockSupplier: Party
    lateinit var mockConductor: Party

    @Before
    fun setup() {
        setCordappPackages("com.tradeix.concord.contracts")
        network = MockNetwork()
        val nodes = network.createSomeNodes(3)
        mockBuyerNode = nodes.partyNodes[0]
        mockSupplierNode = nodes.partyNodes[1]
        mockConductorNode = nodes.partyNodes[2]

        mockBuyer = mockBuyerNode.info.chooseIdentity()
        mockSupplier = mockSupplierNode.info.chooseIdentity()
        mockConductor = mockConductorNode.info.chooseIdentity()

        nodes.partyNodes.forEach { it.registerInitiatedFlow(TradeAssetIssuance.Acceptor::class.java) }

        network.runNetwork()
    }

    @After
    fun tearDown() {
        unsetCordappPackages()
        network.stopNodes()
    }

    @Test
    fun `SignedTransaction returned by the flow is signed by the initiator`() {
        val flow = TradeAssetIssuance.InitiatorFlow(TradeAssetIssuanceRequestMessage(
                linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000")),
                buyer = mockBuyer.name,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                assetId = "MOCK_ASSET",
                value = BigDecimal.ONE,
                currency = "GBP"
        ))
        val future = mockBuyerNode.services.startFlow(flow).resultFuture
        network.runNetwork()

        val signedTx = future.getOrThrow()
        signedTx.verifySignaturesExcept(mockConductor.owningKey, mockSupplier.owningKey)
    }

    @Test
    fun `SignedTransaction returned by the flow is signed by the acceptor`() {
        val flow = TradeAssetIssuance.InitiatorFlow(TradeAssetIssuanceRequestMessage(
                linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000")),
                buyer = mockBuyer.name,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                assetId = "MOCK_ASSET",
                value = BigDecimal.ONE,
                currency = "GBP"
        ))
        val future = mockBuyerNode.services.startFlow(flow).resultFuture
        network.runNetwork()

        val signedTx = future.getOrThrow()
        signedTx.verifySignaturesExcept(mockBuyer.owningKey)
    }

    @Test
    fun `flow records a transaction in all counter-party vaults`() {
        val flow = TradeAssetIssuance.InitiatorFlow(TradeAssetIssuanceRequestMessage(
                linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000")),
                buyer = mockBuyer.name,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                assetId = "MOCK_ASSET",
                value = BigDecimal.ONE,
                currency = "GBP"
        ))
        val future = mockBuyerNode.services.startFlow(flow).resultFuture
        network.runNetwork()
        val signedTx = future.getOrThrow()

        // We check the recorded transaction in both vaults.
        for (node in listOf(mockBuyerNode, mockSupplierNode, mockConductorNode)) {
            assertEquals(signedTx, node.services.validatedTransactions.getTransaction(signedTx.id))
        }
    }

    @Test
    fun `recorded transaction has no inputs and a single output`() {
        val flow = TradeAssetIssuance.InitiatorFlow(TradeAssetIssuanceRequestMessage(
                linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000")),
                buyer = mockBuyer.name,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                assetId = "MOCK_ASSET",
                value = BigDecimal.ONE,
                currency = "GBP"
        ))
        val future = mockBuyerNode.services.startFlow(flow).resultFuture
        network.runNetwork()
        val signedTx = future.getOrThrow()

        for (node in listOf(mockBuyerNode, mockSupplierNode, mockConductorNode)) {
            val recordedTx = node.services.validatedTransactions.getTransaction(signedTx.id) ?: fail()
            assert(recordedTx.inputs.isEmpty())
            assert(recordedTx.tx.outputs.size == 1)
        }
    }
}
