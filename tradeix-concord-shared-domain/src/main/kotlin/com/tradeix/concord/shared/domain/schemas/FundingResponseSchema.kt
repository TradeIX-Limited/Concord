package com.tradeix.concord.shared.domain.schemas

import com.tradeix.concord.shared.domain.enumerations.FundingResponseStatus
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.CordaX500Name
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
    @Table(name = "funding_response_state")
    class PersistentFundingResponseSchemaV1(
            @Column(name = "linear_id")
            val linearId: UUID? = null, //linear ID of UniqueIdentifier

            @Column(name = "linear_external_id")
            val linearExternalId: String? = null, //externalid of UniqueIdentifier

            @Column(name = "funding_request_linear_id")
            val fundingRequestLinearID: UUID? = null, //linear ID of UniqueIdentifier

            @Column(name = "funding_request_linear_external_id")
            val fundingRequestExternalID: String? = null, //externalid of UniqueIdentifier

            @ElementCollection
            @Column(name = "invoice_linear_ids") // TODO : This is a short term solution till Matt comes up with another thought
            val invoiceLinearIds: Collection<String>? = null,

            @Column(name = "supplier")
            val supplier: AbstractParty? = null,

            @Column(name = "funder")
            val funder: AbstractParty? = null,

            @Column(name = "purchaseValue")
            val purchaseValue: BigDecimal? =null,

            @Column(name = "currency")
            val currency: String? = null,

            @Column(name = "status")
            @Enumerated(EnumType.STRING)
            val status: FundingResponseStatus? = null
    ): PersistentState()
}