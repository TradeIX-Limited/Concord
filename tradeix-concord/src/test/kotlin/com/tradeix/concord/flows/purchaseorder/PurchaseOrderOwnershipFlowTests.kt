package com.tradeix.concord.flows.purchaseorder

import com.tradeix.concord.TestValueHelper.DATE_INSTANT_01
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_02
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_03
import com.tradeix.concord.TestValueHelper.DELIVERY_TERMS
import com.tradeix.concord.TestValueHelper.DESCRIPTION_OF_GOODS
import com.tradeix.concord.TestValueHelper.PORT_OF_SHIPMENT
import com.tradeix.concord.TestValueHelper.POSITIVE_ONE
import com.tradeix.concord.TestValueHelper.POUNDS
import com.tradeix.concord.TestValueHelper.PURCHASE_ORDER_EXTERNAL_IDS
import com.tradeix.concord.TestValueHelper.PURCHASE_ORDER_REFERENCE
import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.exceptions.FlowVerificationException
import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderIssuanceFlowModel
import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderOwnershipFlowModel
import com.tradeix.concord.flows.AbstractFlowTest
import com.tradeix.concord.flows.FlowTestHelper
import com.tradeix.concord.flows.FlowTestHelper.changePurchaseOrderOwner
import net.corda.core.transactions.SignedTransaction
import net.corda.testing.node.StartedMockNode
import org.junit.Ignore
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.fail

@Ignore
class PurchaseOrderOwnershipFlowTests : AbstractFlowTest() {
    override fun configureNode(node: StartedMockNode) {
        node.registerInitiatedFlow(PurchaseOrderIssuance.AcceptorFlow::class.java)
        node.registerInitiatedFlow(PurchaseOrderOwnership.AcceptorFlow::class.java)
    }

    @Test
    fun `PurchaseOrder ownership flow fails if externalIds is omitted`() {
        issuePurchaseOrders()
        val exception = assertFailsWith<FlowValidationException> {
            changePurchaseOrderOwner(network, conductor.node, PurchaseOrderOwnershipFlowModel(
                    externalIds = null,
                    newOwner = funder.name
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'externalIds' is required.", exception.validationErrors.single())
    }

    @Test
    fun `PurchaseOrder ownership flow fails if externalIds is empty`() {
        issuePurchaseOrders()
        val exception = assertFailsWith<FlowValidationException> {
            changePurchaseOrderOwner(network, conductor.node, PurchaseOrderOwnershipFlowModel(
                    externalIds = emptyList(),
                    newOwner = funder.name
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'externalIds' must have at least one value.", exception.validationErrors.single())
    }

    @Test
    fun `PurchaseOrder ownership flow fails if newOwner is omitted`() {
        issuePurchaseOrders()
        val exception = assertFailsWith<FlowValidationException> {
            changePurchaseOrderOwner(network, conductor.node, PurchaseOrderOwnershipFlowModel(
                    externalIds = PURCHASE_ORDER_EXTERNAL_IDS,
                    newOwner = null
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'newOwner' is required.", exception.validationErrors.single())
    }

    @Test
    fun `PurchaseOrder ownership flow initiated by the supplier fails because they're not the owner`() {
        issuePurchaseOrders()
        assertFailsWith<FlowVerificationException> {
            changePurchaseOrderOwner(network, supplier.node, PurchaseOrderOwnershipFlowModel(
                    externalIds = PURCHASE_ORDER_EXTERNAL_IDS,
                    newOwner = funder.name
            ))
        }
    }

    @Test
    fun `PurchaseOrder ownership flow initiated by the conductor is signed by the initiator`() {
        issuePurchaseOrders()
        val transaction = changePurchaseOrderOwner(network, conductor.node, PurchaseOrderOwnershipFlowModel(
                externalIds = PURCHASE_ORDER_EXTERNAL_IDS,
                newOwner = funder.name
        ))

        transaction.verifySignaturesExcept(buyer.publicKey, supplier.publicKey, funder.publicKey)
    }

    @Test
    fun `PurchaseOrder ownership flow initiated by the buyer is signed by the initiator`() {
        issuePurchaseOrders()
        val transaction = changePurchaseOrderOwner(network, buyer.node, PurchaseOrderOwnershipFlowModel(
                externalIds = PURCHASE_ORDER_EXTERNAL_IDS,
                newOwner = funder.name
        ))

        transaction.verifySignaturesExcept(supplier.publicKey, conductor.publicKey, funder.publicKey)
    }

    @Test
    fun `PurchaseOrder ownership flow initiated by the conductor is signed by the acceptor`() {
        issuePurchaseOrders()
        val transaction = changePurchaseOrderOwner(network, conductor.node, PurchaseOrderOwnershipFlowModel(
                externalIds = PURCHASE_ORDER_EXTERNAL_IDS,
                newOwner = funder.name
        ))

        transaction.verifySignaturesExcept(conductor.publicKey)
    }

    @Test
    fun `PurchaseOrder ownership flow initiated by the buyer is signed by the acceptor`() {
        issuePurchaseOrders()
        val transaction = changePurchaseOrderOwner(network, buyer.node, PurchaseOrderOwnershipFlowModel(
                externalIds = PURCHASE_ORDER_EXTERNAL_IDS,
                newOwner = funder.name
        ))

        transaction.verifySignaturesExcept(buyer.publicKey)
    }

    @Test
    fun `PurchaseOrder ownership flow records a transaction in all counter-party vaults`() {
        issuePurchaseOrders()
        val transaction = changePurchaseOrderOwner(network, conductor.node, PurchaseOrderOwnershipFlowModel(
                externalIds = PURCHASE_ORDER_EXTERNAL_IDS,
                newOwner = funder.name
        ))

        listOf(buyer.node, supplier.node, funder.node, conductor.node).forEach {
            assertEquals(transaction, it.services.validatedTransactions.getTransaction(transaction.id))
        }
    }

    @Test
    fun `PurchaseOrder ownership flow transaction has an equal number of inputs and outputs`() {
        issuePurchaseOrders()
        val transaction = changePurchaseOrderOwner(network, conductor.node, PurchaseOrderOwnershipFlowModel(
                externalIds = PURCHASE_ORDER_EXTERNAL_IDS,
                newOwner = funder.name
        ))

        listOf(buyer.node, supplier.node, funder.node, conductor.node).forEach {
            val recordedTransaction = it.services.validatedTransactions.getTransaction(transaction.id) ?: fail()
            assertEquals(recordedTransaction.inputs.size, recordedTransaction.tx.outputs.size)
        }
    }

    private fun issuePurchaseOrders(): List<SignedTransaction> {
        return PURCHASE_ORDER_EXTERNAL_IDS.map {
            FlowTestHelper.issuePurchaseOrder(network, conductor.node, PurchaseOrderIssuanceFlowModel(
                    externalId = it,
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
    }
}