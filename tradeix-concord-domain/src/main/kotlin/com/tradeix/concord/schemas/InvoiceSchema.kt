package com.tradeix.concord.schemas

import net.corda.core.identity.AbstractParty
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import java.math.BigDecimal
import java.time.Instant
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

object InvoiceSchema

object InvoiceSchemaV1 : MappedSchema(
        schemaFamily = InvoiceSchema.javaClass,
        version = 1,
        mappedTypes = listOf(PersistentInvoiceSchemaV1::class.java)) {
    @Entity
    @Table(name = "invoices")
    class PersistentInvoiceSchemaV1(
            @Column(name = "linear_id")
            var linearId: UUID,

            @Column(name = "external_id")
            var externalId: String,

            @Column(name = "owner", nullable = false)
            var owner: AbstractParty,

            @Column(name = "buyer", nullable = false)
            var buyer: AbstractParty,

            @Column(name = "supplier", nullable = false)
            var supplier: AbstractParty,

            @Column(name = "funder", nullable = false)
            var funder: AbstractParty,

            @Column(name = "conductor", nullable = false)
            var conductor: AbstractParty,

            @Column(name = "invoice_version")
            var invoiceVersion: String?,

            @Column(name = "invoice_version_date")
            var invoiceVersionDate: Instant?,

            @Column(name = "tix_invoice_version")
            var tixInvoiceVersion: Int?,

            @Column(name = "invoice_number", nullable = false)
            var invoiceNumber: String,

            @Column(name = "invoice_type", nullable = false)
            val invoiceType: String,

            @Column(name = "reference")
            val reference: String?,

            @Column(name = "due_date", nullable = false)
            val dueDate: Instant,

            @Column(name = "offer_id")
            val offerId: Int?,

            @Column(name = "amount", nullable = false)
            val amount: BigDecimal,

            @Column(name = "total_outstanding", nullable = false)
            val totalOutstanding: BigDecimal,

            @Column(name = "created", nullable = false)
            val created: Instant,

            @Column(name = "updated", nullable = false)
            val updated: Instant,

            @Column(name = "expected_settlement_date", nullable = false)
            val expectedSettlementDate: Instant,

            @Column(name = "settlement_date")
            val settlementDate: Instant?,

            @Column(name = "mandatory_reconciliation_date")
            val mandatoryReconciliationDate: Instant?,

            @Column(name = "invoice_date", nullable = false)
            val invoiceDate: Instant,

            @Column(name = "status", nullable = false)
            val status: String,

            @Column(name = "rejection_reason")
            val rejectionReason: String?,

            @Column(name = "eligible_value", nullable = false)
            val eligibleValue: BigDecimal,

            @Column(name = "invoice_purchase_value", nullable = false)
            val invoicePurchaseValue: BigDecimal,

            @Column(name = "trade_date")
            val tradeDate: Instant?,

            @Column(name = "trade_payment_date")
            val tradePaymentDate: Instant?,

            @Column(name = "invoice_payments", nullable = false)
            val invoicePayments: BigDecimal,

            @Column(name = "invoice_dilutions", nullable = false)
            val invoiceDilutions: BigDecimal?,

            @Column(name = "cancelled")
            val cancelled: Boolean?,

            @Column(name = "close_date")
            val closeDate: Instant?,

            @Column(name = "origination_network", nullable = false)
            val originationNetwork: String,

            @Column(name = "hash")
            val hash: String?,

            @Column(name = "currency", nullable = false)
            val currency: String,

            @Column(name = "site_id")
            val siteId: String?,

            @Column(name = "purchase_order_number")
            val purchaseOrderNumber: String?,

            @Column(name = "purchase_order_id")
            val purchaseOrderId: String?,

            @Column(name = "composer_program_id")
            val composerProgramId: Int?
    ) : PersistentState()
}
