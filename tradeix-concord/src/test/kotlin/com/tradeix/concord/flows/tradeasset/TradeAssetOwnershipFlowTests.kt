package com.tradeix.concord.flows.tradeasset

import com.tradeix.concord.TestValueHelper
import com.tradeix.concord.TestValueHelper.EXTERNAL_IDS
import com.tradeix.concord.TestValueHelper.POSITIVE_ONE
import com.tradeix.concord.TestValueHelper.POUNDS
import com.tradeix.concord.TestValueHelper.STATUS_INVOICE
import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.exceptions.FlowVerificationException
import com.tradeix.concord.flowmodels.tradeasset.TradeAssetIssuanceFlowModel
import com.tradeix.concord.flowmodels.tradeasset.TradeAssetOwnershipFlowModel
import com.tradeix.concord.flows.AbstractFlowTest
import com.tradeix.concord.flows.FlowTestHelper
import com.tradeix.concord.flows.FlowTestHelper.changeTradeAssetOwner
import net.corda.core.transactions.SignedTransaction
import net.corda.node.internal.StartedNode
import net.corda.testing.node.MockNetwork
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.fail

class TradeAssetOwnershipFlowTests : AbstractFlowTest() {
    override fun configureNode(node: StartedNode<MockNetwork.MockNode>) {
        node.registerInitiatedFlow(TradeAssetIssuance.AcceptorFlow::class.java)
        node.registerInitiatedFlow(TradeAssetOwnership.AcceptorFlow::class.java)
    }

    @Test
    fun `Ownership flow will fail if externalIds is omitted`() {
        issueTradeAssets(STATUS_INVOICE)
        val exception = assertFailsWith<FlowValidationException> {
            changeTradeAssetOwner(network, conductor.node, TradeAssetOwnershipFlowModel(
                    externalIds = null,
                    newOwner = funder.name
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'externalIds' is required.", exception.validationErrors.single())
    }

    @Test
    fun `Ownership flow will fail if externalIds is empty`() {
        issueTradeAssets(STATUS_INVOICE)
        val exception = assertFailsWith<FlowValidationException> {
            changeTradeAssetOwner(network, conductor.node, TradeAssetOwnershipFlowModel(
                    externalIds = emptyList(),
                    newOwner = funder.name
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'externalIds' must have at least one value.", exception.validationErrors.single())
    }

    @Test
    fun `Ownership flow will fail if newOwner is omitted`() {
        issueTradeAssets(STATUS_INVOICE)
        val exception = assertFailsWith<FlowValidationException> {
            changeTradeAssetOwner(network, conductor.node, TradeAssetOwnershipFlowModel(
                    externalIds = EXTERNAL_IDS,
                    newOwner = null
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'newOwner' is required.", exception.validationErrors.single())
    }

    @Test
    fun `Ownership flow initiated by the buyer fails because they're not the owner`() {
        issueTradeAssets(STATUS_INVOICE)
        assertFailsWith<FlowVerificationException> {
            changeTradeAssetOwner(network, buyer.node, TradeAssetOwnershipFlowModel(
                    externalIds = EXTERNAL_IDS,
                    newOwner = funder.name
            ))
        }
    }

    @Test
    fun `Ownership flow initiated by the conductor is signed by the initiator`() {
        issueTradeAssets(STATUS_INVOICE)
        val transaction = changeTradeAssetOwner(network, conductor.node, TradeAssetOwnershipFlowModel(
                externalIds = EXTERNAL_IDS,
                newOwner = funder.name
        ))

        transaction.verifySignaturesExcept(buyer.publicKey, supplier.publicKey, funder.publicKey)
    }

    @Test
    fun `Ownership flow initiated by the supplier is signed by the initiator`() {
        issueTradeAssets(STATUS_INVOICE)
        val transaction = changeTradeAssetOwner(network, supplier.node, TradeAssetOwnershipFlowModel(
                externalIds = EXTERNAL_IDS,
                newOwner = funder.name
        ))

        transaction.verifySignaturesExcept(buyer.publicKey, conductor.publicKey, funder.publicKey)
    }

    @Test
    fun `Ownership flow initiated by the conductor is signed by the acceptor`() {
        issueTradeAssets(STATUS_INVOICE)
        val transaction = changeTradeAssetOwner(network, conductor.node, TradeAssetOwnershipFlowModel(
                externalIds = EXTERNAL_IDS,
                newOwner = funder.name
        ))

        transaction.verifySignaturesExcept(conductor.publicKey)
    }

    @Test
    fun `Ownership flow initiated by the supplier is signed by the acceptor`() {
        issueTradeAssets(STATUS_INVOICE)
        val transaction = changeTradeAssetOwner(network, supplier.node, TradeAssetOwnershipFlowModel(
                externalIds = EXTERNAL_IDS,
                newOwner = funder.name
        ))

        transaction.verifySignaturesExcept(supplier.publicKey)
    }

    @Test
    fun `Ownership flow records a transaction in all counter-party vaults`() {
        issueTradeAssets(STATUS_INVOICE)
        val transaction = changeTradeAssetOwner(network, supplier.node, TradeAssetOwnershipFlowModel(
                externalIds = EXTERNAL_IDS,
                newOwner = funder.name
        ))

        listOf(buyer.node, supplier.node, funder.node, conductor.node).forEach {
            assertEquals(transaction, it.services.validatedTransactions.getTransaction(transaction.id))
        }
    }

    @Test
    fun `Ownership flow transaction has an equal number of inputs to outputs`() {
        issueTradeAssets(STATUS_INVOICE)
        val transaction = changeTradeAssetOwner(network, supplier.node, TradeAssetOwnershipFlowModel(
                externalIds = EXTERNAL_IDS,
                newOwner = funder.name
        ))

        listOf(buyer.node, supplier.node, funder.node, conductor.node).forEach {
            val recordedTransaction = it.services.validatedTransactions.getTransaction(transaction.id) ?: fail()
            assertEquals(recordedTransaction.inputs.size, recordedTransaction.tx.outputs.size)
        }
    }

    private fun issueTradeAssets(status: String): List<SignedTransaction> {
        return EXTERNAL_IDS.map {
            FlowTestHelper.issueTradeAsset(network, conductor.node, TradeAssetIssuanceFlowModel(
                    externalId = it,
                    buyer = buyer.name,
                    supplier = supplier.name,
                    conductor = conductor.name,
                    status = status,
                    value = POSITIVE_ONE,
                    currency = POUNDS
            ))
        }
    }
}