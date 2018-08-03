package com.tradeix.concord.cordapp.funder.mappers.fundingresponses

import com.tradeix.concord.cordapp.funder.messages.fundingresponses.FundingResponseAcceptanceNotificationMessage
import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.mapper.Mapper

class FundingResponseAcceptanceNotificationMapper : Mapper<FundingResponseState, FundingResponseAcceptanceNotificationMessage>() {

    override fun map(source: FundingResponseState): FundingResponseAcceptanceNotificationMessage {
        return FundingResponseAcceptanceNotificationMessage(
                externalId = source.linearId.externalId!!,
                transactionId = "transaction Id extracted from the transfer flow" // TODO : HOW DO YOU DO THAT??
        )
    }
}