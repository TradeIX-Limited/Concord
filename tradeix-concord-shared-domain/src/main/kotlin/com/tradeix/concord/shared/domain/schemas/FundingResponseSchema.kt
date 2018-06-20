package com.tradeix.concord.shared.domain.schemas

import com.tradeix.concord.shared.domain.enumerations.FundingResponseStatus
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.AbstractParty
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

object FundingResponseSchema

object FundingResponseSchemaV1 : MappedSchema(
        schemaFamily = FundingResponseSchema.javaClass,
        version = 1,
        mappedTypes = listOf(PersistentFundingResponseSchemaV1::class.java)) {

    @Entity
    @Table(name = "funding_response_states")
    class PersistentFundingResponseSchemaV1(
            @Column(name = "linear_id")
            val linearId: UUID,

            @Column(name = "linear_external_id")
            val linearExternalId: String,

            @ElementCollection
            @Column(name = "invoice_linear_ids") // TODO : This is a short term solution till Matt comes up with another thought
            val invoiceLinearIds: Collection<String>,

            @Column(name = "supplier")
            val supplier: AbstractParty,

            @Column(name = "funder")
            val funder: AbstractParty,

            @Column(name = "purchaseValue")
            val purchaseValue: BigDecimal,

            @Column(name = "currency")
            val currency: String,

            @Column(name = "status")
            @Enumerated(EnumType.STRING)
            val status: FundingResponseStatus
    ) : PersistentState()
}