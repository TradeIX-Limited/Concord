package com.tradeix.concord.flows.tradeassets

import com.tradeix.concord.TestValueHelper
import com.tradeix.concord.TestValueHelper.EXTERNAL_ID
import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.flowmodels.tradeasset.TradeAssetAmendmentFlowModel
import com.tradeix.concord.flowmodels.tradeasset.TradeAssetCancellationFlowModel
import com.tradeix.concord.flowmodels.tradeasset.TradeAssetIssuanceFlowModel
import com.tradeix.concord.flows.AbstractFlowTest
import com.tradeix.concord.flows.FlowTestHelper
import com.tradeix.concord.flows.tradeasset.TradeAssetCancellation
import com.tradeix.concord.flows.tradeasset.TradeAssetIssuance
import net.corda.core.flows.FlowException
import net.corda.core.transactions.SignedTransaction
import net.corda.node.internal.StartedNode
import net.corda.testing.node.MockNetwork
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TradeAssetCancellationFlowTests : AbstractFlowTest() {
    override fun configureNode(node: StartedNode<MockNetwork.MockNode>) {
        node.registerInitiatedFlow(TradeAssetIssuance.AcceptorFlow::class.java)
        node.registerInitiatedFlow(TradeAssetCancellation.AcceptorFlow::class.java)
    }

    @Test
    fun `Cancellation flow will fail if externalId is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issueTradeAsset(TestValueHelper.STATUS_INVOICE)
            FlowTestHelper.amendTradeAsset(network, conductor.node, TradeAssetAmendmentFlowModel(
                    externalId = null,
                    value = TestValueHelper.POSITIVE_TEN,
                    currency = TestValueHelper.DOLLARS
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'externalId' is required.", exception.validationErrors.single())
    }

    @Test
    fun `Cancellation flow for INVOICE status cannot be cancelled by a buyer`() {
        issueTradeAsset(TestValueHelper.STATUS_INVOICE)
        val exception = assertFailsWith<FlowException> {
            FlowTestHelper.cancelTradeAsset(network, buyer.node, TradeAssetCancellationFlowModel(
                    externalId = EXTERNAL_ID
            ))
        }

        assertEquals(
                "Flow verification failed: Trade asset of status INVOICE cannot be cancelled by the buyer.",
                exception.message
        )
    }

    @Test
    fun `Cancellation flow for PURCHASE_ORDER status cannot be cancelled by a supplier`() {
        issueTradeAsset(TestValueHelper.STATUS_PURCHASE_ORDER)
        val exception = assertFailsWith<FlowException> {
            FlowTestHelper.cancelTradeAsset(network, supplier.node, TradeAssetCancellationFlowModel(
                    externalId = EXTERNAL_ID
            ))
        }

        assertEquals(
                "Flow verification failed: Trade asset of status PURCHASE_ORDER cannot be cancelled by the supplier.",
                exception.message
        )
    }

    @Test
    fun `Cancellation flow for INVOICE status can be amended by a supplier`() {
        issueTradeAsset(TestValueHelper.STATUS_INVOICE)
        FlowTestHelper.cancelTradeAsset(network, supplier.node, TradeAssetCancellationFlowModel(
                externalId = EXTERNAL_ID
        ))
    }

    @Test
    fun `Cancellation flow for PURCHASE_ORDER status can be amended by a buyer`() {
        issueTradeAsset(TestValueHelper.STATUS_PURCHASE_ORDER)
        FlowTestHelper.cancelTradeAsset(network, buyer.node, TradeAssetCancellationFlowModel(
                externalId = EXTERNAL_ID
        ))
    }

    @Test
    fun `Cancellation flow initiated by the buyer is signed by the initiator`() {
        issueTradeAsset(TestValueHelper.STATUS_PURCHASE_ORDER)
        val transaction = FlowTestHelper.cancelTradeAsset(network, buyer.node, TradeAssetCancellationFlowModel(
                externalId = EXTERNAL_ID
        ))

        transaction.verifySignaturesExcept(supplier.publicKey, conductor.publicKey)
    }

    @Test
    fun `Cancellation flow initiated by the supplier is signed by the initiator`() {
        issueTradeAsset(TestValueHelper.STATUS_INVOICE)
        val transaction = FlowTestHelper.cancelTradeAsset(network, supplier.node, TradeAssetCancellationFlowModel(
                externalId = EXTERNAL_ID
        ))

        transaction.verifySignaturesExcept(buyer.publicKey, conductor.publicKey)
    }

    @Test
    fun `Cancellation flow initiated by the conductor is signed by the initiator`() {
        issueTradeAsset(TestValueHelper.STATUS_INVOICE)
        val transaction = FlowTestHelper.cancelTradeAsset(network, conductor.node, TradeAssetCancellationFlowModel(
                externalId = EXTERNAL_ID
        ))

        transaction.verifySignaturesExcept(buyer.publicKey, supplier.publicKey)
    }

    @Test
    fun `Cancellation flow initiated by the buyer is signed by the acceptor`() {
        issueTradeAsset(TestValueHelper.STATUS_PURCHASE_ORDER)
        val transaction = FlowTestHelper.cancelTradeAsset(network, buyer.node, TradeAssetCancellationFlowModel(
                externalId = EXTERNAL_ID
        ))

        transaction.verifySignaturesExcept(buyer.publicKey)
    }

    @Test
    fun `Cancellation flow initiated by the supplier is signed by the acceptor`() {
        issueTradeAsset(TestValueHelper.STATUS_INVOICE)
        val transaction = FlowTestHelper.cancelTradeAsset(network, supplier.node, TradeAssetCancellationFlowModel(
                externalId = EXTERNAL_ID
        ))

        transaction.verifySignaturesExcept(supplier.publicKey)
    }

    @Test
    fun `Cancellation flow initiated by the conductor is signed by the acceptor`() {
        issueTradeAsset(TestValueHelper.STATUS_INVOICE)
        val transaction = FlowTestHelper.cancelTradeAsset(network, conductor.node, TradeAssetCancellationFlowModel(
                externalId = EXTERNAL_ID
        ))

        transaction.verifySignaturesExcept(conductor.publicKey)
    }

    @Test
    fun `Cancellation flow records a transaction in all counter-party vaults`() {
        issueTradeAsset(TestValueHelper.STATUS_INVOICE)
        val transaction = FlowTestHelper.cancelTradeAsset(network, conductor.node, TradeAssetCancellationFlowModel(
                externalId = EXTERNAL_ID
        ))

        listOf(buyer.node, supplier.node, conductor.node).forEach {
            assertEquals(transaction, it.services.validatedTransactions.getTransaction(transaction.id))
        }
    }

    @Test
    fun `Cancellation flow transaction has a single input zero outputs`() {
        issueTradeAsset(TestValueHelper.STATUS_INVOICE)
        val transaction = FlowTestHelper.cancelTradeAsset(network, conductor.node, TradeAssetCancellationFlowModel(
                externalId = EXTERNAL_ID
        ))

        listOf(buyer.node, supplier.node, conductor.node).forEach {
            assertEquals(transaction, it.services.validatedTransactions.getTransaction(transaction.id))
            assertEquals(1, transaction.inputs.size)
            assertEquals(0, transaction.tx.outputs.size)
        }
    }

    private fun issueTradeAsset(status: String): SignedTransaction {
        return FlowTestHelper.issueTradeAsset(network, conductor.node, TradeAssetIssuanceFlowModel(
                externalId = EXTERNAL_ID,
                buyer = buyer.name,
                supplier = supplier.name,
                conductor = conductor.name,
                status = status,
                value = TestValueHelper.POSITIVE_ONE,
                currency = TestValueHelper.POUNDS
        ))
    }
}