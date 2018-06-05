package com.tradeix.concord.flows.purchaseorder

import com.tradeix.concord.TestValueHelper.DATE_INSTANT_01
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_02
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_03
import com.tradeix.concord.TestValueHelper.DELIVERY_TERMS
import com.tradeix.concord.TestValueHelper.DESCRIPTION_OF_GOODS
import com.tradeix.concord.TestValueHelper.EXTERNAL_ID
import com.tradeix.concord.TestValueHelper.PORT_OF_SHIPMENT
import com.tradeix.concord.TestValueHelper.POSITIVE_ONE
import com.tradeix.concord.TestValueHelper.POUNDS
import com.tradeix.concord.TestValueHelper.PURCHASE_ORDER_REFERENCE
import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderCancellationFlowModel
import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderIssuanceFlowModel
import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderOwnershipFlowModel
import com.tradeix.concord.flows.AbstractFlowTest
import com.tradeix.concord.flows.FlowTestHelper
import com.tradeix.concord.flows.FlowTestHelper.cancelPurchaseOrder
import com.tradeix.concord.flows.FlowTestHelper.issuePurchaseOrder
import net.corda.core.transactions.SignedTransaction
import net.corda.testing.node.StartedMockNode
import org.junit.Ignore
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFailsWith
import kotlin.test.fail

@Ignore
class PurchaseOrderCancellationFlowTests : AbstractFlowTest() {
    override fun configureNode(node: StartedMockNode) {
        node.registerInitiatedFlow(PurchaseOrderIssuance.AcceptorFlow::class.java)
        node.registerInitiatedFlow(PurchaseOrderOwnership.AcceptorFlow::class.java)
        node.registerInitiatedFlow(PurchaseOrderCancellation.AcceptorFlow::class.java)
    }

    @Test
    fun `PurchaseOrder cancellation flow fails if externalId is omitted`() {
        issuePurchaseOrder()
        val exception = assertFailsWith<FlowValidationException> {
            cancelPurchaseOrder(network, conductor.node, PurchaseOrderCancellationFlowModel(
                    externalId = null
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'externalId' is required.", exception.validationErrors.single())
    }

    @Test
    fun `PurchaseOrder cancellation flow fails if buyer is not the owner`() {
        issuePurchaseOrder()
        changePurchaseOrderOwner()
        assertFails {
            cancelPurchaseOrder(network, conductor.node, PurchaseOrderCancellationFlowModel(
                    externalId = EXTERNAL_ID
            ))
        }
    }

    @Test
    fun `PurchaseOrder cancellation flow is signed by the initiator`() {
        issuePurchaseOrder()
        val transaction = cancelPurchaseOrder(network, conductor.node, PurchaseOrderCancellationFlowModel(
                externalId = EXTERNAL_ID
        ))

        transaction.verifySignaturesExcept(buyer.publicKey, supplier.publicKey)
    }

    @Test
    fun `PurchaseOrder cancellation flow is signed by the acceptor`() {
        issuePurchaseOrder()
        val transaction = cancelPurchaseOrder(network, conductor.node, PurchaseOrderCancellationFlowModel(
                externalId = EXTERNAL_ID
        ))

        transaction.verifySignaturesExcept(conductor.publicKey)
    }

    @Test
    fun `PurchaseOrder cancellation flow records a transaction in all counter-party vaults`() {
        issuePurchaseOrder()
        val transaction = cancelPurchaseOrder(network, conductor.node, PurchaseOrderCancellationFlowModel(
                externalId = EXTERNAL_ID
        ))

        listOf(buyer.node, supplier.node, conductor.node).forEach {
            assertEquals(transaction, it.services.validatedTransactions.getTransaction(transaction.id))
        }
    }

    @Test
    fun `PurchaseOrder cancellation flow has one input and zero outputs`() {
        issuePurchaseOrder()
        val transaction = cancelPurchaseOrder(network, conductor.node, PurchaseOrderCancellationFlowModel(
                externalId = EXTERNAL_ID
        ))

        listOf(buyer.node, supplier.node, conductor.node).forEach {
            val recordedTransaction = it.services.validatedTransactions.getTransaction(transaction.id) ?: fail()
            assertEquals(1, recordedTransaction.tx.inputs.size)
            assertEquals(0, recordedTransaction.tx.outputs.size)
        }
    }

    private fun issuePurchaseOrder(): SignedTransaction {
        return issuePurchaseOrder(network, conductor.node, PurchaseOrderIssuanceFlowModel(
                externalId = EXTERNAL_ID,
                buyer = buyer.name,
                supplier = supplier.name,
                conductor = conductor.name,
                reference = PURCHASE_ORDER_REFERENCE,
                value = POSITIVE_ONE,
                currency = POUNDS,
                created = DATE_INSTANT_01,
                earliestShipment = DATE_INSTANT_02,
                latestShipment = DATE_INSTANT_03,
                portOfShipment = PORT_OF_SHIPMENT,
                descriptionOfGoods = DESCRIPTION_OF_GOODS,
                deliveryTerms = DELIVERY_TERMS
        ))
    }

    private fun changePurchaseOrderOwner(): SignedTransaction {
        return FlowTestHelper.changePurchaseOrderOwner(network, conductor.node, PurchaseOrderOwnershipFlowModel(
                externalIds = listOf(EXTERNAL_ID),
                newOwner = funder.name
        ))
    }
}