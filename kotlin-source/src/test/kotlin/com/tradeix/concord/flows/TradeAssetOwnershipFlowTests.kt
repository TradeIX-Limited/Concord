package com.tradeix.concord.flows

import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage
import com.tradeix.concord.messages.TradeAssetOwnershipRequestMessage
import net.corda.core.identity.Party
import net.corda.core.transactions.SignedTransaction
import net.corda.core.utilities.getOrThrow
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
import kotlin.test.assertFailsWith
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
            it.registerInitiatedFlow(TradeAssetIssuance.AcceptorFlow::class.java)
            it.registerInitiatedFlow(TradeAssetOwnership.AcceptorFlow::class.java)
        }
    }

    @After
    fun tearDown() {
        unsetCordappPackages()
        network.stopNodes()
    }

    @Test
    fun `Absence of External ID in message should result in error`() {
        val message = TradeAssetOwnershipRequestMessage(
                correlationId = "TEST_CORRELATION_ID",
                externalIds = null,
                newOwner = mockFunder.name,
                tryCount = 0
        )

        val exception = assertFailsWith<FlowValidationException>("Request validation failed") {
            val future = mockSupplierNode
                    .services
                    .startFlow(TradeAssetOwnership.InitiatorFlow(message))
                    .resultFuture

            network.runNetwork()

            future.getOrThrow()
        }

        assert(exception.validationErrors.size == 1)
        assert(exception.validationErrors.contains("External ID is required for an ownership transaction."))
    }

    @Test
    fun `Absence of new owner in message should result in error`() {
        val message = TradeAssetOwnershipRequestMessage(
                correlationId = "TEST_CORRELATION_ID",
        externalIds = arrayOf("TEST_EXTERNAL_ID"),
                newOwner = null,
                tryCount = 0
        )

        val exception = assertFailsWith<FlowValidationException>("Request validation failed") {
            val future = mockSupplierNode
                    .services
                    .startFlow(TradeAssetOwnership.InitiatorFlow(message))
                    .resultFuture

            network.runNetwork()

            future.getOrThrow()
        }

        assert(exception.validationErrors.size == 1)
        assert(exception.validationErrors.contains("New owner is required for an ownership transaction."))
    }

    @Test
    fun `Conductor initiated SignedTransaction returned by the flow is signed by the initiator`() {
        getOwnershipSignedTransaction(initiator = mockConductorNode)
                .verifySignaturesExcept(
                        mockBuyer.owningKey,
                        mockSupplier.owningKey,
                        mockFunder.owningKey)
    }

    @Test
    fun `Supplier initiated SignedTransaction returned by the flow is signed by the initiator`() {
        getOwnershipSignedTransaction(initiator = mockSupplierNode)
                .verifySignaturesExcept(
                        mockBuyer.owningKey,
                        mockConductor.owningKey,
                        mockFunder.owningKey)
    }

    @Test
    fun `Conductor initiated SignedTransaction returned by the flow is signed by the acceptor`() {
        getOwnershipSignedTransaction(initiator = mockConductorNode)
                .verifySignaturesExcept(
                        mockConductor.owningKey)
    }

    @Test
    fun `Supplier initiated SignedTransaction returned by the flow is signed by the acceptor`() {
        getOwnershipSignedTransaction(initiator = mockSupplierNode)
                .verifySignaturesExcept(
                        mockSupplier.owningKey)
    }

    @Test
    fun `Flow records a transaction in all counter-party vaults`() {
        val signedTransaction = getOwnershipSignedTransaction(initiator = mockSupplierNode)

        for (node in listOf(mockBuyerNode, mockSupplierNode, mockConductorNode, mockFunderNode)) {
            assertEquals(signedTransaction, node.services.validatedTransactions.getTransaction(signedTransaction.id))
        }
    }

    @Test
    fun `Recorded transaction has the same number of inputs as outputs`() {
        val signedTransaction = getOwnershipSignedTransaction(initiator = mockSupplierNode)

        for (node in listOf(mockBuyerNode, mockSupplierNode, mockConductorNode, mockFunderNode)) {
            val recordedTx = node.services.validatedTransactions.getTransaction(signedTransaction.id) ?: fail()
            assert(recordedTx.inputs.size == recordedTx.tx.outputs.size)
        }
    }

    private fun getOwnershipSignedTransaction(initiator: StartedNode<MockNetwork.MockNode>): SignedTransaction {
        val issuanceMessage1 = TradeAssetIssuanceRequestMessage(
                correlationId = "TEST_CORRELATION_ID_123",
                externalId = "TEST_EXTERNAL_ID_123",
                status = "INVOICE",
                buyer = mockBuyer.name,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                value = BigDecimal.ONE,
                currency = "GBP",
                attachmentId = null,
                tryCount = 0
        )

        val issuanceMessage2 = TradeAssetIssuanceRequestMessage(
                correlationId = "TEST_CORRELATION_ID_456",
                externalId = "TEST_EXTERNAL_ID_456",
                status = "INVOICE",
                buyer = mockBuyer.name,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                value = BigDecimal.ONE,
                currency = "GBP",
                attachmentId = null,
                tryCount = 0
        )

        val issuanceMessage3 = TradeAssetIssuanceRequestMessage(
                correlationId = "TEST_CORRELATION_ID_789",
                externalId = "TEST_EXTERNAL_ID_789",
                status = "INVOICE",
                buyer = mockBuyer.name,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                value = BigDecimal.ONE,
                currency = "GBP",
                attachmentId = null,
                tryCount = 0
        )

        arrayListOf(issuanceMessage1, issuanceMessage2, issuanceMessage3).map {
            val issuanceFuture = initiator
                    .services
                    .startFlow(TradeAssetIssuance.InitiatorFlow(it))
                    .resultFuture

            network.runNetwork()

            issuanceFuture.getOrThrow()
        }


        val ownershipMessage = TradeAssetOwnershipRequestMessage(
                correlationId = "TEST_CORRELATION_ID_321",
                externalIds = arrayOf(
                        "TEST_EXTERNAL_ID_123",
                        "TEST_EXTERNAL_ID_456",
                        "TEST_EXTERNAL_ID_789"),
                newOwner = mockFunder.name,
        tryCount = 0
        )

        val ownershipFuture = initiator
                .services
                .startFlow(TradeAssetOwnership.InitiatorFlow(ownershipMessage))
                .resultFuture

        network.runNetwork()

        return ownershipFuture.getOrThrow()
    }
}