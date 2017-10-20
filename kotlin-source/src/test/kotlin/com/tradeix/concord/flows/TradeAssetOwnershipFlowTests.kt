package com.tradeix.concord.flows

import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage
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
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.fail

class TradeAssetOwnershipFlowTests {
    lateinit var network: MockNetwork
    lateinit var mockBuyerNode: StartedNode<MockNetwork.MockNode>
    lateinit var mockSupplierNode: StartedNode<MockNetwork.MockNode>
    lateinit var mockConductorNode: StartedNode<MockNetwork.MockNode>
    lateinit var mockFunderNode: StartedNode<MockNetwork.MockNode>
    lateinit var mockBuyer: Party
    lateinit var mockSupplier: Party
    lateinit var mockConductor: Party
    lateinit var mockFunder: Party

    @Before
    fun setup() {
        setCordappPackages("com.tradeix.concord.contracts")

        network = MockNetwork()

        val nodes = network.createSomeNodes(4)

        mockBuyerNode = nodes.partyNodes[0]
        mockSupplierNode = nodes.partyNodes[1]
        mockConductorNode = nodes.partyNodes[2]
        mockFunderNode = nodes.partyNodes[3]

        mockBuyer = mockBuyerNode.info.chooseIdentity()
        mockSupplier = mockSupplierNode.info.chooseIdentity()
        mockConductor = mockConductorNode.info.chooseIdentity()
        mockFunder = mockFunderNode.info.chooseIdentity()

        nodes.partyNodes.forEach {
            it.registerInitiatedFlow(TradeAssetIssuance.Acceptor::class.java)
            it.registerInitiatedFlow(TradeAssetOwnership.Acceptor::class.java)
        }
    }

    @After
    fun tearDown() {
        unsetCordappPackages()
        network.stopNodes()
    }

    @Test
    fun `SignedTransaction returned by the flow is signed by the initiator`() {
        val signedTx = getOwnershipSignedTransaction()

        signedTx.verifySignaturesExcept(mockBuyer.owningKey, mockConductor.owningKey, mockFunder.owningKey)
    }

    @Test
    fun `SignedTransaction returned by the flow is signed by the acceptor`() {
        val signedTx = getOwnershipSignedTransaction()

        signedTx.verifySignaturesExcept(mockSupplier.owningKey)
    }

    @Test
    fun `flow records a transaction in all counter-party vaults`() {
        val signedTx = getOwnershipSignedTransaction()

        for (node in listOf(mockBuyerNode, mockSupplierNode, mockConductorNode, mockFunderNode)) {
            assertEquals(signedTx, node.services.validatedTransactions.getTransaction(signedTx.id))
        }
    }

    @Test
    fun `recorded transaction has a single input and a single output`() {
        val signedTx = getOwnershipSignedTransaction()

        for (node in listOf(mockBuyerNode, mockSupplierNode, mockConductorNode, mockFunderNode)) {
            val recordedTx = node.services.validatedTransactions.getTransaction(signedTx.id) ?: fail()
            assert(recordedTx.inputs.size == 1)
            assert(recordedTx.tx.outputs.size == 1)
        }
    }

    private fun getOwnershipSignedTransaction(): SignedTransaction {
        val issuanceFuture = mockConductorNode
                .services
                .startFlow(TradeAssetIssuance.InitiatorFlow(
                        TradeAssetIssuanceRequestMessage(
                                linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000")),
                                buyer = mockBuyer.name,
                                supplier = mockSupplier.name,
                                conductor = mockConductor.name,
                                assetId = "MOCK_ASSET",
                                value = BigDecimal.ONE,
                                currency = "GBP"
                        ))).resultFuture

        network.runNetwork()
        val stx = issuanceFuture.getOrThrow()
        val inputStateAndRef: StateAndRef<TradeAssetState> = stx.tx.outRef(stx.tx.outputStates.single())

        val ownershipFuture = mockConductorNode
                .services
                .startFlow(TradeAssetOwnership.BuyerFlow(
                        inputState = inputStateAndRef,
                        newOwner = mockFunder)).resultFuture

        network.runNetwork()

        return ownershipFuture.getOrThrow()
    }
}