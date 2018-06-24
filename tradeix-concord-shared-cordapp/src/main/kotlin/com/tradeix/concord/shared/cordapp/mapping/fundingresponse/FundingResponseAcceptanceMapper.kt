package com.tradeix.concord.shared.cordapp.mapping.fundingresponse

import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.mapper.InputAndOutput
import com.tradeix.concord.shared.mapper.Mapper
import com.tradeix.concord.shared.messages.fundingresponse.FundingResponseAcceptanceRequestMessage
import com.tradeix.concord.shared.services.VaultService
import net.corda.core.flows.FlowException
import net.corda.core.node.ServiceHub

class FundingResponseAcceptanceMapper(private val serviceHub: ServiceHub)
    : Mapper<FundingResponseAcceptanceRequestMessage, InputAndOutput<FundingResponseState>>() {

    override fun map(source: FundingResponseAcceptanceRequestMessage): InputAndOutput<FundingResponseState> {

        val vaultService = VaultService.fromServiceHub<FundingResponseState>(serviceHub)

        val inputState = vaultService
                .findByExternalId(source.externalId!!)
                .singleOrNull()

        if (inputState == null) {
            throw FlowException("A FundingResponseState with externalId '${source.externalId}' does not exist.")
        } else {
            val outputState = inputState.state.data.accept()
            return InputAndOutput(inputState, outputState)
        }
    }
}