package com.tradeix.concord.flows.purchaseorder

import com.tradeix.concord.TestValueHelper.ATTACHMENT
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_01
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_02
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_03
import com.tradeix.concord.TestValueHelper.DELIVERY_TERMS
import com.tradeix.concord.TestValueHelper.DESCRIPTION_OF_GOODS
import com.tradeix.concord.TestValueHelper.EXTERNAL_ID
import com.tradeix.concord.TestValueHelper.NEGATIVE_ONE
import com.tradeix.concord.TestValueHelper.NOT_A_VALID_HASH
import com.tradeix.concord.TestValueHelper.PORT_OF_SHIPMENT
import com.tradeix.concord.TestValueHelper.POSITIVE_ONE
import com.tradeix.concord.TestValueHelper.POUNDS
import com.tradeix.concord.TestValueHelper.PURCHASE_ORDER_REFERENCE
import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderAmendmentFlowModel
import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderIssuanceFlowModel
import com.tradeix.concord.flows.AbstractFlowTest
import com.tradeix.concord.flows.FlowTestHelper
import com.tradeix.concord.flows.FlowTestHelper.amendPurchaseOrder
import com.tradeix.concord.states.PurchaseOrderState
import net.corda.core.transactions.SignedTransaction
import net.corda.testing.node.StartedMockNode
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.fail

class PurchaseOrderAmendmentFlowTests : AbstractFlowTest() {
    override fun configureNode(node: StartedMockNode) {
        node.registerInitiatedFlow(PurchaseOrderIssuance.AcceptorFlow::class.java)
        node.registerInitiatedFlow(PurchaseOrderAmendment.AcceptorFlow::class.java)
    }

    @Test
    fun `PurchaseOrder amendment flow will fail if externalId is omitted`() {
        issuePurchaseOrder()
        val exception = assertFailsWith<FlowValidationException> {
            amendPurchaseOrder(network, conductor.node, PurchaseOrderAmendmentFlowModel(
                    externalId = null,
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

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'externalId' is required.", exception.validationErrors.single())
    }

    @Test
    fun `PurchaseOrder amendment flow will fail if supplier is omitted`() {
        issuePurchaseOrder()
        val exception = assertFailsWith<FlowValidationException> {
            amendPurchaseOrder(network, conductor.node, PurchaseOrderAmendmentFlowModel(
                    externalId = EXTERNAL_ID,
                    buyer = buyer.name,
                    supplier = null,
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

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'supplier' is required.", exception.validationErrors.single())
    }

    @Test
    fun `PurchaseOrder amendment flow will fail if conductor is omitted`() {
        issuePurchaseOrder()
        val exception = assertFailsWith<FlowValidationException> {
            amendPurchaseOrder(network, conductor.node, PurchaseOrderAmendmentFlowModel(
                    externalId = EXTERNAL_ID,
                    buyer = buyer.name,
                    supplier = supplier.name,
                    conductor = null,
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

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'conductor' is required.", exception.validationErrors.single())
    }

    @Test
    fun `PurchaseOrder amendment flow will fail if reference is omitted`() {
        issuePurchaseOrder()
        val exception = assertFailsWith<FlowValidationException> {
            amendPurchaseOrder(network, conductor.node, PurchaseOrderAmendmentFlowModel(
                    externalId = EXTERNAL_ID,
                    buyer = buyer.name,
                    supplier = supplier.name,
                    conductor = conductor.name,
                    reference = null,
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

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'reference' is required.", exception.validationErrors.single())
    }

    @Test
    fun `PurchaseOrder amendment flow will fail if value is omitted`() {
        issuePurchaseOrder()
        val exception = assertFailsWith<FlowValidationException> {
            amendPurchaseOrder(network, conductor.node, PurchaseOrderAmendmentFlowModel(
                    externalId = EXTERNAL_ID,
                    buyer = buyer.name,
                    supplier = supplier.name,
                    conductor = conductor.name,
                    reference = PURCHASE_ORDER_REFERENCE,
                    value = null,
                    currency = POUNDS,
                    created = DATE_INSTANT_01,
                    earliestShipment = DATE_INSTANT_02,
                    latestShipment = DATE_INSTANT_03,
                    portOfShipment = PORT_OF_SHIPMENT,
                    descriptionOfGoods = DESCRIPTION_OF_GOODS,
                    deliveryTerms = DELIVERY_TERMS
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'value' is required.", exception.validationErrors.single())
    }

    @Test
    fun `PurchaseOrder amendment flow will fail if value is negative`() {
        issuePurchaseOrder()
        val exception = assertFailsWith<FlowValidationException> {
            amendPurchaseOrder(network, conductor.node, PurchaseOrderAmendmentFlowModel(
                    externalId = EXTERNAL_ID,
                    buyer = buyer.name,
                    supplier = supplier.name,
                    conductor = conductor.name,
                    reference = PURCHASE_ORDER_REFERENCE,
                    value = NEGATIVE_ONE,
                    currency = POUNDS,
                    created = DATE_INSTANT_01,
                    earliestShipment = DATE_INSTANT_02,
                    latestShipment = DATE_INSTANT_03,
                    portOfShipment = PORT_OF_SHIPMENT,
                    descriptionOfGoods = DESCRIPTION_OF_GOODS,
                    deliveryTerms = DELIVERY_TERMS
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'value' cannot be zero or negative.", exception.validationErrors.single())
    }

    @Test
    fun `PurchaseOrder amendment flow will fail if currency is omitted`() {
        issuePurchaseOrder()
        val exception = assertFailsWith<FlowValidationException> {
            amendPurchaseOrder(network, conductor.node, PurchaseOrderAmendmentFlowModel(
                    externalId = EXTERNAL_ID,
                    buyer = buyer.name,
                    supplier = supplier.name,
                    conductor = conductor.name,
                    reference = PURCHASE_ORDER_REFERENCE,
                    value = POSITIVE_ONE,
                    currency = null,
                    created = DATE_INSTANT_01,
                    earliestShipment = DATE_INSTANT_02,
                    latestShipment = DATE_INSTANT_03,
                    portOfShipment = PORT_OF_SHIPMENT,
                    descriptionOfGoods = DESCRIPTION_OF_GOODS,
                    deliveryTerms = DELIVERY_TERMS
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'currency' is required.", exception.validationErrors.single())
    }

    @Test
    fun `PurchaseOrder amendment flow will fail if created is omitted`() {
        issuePurchaseOrder()
        val exception = assertFailsWith<FlowValidationException> {
            amendPurchaseOrder(network, conductor.node, PurchaseOrderAmendmentFlowModel(
                    externalId = EXTERNAL_ID,
                    buyer = buyer.name,
                    supplier = supplier.name,
                    conductor = conductor.name,
                    reference = PURCHASE_ORDER_REFERENCE,
                    value = POSITIVE_ONE,
                    currency = POUNDS,
                    created = null,
                    earliestShipment = DATE_INSTANT_02,
                    latestShipment = DATE_INSTANT_03,
                    portOfShipment = PORT_OF_SHIPMENT,
                    descriptionOfGoods = DESCRIPTION_OF_GOODS,
                    deliveryTerms = DELIVERY_TERMS
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'created' is required.", exception.validationErrors.single())
    }

    @Test
    fun `PurchaseOrder amendment flow will fail if earliestShipment is omitted`() {
        issuePurchaseOrder()
        val exception = assertFailsWith<FlowValidationException> {
            amendPurchaseOrder(network, conductor.node, PurchaseOrderAmendmentFlowModel(
                    externalId = EXTERNAL_ID,
                    buyer = buyer.name,
                    supplier = supplier.name,
                    conductor = conductor.name,
                    reference = PURCHASE_ORDER_REFERENCE,
                    value = POSITIVE_ONE,
                    currency = POUNDS,
                    created = DATE_INSTANT_01,
                    earliestShipment = null,
                    latestShipment = DATE_INSTANT_03,
                    portOfShipment = PORT_OF_SHIPMENT,
                    descriptionOfGoods = DESCRIPTION_OF_GOODS,
                    deliveryTerms = DELIVERY_TERMS
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'earliestShipment' is required.", exception.validationErrors.single())
    }

    @Test
    fun `PurchaseOrder amendment flow will fail if latestShipment is omitted`() {
        issuePurchaseOrder()
        val exception = assertFailsWith<FlowValidationException> {
            amendPurchaseOrder(network, conductor.node, PurchaseOrderAmendmentFlowModel(
                    externalId = EXTERNAL_ID,
                    buyer = buyer.name,
                    supplier = supplier.name,
                    conductor = conductor.name,
                    reference = PURCHASE_ORDER_REFERENCE,
                    value = POSITIVE_ONE,
                    currency = POUNDS,
                    created = DATE_INSTANT_01,
                    earliestShipment = DATE_INSTANT_02,
                    latestShipment = null,
                    portOfShipment = PORT_OF_SHIPMENT,
                    descriptionOfGoods = DESCRIPTION_OF_GOODS,
                    deliveryTerms = DELIVERY_TERMS
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'latestShipment' is required.", exception.validationErrors.single())
    }

    @Test
    fun `PurchaseOrder amendment flow will fail if portOfShipment is omitted`() {
        issuePurchaseOrder()
        val exception = assertFailsWith<FlowValidationException> {
            amendPurchaseOrder(network, conductor.node, PurchaseOrderAmendmentFlowModel(
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
                    portOfShipment = null,
                    descriptionOfGoods = DESCRIPTION_OF_GOODS,
                    deliveryTerms = DELIVERY_TERMS
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'portOfShipment' is required.", exception.validationErrors.single())
    }

    @Test
    fun `PurchaseOrder amendment flow will fail if descriptionOfGoods is omitted`() {
        issuePurchaseOrder()
        val exception = assertFailsWith<FlowValidationException> {
            amendPurchaseOrder(network, conductor.node, PurchaseOrderAmendmentFlowModel(
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
                    descriptionOfGoods = null,
                    deliveryTerms = DELIVERY_TERMS
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'descriptionOfGoods' is required.", exception.validationErrors.single())
    }

    @Test
    fun `PurchaseOrder amendment flow will fail if deliveryTerms is omitted`() {
        issuePurchaseOrder()
        val exception = assertFailsWith<FlowValidationException> {
            amendPurchaseOrder(network, conductor.node, PurchaseOrderAmendmentFlowModel(
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
                    deliveryTerms = null
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'deliveryTerms' is required.", exception.validationErrors.single())
    }

    @Test
    fun `PurchaseOrder amendment flow will fail if attachmentId is invalid`() {
        issuePurchaseOrder()
        val exception = assertFailsWith<FlowValidationException> {
            amendPurchaseOrder(network, conductor.node, PurchaseOrderAmendmentFlowModel(
                    externalId = EXTERNAL_ID,
                    attachmentId = NOT_A_VALID_HASH,
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

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Invalid Secure hash for attachment.", exception.validationErrors.single())
    }

    @Test
    fun `PurchaseOrder amendment flow initiated by the buyer is signed by the initiator`() {
        issuePurchaseOrder()
        val transaction = amendPurchaseOrder(network, buyer.node, PurchaseOrderAmendmentFlowModel(
                externalId = EXTERNAL_ID,
                supplier = supplier.name,
                conductor = conductor.name,
                buyer = buyer.name,
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

        transaction.verifySignaturesExcept(supplier.publicKey, conductor.publicKey)
    }

    @Test
    fun `PurchaseOrder amendment flow initiated by the buyer is signed by the acceptor`() {
        issuePurchaseOrder()
        val transaction = amendPurchaseOrder(network, buyer.node, PurchaseOrderAmendmentFlowModel(
                externalId = EXTERNAL_ID,
                supplier = supplier.name,
                conductor = conductor.name,
                buyer = buyer.name,
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

        transaction.verifySignaturesExcept(buyer.publicKey)
    }

    @Test
    fun `PurchaseOrder amendment flow initiated by the conductor is signed by the initiator`() {
        issuePurchaseOrder()
        val transaction = amendPurchaseOrder(network, conductor.node, PurchaseOrderAmendmentFlowModel(
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

        transaction.verifySignaturesExcept(buyer.publicKey, supplier.publicKey)
    }

    @Test
    fun `PurchaseOrder amendment flow initiated by the conductor is signed by the acceptor`() {
        issuePurchaseOrder()
        val transaction = amendPurchaseOrder(network, conductor.node, PurchaseOrderAmendmentFlowModel(
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

        transaction.verifySignaturesExcept(conductor.publicKey)
    }

    @Test
    fun `PurchaseOrder amendment flow records a transaction in all counter-party vaults`() {
        issuePurchaseOrder()
        val transaction = amendPurchaseOrder(network, conductor.node, PurchaseOrderAmendmentFlowModel(
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

        listOf(buyer.node, supplier.node, conductor.node).forEach {
            assertEquals(transaction, it.services.validatedTransactions.getTransaction(transaction.id))
        }
    }

    @Test
    fun `PurchaseOrder amendment flow has zero inputs and a single output`() {
        issuePurchaseOrder()
        val transaction = amendPurchaseOrder(network, conductor.node, PurchaseOrderAmendmentFlowModel(
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

        listOf(buyer.node, supplier.node, conductor.node).forEach {
            val recordedTransaction = it.services.validatedTransactions.getTransaction(transaction.id) ?: fail()
            assertEquals(1, recordedTransaction.tx.inputs.size)
            assertEquals(1, recordedTransaction.tx.outputs.size)
            assertEquals(EXTERNAL_ID, recordedTransaction.tx.outputsOfType<PurchaseOrderState>().single().linearId.externalId)
        }
    }

    @Test
    fun `PurchaseOrder issuance flow with a valid attachmentId will store the attachment`() {
        issuePurchaseOrder()
        val validAttachment = conductor.node.transaction {
            conductor.node.services.attachments.importAttachment(File(ATTACHMENT).inputStream(), "UPLOADER", "FILENAME")
        }

        val transaction = amendPurchaseOrder(network, conductor.node, PurchaseOrderAmendmentFlowModel(
                externalId = EXTERNAL_ID,
                attachmentId = validAttachment.toString(),
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

        listOf(buyer.node, supplier.node, conductor.node).forEach {
            assertEquals(transaction, it.services.validatedTransactions.getTransaction(transaction.id))
            // TODO : Can we actually check that the attachment exists?
        }
    }

    private fun issuePurchaseOrder(): SignedTransaction {
        return FlowTestHelper.issuePurchaseOrder(network, conductor.node, PurchaseOrderIssuanceFlowModel(
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
}