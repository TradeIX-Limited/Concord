package com.tradeix.concord.flows

import com.tradeix.concord.states.TradeAssetState
import net.corda.core.contracts.StateAndRef
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.Party
import net.corda.core.transactions.SignedTransaction
import net.corda.core.utilities.getOrThrow
import net.corda.finance.POUNDS
import net.corda.node.internal.StartedNode
import net.corda.testing.chooseIdentity
import net.corda.testing.node.MockNetwork
import net.corda.testing.setCordappPackages
import net.corda.testing.unsetCordappPackages
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.fail

class TradeAssetCancellationFlowTests {
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

        nodes.partyNodes.forEach {
            it.registerInitiatedFlow(TradeAssetIssuance.Acceptor::class.java)
            it.registerInitiatedFlow(TradeAssetCancellation.Acceptor::class.java)
        }
    }

    @After
    fun tearDown() {
        unsetCordappPackages()
        network.stopNodes()
    }

    @Test
    fun `SignedTransaction returned by the flow is signed by the initiator`() {
        val signedTx = getCancellationSignedTransaction()
        signedTx.checkSignaturesAreValid()
        mockConductor.owningKey !in signedTx.getMissingSigners()
    }

    @Test
    fun `SignedTransaction returned by the flow is signed by the acceptors`() {
        val signedTx = getCancellationSignedTransaction()
        signedTx.verifySignaturesExcept(mockConductor.owningKey)
    }

    @Test
    fun `flow records a transaction in all counter-party vaults`() {
        val signedTx = getCancellationSignedTransaction()
        for (node in listOf(mockBuyerNode, mockSupplierNode, mockConductorNode)) {
            assertEquals(signedTx, node.services.validatedTransactions.getTransaction(signedTx.id))
        }
    }

    @Test
    fun `recorded transaction has a single input and zero output`() {
        val signedTx = getCancellationSignedTransaction()
        for (node in listOf(mockBuyerNode, mockSupplierNode, mockConductorNode)) {
            val recordedTx = node.services.validatedTransactions.getTransaction(signedTx.id) ?: fail()
            assert(recordedTx.inputs.size == 1)
            assert(recordedTx.tx.outputs.size == 0)
        }
    }

    private fun getCancellationSignedTransaction(): SignedTransaction {
        val linearId = UniqueIdentifier(id = UUID.randomUUID())
        val assetId = "MOCK_ASSET"
        val issuanceFlow = TradeAssetIssuance.BuyerFlow(
                linearId = linearId,
                assetId = assetId,
                buyer = mockBuyer,
                supplier = mockSupplier,
                conductor = mockConductor,
                amount = 1.POUNDS)
        val issuanceFuture = mockConductorNode.services.startFlow(issuanceFlow).resultFuture
        network.runNetwork()
        val stx = issuanceFuture.getOrThrow()
        val inputStateAndRef: StateAndRef<TradeAssetState> = stx.tx.outRef(stx.tx.outputStates.single())
        val flow = TradeAssetCancellation.InitiatorFlow(
                inputState = inputStateAndRef)
        val cancellationFuture = mockConductorNode.services.startFlow(flow).resultFuture
        network.runNetwork()
        return cancellationFuture.getOrThrow()
    }
}