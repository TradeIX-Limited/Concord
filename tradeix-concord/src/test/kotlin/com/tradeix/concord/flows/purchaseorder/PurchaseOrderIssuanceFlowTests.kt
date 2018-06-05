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
import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderIssuanceFlowModel
import com.tradeix.concord.flows.AbstractFlowTest
import com.tradeix.concord.flows.FlowTestHelper.issuePurchaseOrder
import com.tradeix.concord.states.PurchaseOrderState
import net.corda.testing.node.StartedMockNode
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.fail
import org.junit.Ignore

@Ignore
class PurchaseOrderIssuanceFlowTests : AbstractFlowTest() {
    override fun configureNode(node: StartedMockNode) {
        node.registerInitiatedFlow(PurchaseOrderIssuance.AcceptorFlow::class.java)
    }

    @Test
    fun `PurchaseOrder issuance flow will fail if externalId is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issuePurchaseOrder(network, conductor.node, PurchaseOrderIssuanceFlowModel(
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
    fun `PurchaseOrder issuance flow will fail if supplier is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issuePurchaseOrder(network, conductor.node, PurchaseOrderIssuanceFlowModel(
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
    fun `PurchaseOrder issuance flow will fail if conductor is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issuePurchaseOrder(network, conductor.node, PurchaseOrderIssuanceFlowModel(
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
    fun `PurchaseOrder issuance flow will fail if reference is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issuePurchaseOrder(network, conductor.node, PurchaseOrderIssuanceFlowModel(
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
    fun `PurchaseOrder issuance flow will fail if value is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issuePurchaseOrder(network, conductor.node, PurchaseOrderIssuanceFlowModel(
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
    fun `PurchaseOrder issuance flow will fail if value is negative`() {
        val exception = assertFailsWith<FlowValidationException> {
            issuePurchaseOrder(network, conductor.node, PurchaseOrderIssuanceFlowModel(
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
    fun `PurchaseOrder issuance flow will fail if currency is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issuePurchaseOrder(network, conductor.node, PurchaseOrderIssuanceFlowModel(
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
    fun `PurchaseOrder issuance flow will fail if created is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issuePurchaseOrder(network, conductor.node, PurchaseOrderIssuanceFlowModel(
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
    fun `PurchaseOrder issuance flow will fail if earliestShipment is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issuePurchaseOrder(network, conductor.node, PurchaseOrderIssuanceFlowModel(
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
    fun `PurchaseOrder issuance flow will fail if latestShipment is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issuePurchaseOrder(network, conductor.node, PurchaseOrderIssuanceFlowModel(
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
    fun `PurchaseOrder issuance flow will fail if portOfShipment is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issuePurchaseOrder(network, conductor.node, PurchaseOrderIssuanceFlowModel(
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
    fun `PurchaseOrder issuance flow will fail if descriptionOfGoods is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issuePurchaseOrder(network, conductor.node, PurchaseOrderIssuanceFlowModel(
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
    fun `PurchaseOrder issuance flow will fail if deliveryTerms is omitted`() {
        val exception = assertFailsWith<FlowValidationException> {
            issuePurchaseOrder(network, conductor.node, PurchaseOrderIssuanceFlowModel(
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
    fun `PurchaseOrder issuance flow will fail if attachmentId is invalid`() {
        val exception = assertFailsWith<FlowValidationException> {
            issuePurchaseOrder(network, conductor.node, PurchaseOrderIssuanceFlowModel(
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
    fun `PurchaseOrder issuance flow initiated by the buyer is signed by the initiator`() {
        val transaction = issuePurchaseOrder(network, buyer.node, PurchaseOrderIssuanceFlowModel(
                externalId = EXTERNAL_ID,
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

        transaction.verifySignaturesExcept(supplier.publicKey, conductor.publicKey)
    }

    @Test
    fun `PurchaseOrder issuance flow initiated by the buyer is signed by the acceptor`() {
        val transaction = issuePurchaseOrder(network, buyer.node, PurchaseOrderIssuanceFlowModel(
                externalId = EXTERNAL_ID,
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

        transaction.verifySignaturesExcept(buyer.publicKey)
    }

    @Test
    fun `PurchaseOrder issuance flow initiated by the conductor is signed by the initiator`() {
        val transaction = issuePurchaseOrder(network, conductor.node, PurchaseOrderIssuanceFlowModel(
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
    fun `PurchaseOrder issuance flow initiated by the conductor is signed by the acceptor`() {
        val transaction = issuePurchaseOrder(network, conductor.node, PurchaseOrderIssuanceFlowModel(
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
    fun `PurchaseOrder issuance flow records a transaction in all counter-party vaults`() {
        val transaction = issuePurchaseOrder(network, conductor.node, PurchaseOrderIssuanceFlowModel(
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
    fun `PurchaseOrder issuance flow has zero inputs and a single output`() {
        val transaction = issuePurchaseOrder(network, conductor.node, PurchaseOrderIssuanceFlowModel(
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
            assertEquals(0, recordedTransaction.tx.inputs.size)
            assertEquals(1, recordedTransaction.tx.outputs.size)
            assertEquals(EXTERNAL_ID, recordedTransaction.tx.outputsOfType<PurchaseOrderState>().single().linearId.externalId)
        }
    }

    @Test
    fun `PurchaseOrder issuance flow with a valid attachmentId will store the attachment`() {
        val validAttachment = conductor.node.transaction {
            conductor.node.services.attachments.importAttachment(File(ATTACHMENT).inputStream(), "UPLOADER", "FILENAME")
        }

        val transaction = issuePurchaseOrder(network, conductor.node, PurchaseOrderIssuanceFlowModel(
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
}