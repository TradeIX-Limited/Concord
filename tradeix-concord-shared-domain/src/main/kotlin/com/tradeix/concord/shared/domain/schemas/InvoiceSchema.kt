package com.tradeix.concord.shared.domain.schemas

import net.corda.core.identity.AbstractParty
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import java.math.BigDecimal
import java.time.LocalDateTime
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
    @Table(name = "invoice_states")
    class PersistentInvoiceSchemaV1(
            @Column(name = "linear_id")
            val linearId: UUID,

            @Column(name = "linear_external_id")
            val linearExternalId: String,

            @Column(name = "owner")
            val owner: AbstractParty,

            @Column(name = "buyer")
            val buyer: AbstractParty?,

            @Column(name = "supplier")
            val supplier: AbstractParty,

            @Column(name = "invoice_number")
            val invoiceNumber: String,

            @Column(name = "reference")
            val reference: String,

            @Column(name = "due_date")
            val dueDate: LocalDateTime,

            @Column(name = "currency")
            val currency: String,

            @Column(name = "amount")
            val amount: BigDecimal,

            @Column(name = "total_outstanding")
            val totalOutstanding: BigDecimal,

            @Column(name = "settlement_date")
            val settlementDate: LocalDateTime,

            @Column(name = "invoice_date")
            val invoiceDate: LocalDateTime,

            @Column(name = "invoice_payments")
            val invoicePayments: BigDecimal,

            @Column(name = "invoice_dilutions")
            val invoiceDilutions: BigDecimal,

            @Column(name = "origination_network")
            val originationNetwork: String,

            @Column(name = "site_id")
            val siteId: String,

            @Column(name = "tradeDate")
            val tradeDate: LocalDateTime,

            @Column(name = "tradePaymentDate")
            val tradePaymentDate: LocalDateTime,

            @Column(name= "buyerCompanyName")
            val buyerCompanyName: String
    ) : PersistentState()
}