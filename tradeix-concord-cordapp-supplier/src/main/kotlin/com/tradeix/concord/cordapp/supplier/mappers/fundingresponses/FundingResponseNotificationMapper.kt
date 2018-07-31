package com.tradeix.concord.cordapp.supplier.mappers.fundingresponses

import com.tradeix.concord.cordapp.supplier.messages.fundingresponses.FundingResponseNotificationMessage
import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.mapper.Mapper

class FundingResponseNotificationMapper
    : Mapper<FundingResponseState, FundingResponseNotificationMessage>() {

    override fun map(source: FundingResponseState): FundingResponseNotificationMessage {
        return FundingResponseNotificationMessage(
                externalId = source.linearId.externalId!!,
                fundingRequestExternalId = source.fundingRequestLinearId?.externalId,
                invoiceExternalIds = source.invoiceLinearIds.map { it.externalId!! },
                supplier = source.supplier.toString(),
                funder = source.funder.toString(),
                purchaseValue = source.purchaseValue.toDecimal(),
                currency = source.purchaseValue.token.currencyCode,
                advanceInvoiceValue = source.advanceInvoiceValue.toDecimal(),
                discountValue = source.discountValue.toDecimal(),
                baseRate = source.baseRate,
                status = source.status.toString()
        )
    }
}