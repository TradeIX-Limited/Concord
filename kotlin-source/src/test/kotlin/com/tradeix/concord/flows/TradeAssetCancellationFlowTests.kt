package com.tradeix.concord.flows

import com.tradeix.concord.exceptions.ValidationException
import com.tradeix.concord.messages.TradeAssetCancellationRequestMessage
import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage
import net.corda.core.identity.Party
import net.corda.core.transactions.SignedTransaction
import net.corda.core.utilities.getOrThrow
import net.corda.node.internal.StartedNode
import net.corda.testing.*
import net.corda.testing.node.MockNetwork
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.fail

class TradeAssetCancellationFlowTests {
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
        mockFunder = mockConductorNode.info.chooseIdentity()

        nodes.partyNodes.forEach {
            it.registerInitiatedFlow(TradeAssetIssuance.AcceptorFlow::class.java)
            it.registerInitiatedFlow(TradeAssetCancellation.AcceptorFlow::class.java)
        }
    }

    @After
    fun tearDown() {
        unsetCordappPackages()
        network.stopNodes()
    }

    @Test
    fun `Absence of Linear ID in message should result in error`() {
        val message = TradeAssetCancellationRequestMessage(
                linearId = null
        )

        assertFailsWith<ValidationException>("Request validation failed") {
            val future = mockSupplierNode
                    .services
                    .startFlow(TradeAssetCancellation.InitiatorFlow(message))
                    .resultFuture

            network.runNetwork()

            future.getOrThrow()
        }

        assert(!message.isValid)
        assert(message.getValidationErrors().size == 1)
        assert(message.getValidationErrors().contains("Linear ID is required for a cancellation transaction."))
    }

    @Test
    fun `Conductor initiated SignedTransaction returned by the flow is signed by the initiator`() {
        generateCancellationSignedTransaction(
                issuanceInitiator = mockConductorNode,
                cancellationInitiator = mockConductorNode)
                .verifySignaturesExcept(
                        mockBuyer.owningKey,
                        mockSupplier.owningKey)
    }

    @Test // TODO : This flow requires amendment to ensure that the status changes before buyer cancellation.
    fun `Buyer initiated SignedTransaction returned by the flow is signed by the initiator`() {
        generateCancellationSignedTransaction(
                issuanceInitiator = mockConductorNode,
                cancellationInitiator = mockBuyerNode)
                .verifySignaturesExcept(
                        mockConductor.owningKey,
                        mockSupplier.owningKey)
    }

    @Test
    fun `Supplier initiated SignedTransaction returned by the flow is signed by the initiator`() {
        generateCancellationSignedTransaction(
                issuanceInitiator = mockConductorNode,
                cancellationInitiator = mockSupplierNode)
                .verifySignaturesExcept(
                        mockBuyer.owningKey,
                        mockConductor.owningKey)
    }

    @Test
    fun `Conductor initiated SignedTransaction returned by the flow is signed by the acceptor`() {
        generateCancellationSignedTransaction(
                issuanceInitiator = mockConductorNode,
                cancellationInitiator = mockConductorNode)
                .verifySignaturesExcept(
                        mockConductor.owningKey)
    }

    @Test // TODO : This flow requires amendment to ensure that the status changes before buyer cancellation.
    fun `Buyer initiated SignedTransaction returned by the flow is signed by the acceptor`() {
        generateCancellationSignedTransaction(
                issuanceInitiator = mockConductorNode,
                cancellationInitiator = mockBuyerNode)
                .verifySignaturesExcept(
                        mockBuyer.owningKey)
    }

    @Test
    fun `Supplier initiated SignedTransaction returned by the flow is signed by the acceptor`() {
        generateCancellationSignedTransaction(
                issuanceInitiator = mockConductorNode,
                cancellationInitiator = mockSupplierNode)
                .verifySignaturesExcept(
                        mockSupplier.owningKey)
    }

    @Test // TODO : Check with R3 whether exit transactions are recorded.
    fun `Flow records a transaction in all counter-party vaults`() {
        val signedTransaction = generateCancellationSignedTransaction(
                issuanceInitiator = mockConductorNode,
                cancellationInitiator = mockConductorNode)

        for (node in listOf(mockBuyerNode, mockSupplierNode, mockConductorNode, mockFunderNode)) {
            assertEquals(signedTransaction, node.services.validatedTransactions.getTransaction(signedTransaction.id))
        }
    }

    @Test // TODO : Check with R3 whether exit transactions are recorded.
    fun `Recorded transaction has a single input and a zero outputs`() {
        val signedTransaction = generateCancellationSignedTransaction(
                issuanceInitiator = mockConductorNode,
                cancellationInitiator = mockConductorNode)

        for (node in listOf(mockBuyerNode, mockSupplierNode, mockConductorNode, mockFunderNode)) {
            val recordedTx = node.services.validatedTransactions.getTransaction(signedTransaction.id) ?: fail()
            assert(recordedTx.inputs.size == 1)
            assert(recordedTx.tx.outputs.isEmpty())
        }
    }

    private fun generateCancellationSignedTransaction(
            issuanceInitiator: StartedNode<MockNetwork.MockNode>,
            cancellationInitiator: StartedNode<MockNetwork.MockNode>): SignedTransaction {
        val issuanceMessage = TradeAssetIssuanceRequestMessage(
                linearId = UUID.fromString("00000000-0000-4000-0000-000000000000"),
                status = "INVOICE",
                buyer = mockBuyer.name,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                assetId = "MOCK_ASSET",
                value = BigDecimal.ONE,
                currency = "GBP",
                attachmentHash = null
        )

        val issuanceFuture = issuanceInitiator
                .services
                .startFlow(TradeAssetIssuance.InitiatorFlow(issuanceMessage))
                .resultFuture

        network.runNetwork()

        issuanceFuture.getOrThrow()

        val cancellationMessage = TradeAssetCancellationRequestMessage(
                linearId = UUID.fromString("00000000-0000-4000-0000-000000000000")
        )

        val cancellationFuture = cancellationInitiator
                .services
                .startFlow(TradeAssetCancellation.InitiatorFlow(cancellationMessage))
                .resultFuture

        network.runNetwork()

        return cancellationFuture.getOrThrow()
    }
}