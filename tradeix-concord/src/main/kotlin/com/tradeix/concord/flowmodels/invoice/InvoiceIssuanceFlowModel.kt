package com.tradeix.concord.flowmodels.invoice

import com.tradeix.concord.messages.SingleIdentityMessage
import net.corda.core.identity.CordaX500Name
import net.corda.core.node.services.AttachmentId
import java.math.BigDecimal
import java.time.Instant

data class InvoiceIssuanceFlowModel(
        override val externalId: String?,
        val attachmentId: String?,
        val conductor: CordaX500Name?  = CordaX500Name("TradeIX", "London", "GB"),
        val buyer: CordaX500Name?,
        val supplier: CordaX500Name?,
        val funder: CordaX500Name?,
        val invoiceVersion: String?,
        val invoiceVersionDate: Instant?,
        val tixInvoiceVersion: Int?,
        val invoiceNumber: String?,
        val invoiceType: String?,
        val reference: String?,
        val dueDate: Instant?,
        val offerId: Int?,
        val amount: BigDecimal?,
        val totalOutstanding: BigDecimal?,
        val created: Instant?,
        val updated: Instant?,
        val expectedSettlementDate: Instant?,
        val settlementDate: Instant?,
        val mandatoryReconciliationDate: Instant?,
        val invoiceDate: Instant?,
        val status: String?,
        val rejectionReason: String?,
        val eligibleValue: BigDecimal?,
        val invoicePurchaseValue: BigDecimal?,
        val tradeDate: Instant?,
        val tradePaymentDate: Instant?,
        val invoicePayments: BigDecimal?,
        val invoiceDilutions: BigDecimal?,
        val cancelled: Boolean?,
        val closeDate: Instant?,
        val originationNetwork: String?,
        val hash: String?,
        val currency: String?,
        val siteId: String?,
        val purchaseOrderNumber: String?,
        val purchaseOrderId: String?,
        val composerProgramId: Int?
) : SingleIdentityMessage