package com.tradeix.concord.shared.domain.schemas

import com.tradeix.concord.shared.domain.enumerations.FundingResponseStatus
import net.corda.core.identity.AbstractParty
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

object FundingResponseSchema

object FundingResponseSchemaV1 : MappedSchema(
        schemaFamily = FundingResponseSchema.javaClass,
        version = 1,
        mappedTypes = listOf(PersistentFundingResponseSchemaV1::class.java)) {

    @Entity
    @Table(name = "funding_response_state")
    class PersistentFundingResponseSchemaV1(
            @Column(name = "linear_id")
            val linearId: UUID,

            @Column(name = "linear_external_id")
            val linearExternalId: String,

            @Column(name = "funding_request_linear_id")
            val fundingRequestLinearID: UUID?,

            @Column(name = "funding_request_linear_external_id")
            val fundingRequestExternalID: String?,

            @ElementCollection
            @Column(name = "invoice_linear_ids") // TODO : Collection<UniqueIdentifier>
            val invoiceLinearIds: Collection<String>,

            @Column(name = "supplier")
            val supplier: AbstractParty,

            @Column(name = "funder")
            val funder: AbstractParty,

            @Column(name = "purchase_value")
            val purchaseValue: BigDecimal,

            @Column(name = "currency")
            val currency: String,

            @Column(name = "status")
            @Enumerated(EnumType.STRING)
            val status: FundingResponseStatus,

            @Column(name = "advance_invoice_value")
            val advanceInvoiceValue: BigDecimal,

            @Column(name = "discount_value")
            val discountValue: BigDecimal,

            @Column(name = "base_rate")
            val baseRate: BigDecimal,

            @Column(name = "transaction_fee")
            val transactionFee: BigDecimal,

            @Column(name = "submitted")
            val submitted: LocalDateTime

    ) : PersistentState()
}