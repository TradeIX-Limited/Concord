package com.tradeix.concord.flows.invoice

import com.tradeix.concord.TestValueHelper.CANCELLED
import com.tradeix.concord.TestValueHelper.COMPOSER_PROGRAM_ID
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_01
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_02
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_03
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_04
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_05
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_06
import com.tradeix.concord.TestValueHelper.DATE_INSTANT_07
import com.tradeix.concord.TestValueHelper.EXTERNAL_ID
import com.tradeix.concord.TestValueHelper.HASH
import com.tradeix.concord.TestValueHelper.INVOICE_NUMBER
import com.tradeix.concord.TestValueHelper.INVOICE_TYPE
import com.tradeix.concord.TestValueHelper.INVOICE_VERSION
import com.tradeix.concord.TestValueHelper.OFFER_ID
import com.tradeix.concord.TestValueHelper.ORIGINATION_NETWORK
import com.tradeix.concord.TestValueHelper.POSITIVE_ONE
import com.tradeix.concord.TestValueHelper.POUNDS
import com.tradeix.concord.TestValueHelper.PURCHASE_ORDER_ID
import com.tradeix.concord.TestValueHelper.PURCHASE_ORDER_NUMBER
import com.tradeix.concord.TestValueHelper.REFERENCE
import com.tradeix.concord.TestValueHelper.REJECTION_REASON
import com.tradeix.concord.TestValueHelper.SITE_ID
import com.tradeix.concord.TestValueHelper.STATUS
import com.tradeix.concord.TestValueHelper.TIX_INVOICE_VERSION
import com.tradeix.concord.exceptions.FlowValidationException
import com.tradeix.concord.exceptions.FlowVerificationException
import com.tradeix.concord.flowmodels.invoice.InvoiceIssuanceFlowModel
import com.tradeix.concord.flowmodels.invoice.InvoiceOwnershipFlowModel
import com.tradeix.concord.flows.AbstractFlowTest
import com.tradeix.concord.flows.FlowTestHelper
import com.tradeix.concord.flows.FlowTestHelper.changeInvoiceOwner
import net.corda.core.transactions.SignedTransaction
import net.corda.node.internal.StartedNode
import net.corda.testing.node.MockNetwork
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.fail

//TODO: The tests are failing because the conductor is final in the Model. Hence we could not inject the mock
class InvoiceOwnershipFlowTests : AbstractFlowTest() {
    override fun configureNode(node: StartedNode<MockNetwork.MockNode>) {
        node.registerInitiatedFlow(InvoiceIssuance.AcceptorFlow::class.java)
        node.registerInitiatedFlow(InvoiceOwnership.AcceptorFlow::class.java)
    }

    @Test
    fun `Invoice ownership flow fails if externalId is omitted`() {
        issueInvoice()
        val exception = assertFailsWith<FlowValidationException> {
            changeInvoiceOwner(network, conductor.node, InvoiceOwnershipFlowModel(
                    externalId = null,
                    newOwner = funder.name
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'externalId' is required.", exception.validationErrors.single())
    }


    @Test
    fun `Invoice ownership flow fails if newOwner is omitted`() {
        issueInvoice()
        val exception = assertFailsWith<FlowValidationException> {
            changeInvoiceOwner(network, conductor.node, InvoiceOwnershipFlowModel(
                    externalId = EXTERNAL_ID,
                    newOwner = null
            ))
        }

        assertEquals(1, exception.validationErrors.size)
        assertEquals("Field 'newOwner' is required.", exception.validationErrors.single())
    }

    @Test
    fun `Invoice ownership flow initiated by the buyer fails because they're not the owner`() {
        issueInvoice()
        assertFailsWith<FlowVerificationException> {
            changeInvoiceOwner(network, buyer.node, InvoiceOwnershipFlowModel(
                    externalId = EXTERNAL_ID,
                    newOwner = funder.name
            ))
        }
    }

    @Test
    fun `Invoice ownership flow initiated by the conductor is signed by the initiator`() {
        issueInvoice()
        val transaction = changeInvoiceOwner(network, conductor.node, InvoiceOwnershipFlowModel(
                externalId = EXTERNAL_ID,
                newOwner = funder.name
        ))

        transaction.verifySignaturesExcept(buyer.publicKey, supplier.publicKey, funder.publicKey)
    }

    @Test
    fun `Invoice ownership flow initiated by the supplier is signed by the initiator`() {
        issueInvoice()
        val transaction = changeInvoiceOwner(network, supplier.node, InvoiceOwnershipFlowModel(
                externalId = EXTERNAL_ID,
                newOwner = funder.name
        ))

        transaction.verifySignaturesExcept(buyer.publicKey)
    }

    @Test
    fun `Invoice ownership flow initiated by the conductor is signed by the acceptor`() {
        issueInvoice()
        val transaction = changeInvoiceOwner(network, conductor.node, InvoiceOwnershipFlowModel(
                externalId = EXTERNAL_ID,
                newOwner = funder.name
        ))

        transaction.verifySignaturesExcept(conductor.publicKey)
    }

    @Test
    fun `Invoice ownership flow initiated by the supplier is signed by the acceptor`() {
        issueInvoice()
        val transaction = changeInvoiceOwner(network, supplier.node, InvoiceOwnershipFlowModel(
                externalId = EXTERNAL_ID,
                newOwner = funder.name
        ))

        transaction.verifySignaturesExcept()
    }

    @Test
    fun `Invoice ownership flow records a transaction in all counter-party vaults`() {
        issueInvoice()
        val transaction = changeInvoiceOwner(network, conductor.node, InvoiceOwnershipFlowModel(
                externalId = EXTERNAL_ID,
                newOwner = funder.name
        ))

        listOf(buyer.node, supplier.node, funder.node, conductor.node).forEach {
            assertEquals(transaction, it.services.validatedTransactions.getTransaction(transaction.id))
        }
    }

    @Test
    fun `Invoice ownership flow transaction has an equal number of inputs and outputs`() {
        issueInvoice()
        val transaction = changeInvoiceOwner(network, conductor.node, InvoiceOwnershipFlowModel(
                externalId = EXTERNAL_ID,
                newOwner = funder.name
        ))

        listOf(buyer.node, supplier.node, funder.node, conductor.node).forEach {
            val recordedTransaction = it.services.validatedTransactions.getTransaction(transaction.id) ?: fail()
            assertEquals(recordedTransaction.inputs.size, recordedTransaction.tx.outputs.size)
        }
    }

    private fun issueInvoice() {
        FlowTestHelper.issueInvoice(network, buyer.node, InvoiceIssuanceFlowModel(
                externalId = EXTERNAL_ID,
                attachmentId = null,
                conductor = conductor.name,
                buyer = buyer.name,
                supplier = supplier.name,
                invoiceVersion = INVOICE_VERSION,
                invoiceVersionDate = DATE_INSTANT_01,
                tixInvoiceVersion = TIX_INVOICE_VERSION,
                invoiceNumber = INVOICE_NUMBER,
                invoiceType = INVOICE_TYPE,
                reference = REFERENCE,
                dueDate = DATE_INSTANT_02,
                offerId = OFFER_ID,
                amount = POSITIVE_ONE,
                totalOutstanding = POSITIVE_ONE,
                created = DATE_INSTANT_03,
                updated = DATE_INSTANT_04,
                expectedSettlementDate = DATE_INSTANT_04,
                settlementDate = DATE_INSTANT_05,
                mandatoryReconciliationDate = DATE_INSTANT_06,
                invoiceDate = DATE_INSTANT_07,
                status = STATUS,
                rejectionReason = REJECTION_REASON,
                eligibleValue = POSITIVE_ONE,
                invoicePurchaseValue = POSITIVE_ONE,
                tradeDate = DATE_INSTANT_06,
                tradePaymentDate = DATE_INSTANT_06,
                invoicePayments = POSITIVE_ONE,
                invoiceDilutions = POSITIVE_ONE,
                cancelled = CANCELLED,
                closeDate = DATE_INSTANT_06,
                originationNetwork = ORIGINATION_NETWORK,
                hash = HASH,
                currency = POUNDS,
                siteId = SITE_ID,
                purchaseOrderNumber = PURCHASE_ORDER_NUMBER,
                purchaseOrderId = PURCHASE_ORDER_ID,
                composerProgramId = COMPOSER_PROGRAM_ID
        ))
    }
}