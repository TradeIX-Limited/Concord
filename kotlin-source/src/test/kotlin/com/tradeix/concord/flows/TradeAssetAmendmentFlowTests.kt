package com.tradeix.concord.flows

import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.messages.TradeAssetAmendmentRequestMessage
import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage
import com.tradeix.concord.messages.webapi.tradeasset.TradeAssetIssuanceRequestMessage
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

class TradeAssetAmendmentFlowTests {
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
            it.registerInitiatedFlow(TradeAssetAmendment.AcceptorFlow::class.java)
        }
    }

    @After
    fun tearDown() {
        unsetCordappPackages()
        network.stopNodes()
    }

    @Test
    fun `Absence of Correlation ID in message should result in error`() {
        val message = TradeAssetAmendmentRequestMessage(
                correlationId = null,
                externalId = "TEST_EXTERNAL_ID",
                value = null,
                currency = null,
                tryCount = 0
        )

        val exception = assertFailsWith<FlowValidationException>("Request validation failed") {
            val future = mockSupplierNode
                    .services
                    .startFlow(TradeAssetAmendment.InitiatorFlow(message))
                    .resultFuture

            network.runNetwork()

            future.getOrThrow()
        }

        assert(exception.validationErrors.size == 1)
        assert(exception.validationErrors.contains("Correlation ID is required for an amendment transaction."))
    }

    @Test
    fun `Conductor initiated SignedTransaction returned by the flow is signed by the initiator`() {
        generateAmendmentSignedTransaction(
                issuanceInitiator = mockConductorNode,
                amendmentInitiator = mockConductorNode)
                .verifySignaturesExcept(
                        mockBuyer.owningKey,
                        mockSupplier.owningKey)
    }

    @Test // TODO : Buyer cannot amend because it's an INVOICE.
    fun `Buyer initiated SignedTransaction returned by the flow is signed by the initiator`() {
        generateAmendmentSignedTransaction(
                issuanceInitiator = mockConductorNode,
                amendmentInitiator = mockBuyerNode)
                .verifySignaturesExcept(
                        mockConductor.owningKey,
                        mockSupplier.owningKey)
    }

    @Test
    fun `Supplier initiated SignedTransaction returned by the flow is signed by the initiator`() {
        generateAmendmentSignedTransaction(
                issuanceInitiator = mockConductorNode,
                amendmentInitiator = mockSupplierNode)
                .verifySignaturesExcept(
                        mockBuyer.owningKey,
                        mockConductor.owningKey)
    }

    @Test
    fun `Conductor initiated SignedTransaction returned by the flow is signed by the acceptor`() {
        generateAmendmentSignedTransaction(
                issuanceInitiator = mockConductorNode,
                amendmentInitiator = mockConductorNode)
                .verifySignaturesExcept(
                        mockConductor.owningKey)
    }

    @Test // TODO : Buyer cannot amend because it's an INVOICE.
    fun `Buyer initiated SignedTransaction returned by the flow is signed by the acceptor`() {
        generateAmendmentSignedTransaction(
                issuanceInitiator = mockConductorNode,
                amendmentInitiator = mockBuyerNode)
                .verifySignaturesExcept(
                        mockBuyer.owningKey)
    }

    @Test
    fun `Supplier initiated SignedTransaction returned by the flow is signed by the acceptor`() {
        generateAmendmentSignedTransaction(
                issuanceInitiator = mockConductorNode,
                amendmentInitiator = mockSupplierNode)
                .verifySignaturesExcept(
                        mockSupplier.owningKey)
    }

    @Test // TODO : Getting "No transaction in context" error.
    fun `Flow records a transaction in all counter-party vaults`() {
        val signedTransaction = generateAmendmentSignedTransaction(
                issuanceInitiator = mockConductorNode,
                amendmentInitiator = mockConductorNode)

        for (node in listOf(mockBuyerNode, mockSupplierNode, mockConductorNode, mockFunderNode)) {
            assertEquals(signedTransaction, node.services.validatedTransactions.getTransaction(signedTransaction.id))
        }
    }

    @Test // TODO : Getting "No transaction in context" error.
    fun `Recorded transaction has a single input and a single output`() {
        val signedTransaction = generateAmendmentSignedTransaction(
                issuanceInitiator = mockConductorNode,
                amendmentInitiator = mockConductorNode)

        for (node in listOf(mockBuyerNode, mockSupplierNode, mockConductorNode, mockFunderNode)) {
            val recordedTx = node.services.validatedTransactions.getTransaction(signedTransaction.id) ?: fail()
            assert(recordedTx.inputs.size == 1)
            assert(recordedTx.tx.outputs.size == 1)
        }
    }

    private fun generateAmendmentSignedTransaction(
            issuanceInitiator: StartedNode<MockNetwork.MockNode>,
            amendmentInitiator: StartedNode<MockNetwork.MockNode>): SignedTransaction {
        val issuanceMessage = TradeAssetIssuanceRequestMessage(
                correlationId = "TEST_CORRELATION_ID",
                externalId = "TEST_EXTERNAL_ID",
                status = "PURCHASE_ORDER",
                buyer = mockBuyer.name,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                value = BigDecimal.ONE,
                currency = "GBP",
                attachmentId = null,
                tryCount = 0
        )

        val issuanceFuture = issuanceInitiator
                .services
                .startFlow(TradeAssetIssuance.InitiatorFlow(issuanceMessage))
                .resultFuture

        network.runNetwork()

        issuanceFuture.getOrThrow()

        val amendmentMessage = TradeAssetAmendmentRequestMessage(
                correlationId = "TEST_CORRELATION_ID",
                externalId = "TEST_EXTERNAL_ID",
                value = BigDecimal.TEN,
                currency = "USD",
                tryCount = 0
        )

        val amendmentFuture = amendmentInitiator
                .services
                .startFlow(TradeAssetAmendment.InitiatorFlow(amendmentMessage))
                .resultFuture

        network.runNetwork()

        return amendmentFuture.getOrThrow()
    }
}