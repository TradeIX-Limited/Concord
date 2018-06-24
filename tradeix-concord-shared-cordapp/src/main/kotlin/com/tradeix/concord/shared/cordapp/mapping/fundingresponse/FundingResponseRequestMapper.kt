package com.tradeix.concord.shared.cordapp.mapping.fundingresponse

import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.mapper.Mapper
import com.tradeix.concord.shared.messages.fundingresponse.FundingResponseRequestMessage

class FundingResponseRequestMapper : Mapper<FundingResponseState, FundingResponseRequestMessage>() {

    override fun map(source: FundingResponseState): FundingResponseRequestMessage {
        return FundingResponseRequestMessage(
                externalId = source.linearId.externalId.toString(),
                funder = source.funder.nameOrNull().toString(),
                supplier = source.supplier.nameOrNull().toString(),
                fundingRequestExternalId = source.fundingRequestLinearId?.externalId,
                invoiceExternalIds = source.invoiceLinearIds.map { it.externalId!! },
                purchaseValue = source.purchaseValue.toDecimal(),
                currency = source.purchaseValue.token.currencyCode
        )
    }
}