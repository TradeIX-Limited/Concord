package com.tradeix.concord.cordapp.funder.mappers.fundingresponses

import com.tradeix.concord.cordapp.funder.messages.fundingresponses.FundingResponseRejectionNotificationMessage
import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.mapper.Mapper

class FundingResponseRejectionNotificationMapper : Mapper<FundingResponseState, FundingResponseRejectionNotificationMessage>() {

    override fun map(source: FundingResponseState): FundingResponseRejectionNotificationMessage {
        return FundingResponseRejectionNotificationMessage(
                externalId = source.linearId.externalId!!
        )
    }
}