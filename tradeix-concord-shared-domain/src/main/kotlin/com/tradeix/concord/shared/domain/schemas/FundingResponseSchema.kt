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
    @Table(name = "invoice_states")
    class PersistentFundingResponseSchemaV1(
            @Column(name = "linear_id")
            val linearId: UUID,

            @Column(name = "linear_external_id")
            val linearExternalId: String, //TODO : This could be removed

            @Column(name = "supplier")
            val supplier: AbstractParty,

            @Column(name = "invoice_number")
            val invoiceNumber: String,

            @Column(name = "purchaseValue")
            val purchaseValue : BigDecimal,

            @Column(name = "funder")
            val funder : AbstractParty,

            @Enumerated(EnumType.STRING)
            val status: FundingResponseStatus
    ) : PersistentState()
}