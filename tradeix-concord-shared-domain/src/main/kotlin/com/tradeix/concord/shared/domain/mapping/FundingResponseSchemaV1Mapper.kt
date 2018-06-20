package com.tradeix.concord.shared.domain.mapping

import com.tradeix.concord.shared.domain.schemas.FundingResponseSchemaV1
import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.mapper.Mapper

class FundingResponseSchemaV1Mapper
    : Mapper<FundingResponseState, FundingResponseSchemaV1.PersistentFundingResponseSchemaV1>() {

    override fun map(source: FundingResponseState): FundingResponseSchemaV1.PersistentFundingResponseSchemaV1 {
        val invoiceIds: MutableCollection<String> = mutableListOf()

        source.invoiceLinearIds.forEach {
            invoiceIds.add(it.externalId!!.toString())
        }

        return FundingResponseSchemaV1.PersistentFundingResponseSchemaV1(
                linearId = source.linearId.id,
                linearExternalId = source.linearId.toString(),
                invoiceLinearIds = invoiceIds,
                supplier = source.supplier,
                funder = source.funder,
                purchaseValue = source.purchaseValue.toDecimal(),
                currency = source.purchaseValue.token.currencyCode,
                status = source.status
        )
    }
}