package com.tradeix.concord.cordapp.funder.mappers.fundingresponses

import com.tradeix.concord.cordapp.funder.messages.fundingresponses.FundingResponseNotificationMessage
import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.mapper.Mapper

class FundingResponseNotificationMapper : Mapper<FundingResponseState, FundingResponseNotificationMessage>() {

    override fun map(source: FundingResponseState): FundingResponseNotificationMessage {
        return FundingResponseNotificationMessage(
                externalId = source.linearId.externalId!!,
                status = source.status.toString(),
                exceptionReason = "Not available"
        )
    }
}