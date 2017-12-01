package com.tradeix.concord.flows.tradeassets

import com.tradeix.concord.TestValueHelper.DOLLARS
import com.tradeix.concord.TestValueHelper.EXTERNAL_ID
import com.tradeix.concord.TestValueHelper.POSITIVE_ONE
import com.tradeix.concord.TestValueHelper.POSITIVE_TEN
import com.tradeix.concord.TestValueHelper.POUNDS
import com.tradeix.concord.TestValueHelper.STATUS_INVOICE
import com.tradeix.concord.TestValueHelper.STATUS_PURCHASE_ORDER
import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.flowmodels.TradeAssetAmendmentFlowModel
import com.tradeix.concord.flowmodels.TradeAssetIssuanceFlowModel
import com.tradeix.concord.flows.AbstractFlowTest
import com.tradeix.concord.flows.FlowTestHelper
import com.tradeix.concord.flows.FlowTestHelper.amendTradeAsset
import com.tradeix.concord.flows.TradeAssetAmendment
import com.tradeix.concord.flows.TradeAssetIssuance
import net.corda.core.flows.FlowException
import net.corda.core.transactions.SignedTransaction
import net.corda.node.internal.StartedNode
import net.corda.testing.node.MockNetwork
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TradeAssetAmendmentFlowTests : AbstractFlowTest() {
    override fun configureNode(node: StartedNode<MockNetwork.MockNode>) {
        node.registerInitiatedFlow(TradeAssetIssuance.AcceptorFlow::class.java)
        node.registerInitiatedFlow(TradeAssetAmendment.AcceptorFlow::class.java)
    }

    @Test
    fun `Amendment flow will fail if externalId is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueTradeAsset(STATUS_INVOICE)
            amendTradeAsset(network, conductor.node, TradeAssetAmendmentFlowModel(
                    externalId = null,
                    value = POSITIVE_TEN,
                    currency = DOLLARS
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'externalId' is required.", exception.validationErrors.single())
    }

    @Test
    fun `Amendment flow for INVOICE status cannot be amended by a buyer`() {
        issueTradeAsset(STATUS_INVOICE)
        val exception = assertFailsWith<FlowException> {
            amendTradeAsset(network, buyer.node, TradeAssetAmendmentFlowModel(
                    externalId = EXTERNAL_ID,
                    value = POSITIVE_TEN,
                    currency = DOLLARS
            ))
        }

        assertEquals(
                "Flow verification failed: Trade asset of status PURCHASE_ORDER can only be amended by the buyer.",
                exception.message
        )
    }

    @Test
    fun `Amendment flow for PURCHASE_ORDER status cannot be amended by a supplier`() {
        issueTradeAsset(STATUS_PURCHASE_ORDER)
        val exception = assertFailsWith<FlowException> {
            amendTradeAsset(network, supplier.node, TradeAssetAmendmentFlowModel(
                    externalId = EXTERNAL_ID,
                    value = POSITIVE_TEN,
                    currency = DOLLARS
            ))
        }

        assertEquals(
                "Flow verification failed: Trade asset of status INVOICE can only be amended by the supplier.",
                exception.message
        )
    }

    @Test
    fun `Amendment flow for INVOICE status can be amended by a supplier`() {
        issueTradeAsset(STATUS_INVOICE)
        amendTradeAsset(network, supplier.node, TradeAssetAmendmentFlowModel(
                externalId = EXTERNAL_ID,
                value = POSITIVE_TEN,
                currency = DOLLARS
        ))
    }

    @Test
    fun `Amendment flow for PURCHASE_ORDER status can be amended by a buyer`() {
        issueTradeAsset(STATUS_PURCHASE_ORDER)
        amendTradeAsset(network, buyer.node, TradeAssetAmendmentFlowModel(
                externalId = EXTERNAL_ID,
                value = POSITIVE_TEN,
                currency = DOLLARS
        ))
    }

    @Test
    fun `Amendment flow initiated by the buyer is signed by the initiator`() {
        issueTradeAsset(STATUS_PURCHASE_ORDER)
        val transaction = amendTradeAsset(network, buyer.node, TradeAssetAmendmentFlowModel(
                externalId = EXTERNAL_ID,
                value = POSITIVE_TEN,
                currency = DOLLARS
        ))

        transaction.verifySignaturesExcept(supplier.publicKey, conductor.publicKey)
    }

    @Test
    fun `Amendment flow initiated by the supplier is signed by the initiator`() {
        issueTradeAsset(STATUS_INVOICE)
        val transaction = amendTradeAsset(network, supplier.node, TradeAssetAmendmentFlowModel(
                externalId = EXTERNAL_ID,
                value = POSITIVE_TEN,
                currency = DOLLARS
        ))

        transaction.verifySignaturesExcept(buyer.publicKey, conductor.publicKey)
    }

    @Test
    fun `Amendment flow initiated by the conductor is signed by the initiator`() {
        issueTradeAsset(STATUS_INVOICE)
        val transaction = amendTradeAsset(network, conductor.node, TradeAssetAmendmentFlowModel(
                externalId = EXTERNAL_ID,
                value = POSITIVE_TEN,
                currency = DOLLARS
        ))

        transaction.verifySignaturesExcept(buyer.publicKey, supplier.publicKey)
    }

    @Test
    fun `Amendment flow initiated by the buyer is signed by the acceptor`() {
        issueTradeAsset(STATUS_PURCHASE_ORDER)
        val transaction = amendTradeAsset(network, buyer.node, TradeAssetAmendmentFlowModel(
                externalId = EXTERNAL_ID,
                value = POSITIVE_TEN,
                currency = DOLLARS
        ))

        transaction.verifySignaturesExcept(buyer.publicKey)
    }

    @Test
    fun `Amendment flow initiated by the supplier is signed by the acceptor`() {
        issueTradeAsset(STATUS_INVOICE)
        val transaction = amendTradeAsset(network, supplier.node, TradeAssetAmendmentFlowModel(
                externalId = EXTERNAL_ID,
                value = POSITIVE_TEN,
                currency = DOLLARS
        ))

        transaction.verifySignaturesExcept(supplier.publicKey)
    }

    @Test
    fun `Amendment flow initiated by the conductor is signed by the acceptor`() {
        issueTradeAsset(STATUS_INVOICE)
        val transaction = amendTradeAsset(network, conductor.node, TradeAssetAmendmentFlowModel(
                externalId = EXTERNAL_ID,
                value = POSITIVE_TEN,
                currency = DOLLARS
        ))

        transaction.verifySignaturesExcept(conductor.publicKey)
    }

    @Test
    fun `Amendment flow records a transaction in all counter-party vaults`() {
        issueTradeAsset(STATUS_INVOICE)
        val transaction = amendTradeAsset(network, conductor.node, TradeAssetAmendmentFlowModel(
                externalId = EXTERNAL_ID,
                value = POSITIVE_TEN,
                currency = DOLLARS
        ))

        listOf(buyer.node, supplier.node, conductor.node).forEach {
            assertEquals(transaction, it.services.validatedTransactions.getTransaction(transaction.id))
        }
    }

    @Test
    fun `Amendment flow transaction has a single input and a single output`() {
        issueTradeAsset(STATUS_INVOICE)
        val transaction = amendTradeAsset(network, conductor.node, TradeAssetAmendmentFlowModel(
                externalId = EXTERNAL_ID,
                value = POSITIVE_TEN,
                currency = DOLLARS
        ))

        listOf(buyer.node, supplier.node, conductor.node).forEach {
            assertEquals(transaction, it.services.validatedTransactions.getTransaction(transaction.id))
            assertEquals(1, transaction.inputs.size)
            assertEquals(1, transaction.tx.outputs.size)
        }
    }

    private fun issueTradeAsset(status: String): SignedTransaction {
        return FlowTestHelper.issueTradeAsset(network, conductor.node, TradeAssetIssuanceFlowModel(
                externalId = EXTERNAL_ID,
                buyer = buyer.name,
                supplier = supplier.name,
                conductor = conductor.name,
                status = status,
                value = POSITIVE_ONE,
                currency = POUNDS
        ))
    }
}