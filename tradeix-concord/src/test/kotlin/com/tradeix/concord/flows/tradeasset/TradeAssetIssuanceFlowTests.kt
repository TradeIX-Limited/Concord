package com.tradeix.concord.flows.tradeasset

import com.tradeix.concord.TestValueHelper.ATTACHMENT
import com.tradeix.concord.TestValueHelper.EXTERNAL_ID
import com.tradeix.concord.TestValueHelper.NEGATIVE_ONE
import com.tradeix.concord.TestValueHelper.NOT_A_VALID_HASH
import com.tradeix.concord.TestValueHelper.POSITIVE_ONE
import com.tradeix.concord.TestValueHelper.POUNDS
import com.tradeix.concord.TestValueHelper.STATUS_INVOICE
import com.tradeix.concord.TestValueHelper.NOT_A_VALID_STATUS
import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.flowmodels.tradeasset.TradeAssetIssuanceFlowModel
import com.tradeix.concord.flows.AbstractFlowTest
import net.corda.node.internal.StartedNode
import net.corda.testing.node.MockNetwork
import org.junit.Test
import com.tradeix.concord.flows.FlowTestHelper.issueTradeAsset
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.fail

class TradeAssetIssuanceFlowTests : AbstractFlowTest() {
    override fun configureNode(node: StartedNode<MockNetwork.MockNode>) {
        node.registerInitiatedFlow(TradeAssetIssuance.AcceptorFlow::class.java)
    }

    @Test
    fun `Issuance flow will fail if externalId is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueTradeAsset(network, conductor.node, TradeAssetIssuanceFlowModel(
                    externalId = null,
                    buyer = buyer.name,
                    supplier = supplier.name,
                    conductor = conductor.name,
                    status = STATUS_INVOICE,
                    value = POSITIVE_ONE,
                    currency = POUNDS
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'externalId' is required.", exception.validationErrors.single())
    }

    @Test
    fun `Issuance flow will fail if status is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueTradeAsset(network, conductor.node, TradeAssetIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    buyer = buyer.name,
                    supplier = supplier.name,
                    conductor = conductor.name,
                    status = null,
                    value = POSITIVE_ONE,
                    currency = POUNDS
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'status' is required.", exception.validationErrors.single())
    }

    @Test
    fun `Issuance flow will fail if status is invalid`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueTradeAsset(network, conductor.node, TradeAssetIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    buyer = buyer.name,
                    supplier = supplier.name,
                    conductor = conductor.name,
                    status = NOT_A_VALID_STATUS,
                    value = POSITIVE_ONE,
                    currency = POUNDS
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Invalid status value.", exception.validationErrors.single())
    }

    @Test
    fun `Issuance flow will fail if supplier is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueTradeAsset(network, conductor.node, TradeAssetIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    buyer = buyer.name,
                    supplier = null,
                    conductor = conductor.name,
                    status = STATUS_INVOICE,
                    value = POSITIVE_ONE,
                    currency = POUNDS
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'supplier' is required.", exception.validationErrors.single())
    }

    @Test
    fun `Issuance flow will fail if currency is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueTradeAsset(network, conductor.node, TradeAssetIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    buyer = buyer.name,
                    supplier = supplier.name,
                    conductor = conductor.name,
                    status = STATUS_INVOICE,
                    value = POSITIVE_ONE,
                    currency = null
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'currency' is required.", exception.validationErrors.single())
    }

    @Test
    fun `Issuance flow will fail if value is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueTradeAsset(network, conductor.node, TradeAssetIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    buyer = buyer.name,
                    supplier = supplier.name,
                    conductor = conductor.name,
                    status = STATUS_INVOICE,
                    value = null,
                    currency = POUNDS
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'value' is required.", exception.validationErrors.single())
    }

    @Test
    fun `Issuance flow will fail if value is negative`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueTradeAsset(network, conductor.node, TradeAssetIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    buyer = buyer.name,
                    supplier = supplier.name,
                    conductor = conductor.name,
                    status = STATUS_INVOICE,
                    value = NEGATIVE_ONE,
                    currency = POUNDS
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'value' cannot be negative.", exception.validationErrors.single())
    }

    @Test
    fun `Issuance flow will fail if attachmentId is invalid`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueTradeAsset(network, conductor.node, TradeAssetIssuanceFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = NOT_A_VALID_HASH,
                    buyer = buyer.name,
                    supplier = supplier.name,
                    conductor = conductor.name,
                    status = STATUS_INVOICE,
                    value = POSITIVE_ONE,
                    currency = POUNDS
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Invalid Secure hash for attachment.", exception.validationErrors.single())
    }

    @Test
    fun `Issuance flow initiated by the buyer is signed by the initiator`() {
        val transaction = issueTradeAsset(network, buyer.node, TradeAssetIssuanceFlowModel(
                externalId = EXTERNAL_ID,
                supplier = supplier.name,
                conductor = conductor.name,
                status = STATUS_INVOICE,
                value = POSITIVE_ONE,
                currency = POUNDS
        ))

        transaction.verifySignaturesExcept(supplier.publicKey, conductor.publicKey)
    }

    @Test
    fun `Issuance flow initiated by the buyer is signed by the acceptor`() {
        val transaction = issueTradeAsset(network, buyer.node, TradeAssetIssuanceFlowModel(
                externalId = EXTERNAL_ID,
                supplier = supplier.name,
                conductor = conductor.name,
                status = STATUS_INVOICE,
                value = POSITIVE_ONE,
                currency = POUNDS
        ))

        transaction.verifySignaturesExcept(buyer.publicKey)
    }

    @Test
    fun `Issuance flow initiated by the conductor is signed by the initiator`() {
        val transaction = issueTradeAsset(network, conductor.node, TradeAssetIssuanceFlowModel(
                externalId = EXTERNAL_ID,
                buyer = buyer.name,
                supplier = supplier.name,
                conductor = conductor.name,
                status = STATUS_INVOICE,
                value = POSITIVE_ONE,
                currency = POUNDS
        ))

        transaction.verifySignaturesExcept(supplier.publicKey, conductor.publicKey)
    }

    @Test
    fun `Issuance flow initiated by the conductor is signed by the acceptor`() {
        val transaction = issueTradeAsset(network, conductor.node, TradeAssetIssuanceFlowModel(
                externalId = EXTERNAL_ID,
                buyer = buyer.name,
                supplier = supplier.name,
                conductor = conductor.name,
                status = STATUS_INVOICE,
                value = POSITIVE_ONE,
                currency = POUNDS
        ))

        transaction.verifySignaturesExcept(conductor.publicKey)
    }

    @Test
    fun `Issuance flow records a transaction in all counter-party vaults`() {
        val transaction = issueTradeAsset(network, conductor.node, TradeAssetIssuanceFlowModel(
                externalId = EXTERNAL_ID,
                buyer = buyer.name,
                supplier = supplier.name,
                conductor = conductor.name,
                status = STATUS_INVOICE,
                value = POSITIVE_ONE,
                currency = POUNDS
        ))

        listOf(buyer.node, supplier.node, conductor.node).forEach {
            assertEquals(transaction, it.services.validatedTransactions.getTransaction(transaction.id))
        }
    }

    @Test
    fun `Issuance flow transaction has zero inputs and a single output`() {
        val transaction = issueTradeAsset(network, conductor.node, TradeAssetIssuanceFlowModel(
                externalId = EXTERNAL_ID,
                buyer = buyer.name,
                supplier = supplier.name,
                conductor = conductor.name,
                status = STATUS_INVOICE,
                value = POSITIVE_ONE,
                currency = POUNDS
        ))

        listOf(buyer.node, supplier.node, conductor.node).forEach {
            val recordedTransaction = it.services.validatedTransactions.getTransaction(transaction.id) ?: fail()
            assertEquals(0, recordedTransaction.tx.inputs.size)
            assertEquals(1, recordedTransaction.tx.outputs.size)
        }
    }

    @Test
    fun `Issuance flow with a valid attachmentId will store the attachment`() {
        val validAttachment = conductor.node.database.transaction {
            conductor.node.attachments.importAttachment(File(ATTACHMENT).inputStream())
        }

        val transaction = issueTradeAsset(network, conductor.node, TradeAssetIssuanceFlowModel(
                externalId = EXTERNAL_ID,
                attachmentId = validAttachment.toString(),
                buyer = buyer.name,
                supplier = supplier.name,
                conductor = conductor.name,
                status = STATUS_INVOICE,
                value = POSITIVE_ONE,
                currency = POUNDS
        ))

        listOf(buyer.node, supplier.node, conductor.node).forEach {
            assertEquals(transaction, it.services.validatedTransactions.getTransaction(transaction.id))
            // TODO : Can we actually check that the attachment exists?
        }
    }
}