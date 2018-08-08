package com.tradeix.concord.shared.domain.mapping

import com.tradeix.concord.shared.domain.schemas.FundingResponseSchemaV1
import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.mapper.Mapper

class FundingResponseSchemaV1Mapper
    : Mapper<FundingResponseState, FundingResponseSchemaV1.PersistentFundingResponseSchemaV1>() {

    override fun map(source: FundingResponseState): FundingResponseSchemaV1.PersistentFundingResponseSchemaV1 {
        return FundingResponseSchemaV1.PersistentFundingResponseSchemaV1(
                linearId = source.linearId.id,
                linearExternalId = source.linearId.externalId!!,
                fundingRequestLinearID = source.fundingRequestLinearId?.id,
                fundingRequestExternalID = source.fundingRequestLinearId!!.externalId, // TODO : Must be mapped when FundingRequestState is implemented.
                invoiceLinearIds = source.invoiceLinearIds.map { it.externalId!! }, // TODO : Collection<UID>
                supplier = source.supplier,
                funder = source.funder,
                purchaseValue = source.purchaseValue.toDecimal(),
                currency = source.purchaseValue.token.currencyCode,
                status = source.status,
                advanceInvoiceValue = source.advanceInvoiceValue.toDecimal(),
                discountValue = source.discountValue.toDecimal(),
                baseRate = source.baseRate,
                transactionFee = source.transactionFee
        )
    }
}