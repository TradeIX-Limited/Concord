package com.tradeix.concord.flows

import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage
import groovy.util.GroovyTestCase.assertEquals
import net.corda.core.crypto.SecureHash
import net.corda.node.internal.StartedNode
import net.corda.testing.node.MockNetwork
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*
import net.corda.testing.*
import net.corda.core.identity.Party
import net.corda.core.transactions.SignedTransaction
import net.corda.core.utilities.getOrThrow
import java.io.File
import java.math.BigDecimal
import kotlin.test.assertFailsWith
import kotlin.test.fail

class TradeAssetIssuanceFlowTests {
    val VALID_ATTACHMENT_PATH = "src/test/resources/good.jar"

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

        nodes.partyNodes.forEach { it.registerInitiatedFlow(TradeAssetIssuance.AcceptorFlow::class.java) }

        network.runNetwork()
    }

    @After
    fun tearDown() {
        unsetCordappPackages()
        network.stopNodes()
    }

    @Test
    fun `Absence of supplier in message should result in error`() {
        val message = TradeAssetIssuanceRequestMessage(
                correlationId = "TEST_CORRELATION_ID",
                externalId = "TEST_EXTERNAL_ID",
                status = "INVOICE",
                buyer = mockBuyer.name,
                supplier = null,
                value = BigDecimal.ONE,
                currency = "GBP",
                attachmentId = null,
                tryCount = 0
        )

        val exception = assertFailsWith<FlowValidationException>("Request validation failed") {
            val future = mockBuyerNode
                    .services
                    .startFlow(TradeAssetIssuance.InitiatorFlow(message))
                    .resultFuture

            network.runNetwork()

            future.getOrThrow()
        }

        assert(exception.validationErrors.size == 1)
        assert(exception.validationErrors.contains("Supplier is required for an issuance transaction."))
    }

    @Test
    fun `Absence of Correlation ID in message should result in error`() {
        val message = TradeAssetIssuanceRequestMessage(
                correlationId = null,
                externalId = "TEST_EXTERNAL_ID",
                status = "INVOICE",
                buyer = mockBuyer.name,
                supplier = mockSupplier.name,
                value = BigDecimal.ONE,
                currency = "GBP",
                attachmentId = null,
                tryCount = 0
        )

        val exception = assertFailsWith<FlowValidationException>("Request validation failed") {
            val future = mockBuyerNode
                    .services
                    .startFlow(TradeAssetIssuance.InitiatorFlow(message))
                    .resultFuture

            network.runNetwork()

            future.getOrThrow()
        }

        assert(exception.validationErrors.size == 1)
        assert(exception.validationErrors.contains("Correlation ID is required for an issuance transaction."))
    }

    @Test
    fun `Absence of status in message should result in error`() {
        val message = TradeAssetIssuanceRequestMessage(
                correlationId = "TEST_CORRELATION_ID",
                externalId = "TEST_EXTERNAL_ID",
                status = null,
                buyer = mockBuyer.name,
                supplier = mockSupplier.name,
                value = BigDecimal.ONE,
                currency = "GBP",
                attachmentId = null,
                tryCount = 0
        )

        val exception = assertFailsWith<FlowValidationException>("Request validation failed") {
            val future = mockBuyerNode
                    .services
                    .startFlow(TradeAssetIssuance.InitiatorFlow(message))
                    .resultFuture

            network.runNetwork()

            future.getOrThrow()
        }

        assert(exception.validationErrors.size == 1)
        assert(exception.validationErrors.contains("Status is required for an issuance transaction."))
    }

    @Test
    fun `Invalid status message should result in error`() {
        val flow = TradeAssetIssuance.InitiatorFlow(TradeAssetIssuanceRequestMessage(
                correlationId = "TEST_CORRELATION_ID",
                externalId = "TEST_EXTERNAL_ID",
                status = "THIS_IS_NOT_A_VALID_STATUS",
                buyer = null,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                value = BigDecimal.ONE,
                currency = "GBP",
                attachmentId = null,
                tryCount = 0
        ))

        val future = mockBuyerNode
                .services
                .startFlow(flow)
                .resultFuture

        network.runNetwork()

        assertFailsWith<FlowValidationException>("Request validation failed") {
            future.getOrThrow()
        }

    }

    @Test
    fun `Absence of currency in message should result in error`() {
        val message = TradeAssetIssuanceRequestMessage(
                correlationId = "TEST_CORRELATION_ID",
                externalId = "TEST_EXTERNAL_ID",
                status = "INVOICE",
                buyer = mockBuyer.name,
                supplier = mockSupplier.name,
                value = BigDecimal.ONE,
                currency = null,
                attachmentId = null,
                tryCount = 0
        )

        val exception = assertFailsWith<FlowValidationException>("Request validation failed") {
            val future = mockBuyerNode
                    .services
                    .startFlow(TradeAssetIssuance.InitiatorFlow(message))
                    .resultFuture

            network.runNetwork()

            future.getOrThrow()
        }

        assert(exception.validationErrors.size == 1)
        assert(exception.validationErrors.contains("Currency is required for an issuance transaction."))
    }

    @Test
    fun `Absence of value in message should result in error`() {
        val message = TradeAssetIssuanceRequestMessage(
                correlationId = "TEST_CORRELATION_ID",
                externalId = "TEST_EXTERNAL_ID",
                status = "INVOICE",
                buyer = mockBuyer.name,
                supplier = mockSupplier.name,
                value = null,
                currency = "GBP",
                attachmentId = null,
                tryCount = 0
        )

        val exception = assertFailsWith<FlowValidationException>("Request validation failed") {
            val future = mockBuyerNode
                    .services
                    .startFlow(TradeAssetIssuance.InitiatorFlow(message))
                    .resultFuture

            network.runNetwork()

            future.getOrThrow()
        }

        assert(exception.validationErrors.size == 1)
        assert(exception.validationErrors.contains("Value is required for an issuance transaction."))
    }

    @Test
    fun `Negative value in message should result in error`() {
        val message = TradeAssetIssuanceRequestMessage(
                correlationId = "TEST_CORRELATION_ID",
                externalId = "TEST_EXTERNAL_ID",
                status = "INVOICE",
                buyer = mockBuyer.name,
                supplier = mockSupplier.name,
                value = BigDecimal.ONE.negate(),
                currency = "GBP",
                attachmentId = null,
                tryCount = 0
        )

        val exception = assertFailsWith<FlowValidationException>("Request validation failed") {
            val future = mockBuyerNode
                    .services
                    .startFlow(TradeAssetIssuance.InitiatorFlow(message))
                    .resultFuture

            network.runNetwork()

            future.getOrThrow()
        }

        assert(exception.validationErrors.size == 1)
        assert(exception.validationErrors.contains("Value cannot be negative for an issuance transaction."))
    }

    @Test
    fun `Buyer initiated SignedTransaction returned by the flow is signed by the initiator`() {
        val flow = TradeAssetIssuance.InitiatorFlow(TradeAssetIssuanceRequestMessage(
                correlationId = "TEST_CORRELATION_ID",
                externalId = "TEST_EXTERNAL_ID",
                status = "INVOICE",
                buyer = null,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                value = BigDecimal.ONE,
                currency = "GBP",
                attachmentId = null,
                tryCount = 0
        ))

        val future = mockBuyerNode
                .services
                .startFlow(flow)
                .resultFuture

        network.runNetwork()

        future.getOrThrow().verifySignaturesExcept(
                mockConductor.owningKey,
                mockSupplier.owningKey)
    }

    @Test
    fun `Buyer initiated SignedTransaction returned by the flow is signed by the acceptor`() {
        val flow = TradeAssetIssuance.InitiatorFlow(TradeAssetIssuanceRequestMessage(
                correlationId = "TEST_CORRELATION_ID",
                externalId = "TEST_EXTERNAL_ID",
                status = "INVOICE",
                buyer = null,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                value = BigDecimal.ONE,
                currency = "GBP",
                attachmentId = null,
                tryCount = 0
        ))

        val future = mockBuyerNode
                .services
                .startFlow(flow)
                .resultFuture

        network.runNetwork()

        val signedTransaction = future.getOrThrow()

        signedTransaction.verifySignaturesExcept(
                mockBuyer.owningKey)
    }

    @Test
    fun `Conductor initiated SignedTransaction returned by the flow is signed by the initiator`() {
        val flow = TradeAssetIssuance.InitiatorFlow(TradeAssetIssuanceRequestMessage(
                correlationId = "TEST_CORRELATION_ID",
                externalId = "TEST_EXTERNAL_ID",
                status = "INVOICE",
                buyer = null,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                value = BigDecimal.ONE,
                currency = "GBP",
                attachmentId = null,
                tryCount = 0
        ))

        val future = mockConductorNode
                .services
                .startFlow(flow)
                .resultFuture

        network.runNetwork()

        val signedTransaction = future.getOrThrow()

        signedTransaction.verifySignaturesExcept(
                mockBuyer.owningKey,
                mockSupplier.owningKey)
    }

    @Test
    fun `Conductor initiated SignedTransaction returned by the flow is signed by the acceptor`() {
        val flow = TradeAssetIssuance.InitiatorFlow(TradeAssetIssuanceRequestMessage(
                correlationId = "TEST_CORRELATION_ID",
                externalId = "TEST_EXTERNAL_ID",
                status = "INVOICE",
                buyer = null,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                value = BigDecimal.ONE,
                currency = "GBP",
                attachmentId = null,
                tryCount = 0
        ))

        val future = mockConductorNode
                .services
                .startFlow(flow)
                .resultFuture

        network.runNetwork()

        val signedTransaction = future.getOrThrow()

        signedTransaction.verifySignaturesExcept(
                mockConductor.owningKey)
    }

    @Test
    fun `Flow records a transaction in all counter-party vaults`() {
        val flow = TradeAssetIssuance.InitiatorFlow(TradeAssetIssuanceRequestMessage(
                correlationId = "TEST_CORRELATION_ID",
                externalId = "TEST_EXTERNAL_ID",
                status = "INVOICE",
                buyer = null,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                value = BigDecimal.ONE,
                currency = "GBP",
                attachmentId = null,
                tryCount = 0
        ))

        val future = mockBuyerNode
                .services
                .startFlow(flow)
                .resultFuture

        network.runNetwork()

        val signedTransaction = future.getOrThrow()

        for (node in listOf(mockBuyerNode, mockSupplierNode, mockConductorNode)) {
            assertEquals(signedTransaction, node.services.validatedTransactions.getTransaction(signedTransaction.id))
        }
    }

    @Test
    fun `Recorded transaction has no inputs and a single output`() {
        val signedTransaction = getFutureForIssuanceFlow(null, mockBuyerNode)
        for (node in listOf(mockBuyerNode, mockSupplierNode, mockConductorNode)) {
            val recordedTx = node.services.validatedTransactions.getTransaction(signedTransaction.id) ?: fail()
            assert(recordedTx.inputs.isEmpty())
            assert(recordedTx.tx.outputs.size == 1)
        }
    }

    @Test
    fun `An Invalid value for document Hash would return an exception`() {
        assertFailsWith<FlowValidationException>("Request validation failed") {
            getFutureForIssuanceFlow("abcd", mockBuyerNode)
        }
    }

    @Test
    fun `A valid document should be accepted`() {
        val attachmentInputStream = File(VALID_ATTACHMENT_PATH).inputStream()

        val validAttachment: SecureHash = mockBuyerNode.database.transaction {
            mockBuyerNode.attachments.importAttachment(attachmentInputStream)
        }

        val signedTransaction = getFutureForIssuanceFlow(validAttachment.toString(), mockBuyerNode)

        for (node in listOf(mockBuyerNode, mockSupplierNode, mockConductorNode)) {
            val recordedTx = node.services.validatedTransactions.getTransaction(signedTransaction.id) ?: fail()
            assert(recordedTx.inputs.isEmpty())
            assert(recordedTx.tx.outputs.size == 1)
        }
    }

    private fun getFutureForIssuanceFlow(
            validAttachment: String?,
            initiatorNode: StartedNode<MockNetwork.MockNode>): SignedTransaction {
        val flow = TradeAssetIssuance.InitiatorFlow(TradeAssetIssuanceRequestMessage(
                correlationId = "TEST_CORRELATION_ID",
                externalId = "TEST_EXTERNAL_ID",
                status = "INVOICE",
                buyer = null,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                value = BigDecimal.ONE,
                currency = "GBP",
                attachmentId = validAttachment,
                tryCount = 0
        ))
        val future = initiatorNode
                .services
                .startFlow(flow)
                .resultFuture
        network.runNetwork()
        return future.getOrThrow()
    }
}
