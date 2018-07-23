package com.tradeix.concord.shared.client.mappers

import com.tradeix.concord.shared.client.messages.fundingresponses.FundingResponseResponseMessage
import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.mapper.Mapper

class FundingResponseResponseMapper : Mapper<FundingResponseState, FundingResponseResponseMessage>() {

    override fun map(source: FundingResponseState): FundingResponseResponseMessage {
        return FundingResponseResponseMessage(
                externalId = source.linearId.externalId!!,
                invoiceExternalIds = source.invoiceLinearIds.map { it.externalId!! },
                supplier = source.supplier.toString(),
                funder = source.funder.toString(),
                purchaseValue = source.purchaseValue.toDecimal(),
                currency = source.purchaseValue.token.currencyCode,
                advanceInvoiceValue = source.advanceInvoiceValue.toDecimal(),
                discountValue = source.discountValue.toDecimal(),
                baseRate = source.baseRate
        )
    }
}