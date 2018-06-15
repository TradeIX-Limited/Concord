package com.tradeix.concord.shared.domain.mapping

import com.tradeix.concord.shared.domain.schemas.FundingResponseSchemaV1
import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.mapper.Mapper

class FundingResponseSchemaV1Mapper
    : Mapper<FundingResponseState, FundingResponseSchemaV1.PersistentFundingResponseSchemaV1>() {

    override fun map(source: FundingResponseState): FundingResponseSchemaV1.PersistentFundingResponseSchemaV1 {
        return FundingResponseSchemaV1.PersistentFundingResponseSchemaV1(
                linearId = source.linearId.id,
                linearExternalId = source.linearId.externalId.toString(),
                supplier = source.supplier,
                invoiceNumber = source.invoiceNumber,
                status = source.status,
                funder = source.funder,
                purchaseValue = source.purchaseValue.toDecimal()
        )
    }
}