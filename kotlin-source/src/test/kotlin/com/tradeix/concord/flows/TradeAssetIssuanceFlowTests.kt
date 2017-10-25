package com.tradeix.concord.flows

import com.tradeix.concord.exceptions.ValidationException
import com.tradeix.concord.messages.TradeAssetIssuanceRequestMessage
import groovy.util.GroovyTestCase.assertEquals
import net.corda.core.concurrent.CordaFuture
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
import kotlin.test.assertNotNull
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
                status = "Invoice",
                buyer = mockBuyer.name,
                supplier = null,
                assetId = "MOCK_ASSET",
                value = BigDecimal.ONE,
                currency = "GBP",
                supportingDocumentHash = null
        )

        assertFailsWith<ValidationException>("Request validation failed") {
            val future = mockBuyerNode
                    .services
                    .startFlow(TradeAssetIssuance.InitiatorFlow(message))
                    .resultFuture

            network.runNetwork()

            future.getOrThrow()
        }

        assert(!message.isValid)
        assert(message.getValidationErrors().size == 1)
        assert(message.getValidationErrors().contains("Supplier is required for an issuance transaction."))
    }

    @Test
    fun `Absence of asset ID in message should result in error`() {
        val message = TradeAssetIssuanceRequestMessage(
                status = "Invoice",
                buyer = mockBuyer.name,
                supplier = mockSupplier.name,
                assetId = null,
                value = BigDecimal.ONE,
                currency = "GBP",
                supportingDocumentHash = null
        )

        assertFailsWith<ValidationException>("Request validation failed") {
            val future = mockBuyerNode
                    .services
                    .startFlow(TradeAssetIssuance.InitiatorFlow(message))
                    .resultFuture

            network.runNetwork()

            future.getOrThrow()
        }

        assert(!message.isValid)
        assert(message.getValidationErrors().size == 1)
        assert(message.getValidationErrors().contains("Asset ID is required for an issuance transaction."))
    }

    @Test
    fun `Absence of currency in message should result in error`() {
        val message = TradeAssetIssuanceRequestMessage(
                status = "Invoice",
                buyer = mockBuyer.name,
                supplier = mockSupplier.name,
                assetId = "MOCK_ASSET",
                value = BigDecimal.ONE,
                currency = null,
                supportingDocumentHash = null
        )

        assertFailsWith<ValidationException>("Request validation failed") {
            val future = mockBuyerNode
                    .services
                    .startFlow(TradeAssetIssuance.InitiatorFlow(message))
                    .resultFuture

            network.runNetwork()

            future.getOrThrow()
        }

        assert(!message.isValid)
        assert(message.getValidationErrors().size == 1)
        assert(message.getValidationErrors().contains("Currency is required for an issuance transaction."))
    }

    @Test
    fun `Absence of value in message should result in error`() {
        val message = TradeAssetIssuanceRequestMessage(
                status = "Invoice",
                buyer = mockBuyer.name,
                supplier = mockSupplier.name,
                assetId = "MOCK_ASSET",
                value = null,
                currency = "GBP",
                supportingDocumentHash = null
        )

        assertFailsWith<ValidationException>("Request validation failed") {
            val future = mockBuyerNode
                    .services
                    .startFlow(TradeAssetIssuance.InitiatorFlow(message))
                    .resultFuture

            network.runNetwork()

            future.getOrThrow()
        }

        assert(!message.isValid)
        assert(message.getValidationErrors().size == 1)
        assert(message.getValidationErrors().contains("Value is required for an issuance transaction."))
    }

    @Test
    fun `Negative value in message should result in error`() {
        val message = TradeAssetIssuanceRequestMessage(
                status = "Invoice",
                buyer = mockBuyer.name,
                supplier = mockSupplier.name,
                assetId = "MOCK_ASSET",
                value = BigDecimal.ONE.negate(),
                currency = "GBP",
                supportingDocumentHash = null
        )

        assertFailsWith<ValidationException>("Request validation failed") {
            val future = mockBuyerNode
                    .services
                    .startFlow(TradeAssetIssuance.InitiatorFlow(message))
                    .resultFuture

            network.runNetwork()

            future.getOrThrow()
        }

        assert(!message.isValid)
        assert(message.getValidationErrors().size == 1)
        assert(message.getValidationErrors().contains("Value cannot be negative for an issuance transaction."))
    }

    @Test
    fun `Buyer initiated SignedTransaction returned by the flow is signed by the initiator`() {
        val flow = TradeAssetIssuance.InitiatorFlow(TradeAssetIssuanceRequestMessage(
                linearId = UUID.fromString("00000000-0000-4000-0000-000000000000"),
                status = "Invoice",
                buyer = null,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                assetId = "MOCK_ASSET",
                value = BigDecimal.ONE,
                currency = "GBP",
                supportingDocumentHash = null
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
                linearId = UUID.fromString("00000000-0000-4000-0000-000000000000"),
                status = "Invoice",
                buyer = null,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                assetId = "MOCK_ASSET",
                value = BigDecimal.ONE,
                currency = "GBP",
                supportingDocumentHash = null
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
                linearId = UUID.fromString("00000000-0000-4000-0000-000000000000"),
                status = "Invoice",
                buyer = mockBuyer.name,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                assetId = "MOCK_ASSET",
                value = BigDecimal.ONE,
                currency = "GBP",
                supportingDocumentHash = null
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
                linearId = UUID.fromString("00000000-0000-4000-0000-000000000000"),
                status = "Invoice",
                buyer = mockBuyer.name,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                assetId = "MOCK_ASSET",
                value = BigDecimal.ONE,
                currency = "GBP",
                supportingDocumentHash = null
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
                linearId = UUID.fromString("00000000-0000-4000-0000-000000000000"),
                status = "Invoice",
                buyer = null,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                assetId = "MOCK_ASSET",
                value = BigDecimal.ONE,
                currency = "GBP",
                supportingDocumentHash = null
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
        val future = getFutureForIssuanceFlow(null,mockBuyerNode )
        val signedTransaction = future.getOrThrow()
        for (node in listOf(mockBuyerNode, mockSupplierNode, mockConductorNode)) {
            val recordedTx = node.services.validatedTransactions.getTransaction(signedTransaction.id) ?: fail()
            assert(recordedTx.inputs.isEmpty())
            assert(recordedTx.tx.outputs.size == 1)
        }
    }

    @Test
    fun `An Invalid value for document Hash would return an exception`() {
        val future = getFutureForIssuanceFlow("abcd",mockBuyerNode )
        assertFailsWith<ValidationException>("Request validation failed") {
            future.getOrThrow()
        }
    }

    @Test
    fun `A valid document should be accepted`() {
        val attachmentInputStream = File(VALID_ATTACHMENT_PATH).inputStream()
        val validAttachment: SecureHash = mockBuyerNode.database.transaction {
           mockBuyerNode.attachments.importAttachment(attachmentInputStream)
        }
        val future = getFutureForIssuanceFlow(validAttachment.toString(),mockBuyerNode )
        val signedTransaction = future.getOrThrow()
        for (node in listOf(mockBuyerNode, mockSupplierNode, mockConductorNode)) {
            val recordedTx = node.services.validatedTransactions.getTransaction(signedTransaction.id) ?: fail()
            assert(recordedTx.inputs.isEmpty())
            assert(recordedTx.tx.outputs.size == 1)
        }
    }

    private fun getFutureForIssuanceFlow(validAttachment: String?, initiatorNode : StartedNode<MockNetwork.MockNode>): CordaFuture<SignedTransaction> {
        val flow = TradeAssetIssuance.InitiatorFlow(TradeAssetIssuanceRequestMessage(
                linearId = UUID.fromString("00000000-0000-4000-0000-000000000000"),
                status = "Invoice",
                buyer = null,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                assetId = "MOCK_ASSET",
                value = BigDecimal.ONE,
                currency = "GBP",
                supportingDocumentHash = validAttachment
        ))
        val future = initiatorNode
                .services
                .startFlow(flow)
                .resultFuture
        network.runNetwork()
        return future
    }

        val signedTransaction = future.getOrThrow()

        for (node in listOf(mockBuyerNode, mockSupplierNode, mockConductorNode)) {
            val recordedTx = node.services.validatedTransactions.getTransaction(signedTransaction.id) ?: fail()
            assert(recordedTx.inputs.isEmpty())
            assert(recordedTx.tx.outputs.size == 1)
        }
    }

    @Test
    fun `an invalid status message should return an error`() {
        val flow = TradeAssetIssuance.InitiatorFlow(TradeAssetIssuanceRequestMessage(
                linearId = UUID.fromString("00000000-0000-4000-0000-000000000000"),
                status = "Blah Blah",
                buyer = null,
                supplier = mockSupplier.name,
                conductor = mockConductor.name,
                assetId = "MOCK_ASSET",
                value = BigDecimal.ONE,
                currency = "GBP"
        ))

        val future = mockBuyerNode
                .services
                .startFlow(flow)
                .resultFuture

        network.runNetwork()

        assertFailsWith<ValidationException>("Request validation failed") {
            future.getOrThrow()
        }

    }
}
