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
import com.tradeix.concord.flowmodels.invoice.InvoiceIPUFlowModel
import com.tradeix.concord.flowmodels.invoice.InvoiceIssuanceFlowModel
import com.tradeix.concord.flows.AbstractFlowTest
import com.tradeix.concord.flows.FlowTestHelper
import com.tradeix.concord.flows.FlowTestHelper.setInvoiceIPU
import com.tradeix.concord.helpers.FlowHelper
import com.tradeix.concord.states.InvoiceState
import net.corda.core.transactions.SignedTransaction
import net.corda.testing.node.StartedMockNode
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class InvoiceIPUFlowTests : AbstractFlowTest() {

  override fun configureNode(node: StartedMockNode) {
    node.registerInitiatedFlow(InvoiceIssuance.AcceptorFlow::class.java)
    node.registerInitiatedFlow(InvoiceIPU.AcceptorFlow::class.java)
  }

  @Test
  fun `Invoice IPU flow initiated by the buyer is signed by the initiator`() {
    issueInvoice()
    val transaction = setInvoiceIPU(network, buyer.node, InvoiceIPUFlowModel(externalId = EXTERNAL_ID))
    transaction.verifySignaturesExcept(supplier.publicKey, conductor.publicKey)
  }

  @Test
  fun `Invoice IPU flow initiated by the buyer is signed by the acceptor`() {
    issueInvoice()
    val transaction = setInvoiceIPU(network, buyer.node, InvoiceIPUFlowModel(externalId = EXTERNAL_ID))

    transaction.verifySignaturesExcept(buyer.publicKey)
  }

  @Test
  fun `Invoice IPU flow initiated by the conductor is signed by the initiator`() {
    issueInvoice()
    val transaction = setInvoiceIPU(network, conductor.node, InvoiceIPUFlowModel(externalId = EXTERNAL_ID))

    transaction.verifySignaturesExcept(buyer.publicKey, supplier.publicKey)
  }

  @Test
  fun `Invoice IPU flow initiated by the conductor is signed by the acceptor`() {
    issueInvoice()
    val transaction = setInvoiceIPU(network, conductor.node, InvoiceIPUFlowModel(externalId = EXTERNAL_ID))

    transaction.verifySignaturesExcept(buyer.publicKey, supplier.publicKey)
  }

  @Test
  fun `Invoice IPU flow records a transaction in all counter-party vaults`() {
    issueInvoice()
    val transaction = setInvoiceIPU(network, conductor.node, InvoiceIPUFlowModel(externalId = EXTERNAL_ID))

    listOf(buyer.node, supplier.node, conductor.node).forEach {
      assertEquals(transaction, it.services.validatedTransactions.getTransaction(transaction.id))
    }
  }

  @Test
  fun `Invoice IPU flow has a single input and a single output`() {
    issueInvoice()
    val transaction = setInvoiceIPU(network, conductor.node, InvoiceIPUFlowModel(externalId = EXTERNAL_ID))

    transaction.verifySignaturesExcept(buyer.publicKey, supplier.publicKey)

    listOf(buyer.node, supplier.node, conductor.node).forEach {
      val recordedTransaction = it.services.validatedTransactions.getTransaction(transaction.id) ?: fail()
      assertEquals(1, recordedTransaction.tx.inputs.size)
      assertEquals(1, recordedTransaction.tx.outputs.size)
      assertEquals(EXTERNAL_ID, recordedTransaction.tx.outputsOfType<InvoiceState>().single().linearId.externalId)
    }
  }

  @Test
  fun `Invoice IPU flow can use system property to select metering notary`() {
    IssueWithSpecificNotary("MeteringNotary")
  }

  @Test
  fun `Invoice IPU flow can use system property to select bootstrap notary`() {
    IssueWithSpecificNotary("BootstrapNotary")
  }


  private fun IssueWithSpecificNotary(notaryOrganisationName: String) {
    System.setProperty(FlowHelper.BNO_SELECTED_NOTARY_ORGANISATION_NAME, notaryOrganisationName)
    issueInvoice()
    val transaction = setInvoiceIPU(network, conductor.node, InvoiceIPUFlowModel(externalId = EXTERNAL_ID))
    transaction.verifySignaturesExcept(buyer.publicKey, supplier.publicKey)
    assertEquals(transaction.notary!!.name.organisation, notaryOrganisationName)
  }

  fun issueInvoice(): SignedTransaction {
    return FlowTestHelper.issueInvoice(network, conductor.node, InvoiceIssuanceFlowModel(
      externalId = EXTERNAL_ID,
      attachmentId = null,
      buyer = buyer.name,
      supplier = supplier.name,
      funder = funder.name,
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