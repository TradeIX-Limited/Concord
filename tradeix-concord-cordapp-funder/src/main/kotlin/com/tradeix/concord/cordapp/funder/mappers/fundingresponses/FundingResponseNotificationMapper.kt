package com.tradeix.concord.cordapp.funder.mappers.fundingresponses

import com.tradeix.concord.cordapp.funder.messages.fundingresponses.FundingResponseImportNotificationMessage
import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.mapper.Mapper

class FundingResponseNotificationMapper : Mapper<FundingResponseState, FundingResponseImportNotificationMessage>() {

    override fun map(source: FundingResponseState): FundingResponseImportNotificationMessage {
        return FundingResponseImportNotificationMessage(
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