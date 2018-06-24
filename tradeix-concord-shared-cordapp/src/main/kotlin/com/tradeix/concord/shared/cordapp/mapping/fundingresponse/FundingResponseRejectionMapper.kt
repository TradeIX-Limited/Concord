package com.tradeix.concord.shared.cordapp.mapping.fundingresponse

import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.mapper.InputAndOutput
import com.tradeix.concord.shared.mapper.Mapper
import com.tradeix.concord.shared.messages.fundingresponse.FundingResponseRejectionRequestMessage
import com.tradeix.concord.shared.services.VaultService
import net.corda.core.flows.FlowException
import net.corda.core.node.ServiceHub

class FundingResponseRejectionMapper(private val serviceHub: ServiceHub)
    : Mapper<FundingResponseRejectionRequestMessage, InputAndOutput<FundingResponseState>>() {

    override fun map(source: FundingResponseRejectionRequestMessage): InputAndOutput<FundingResponseState> {

        val vaultService = VaultService.fromServiceHub<FundingResponseState>(serviceHub)

        val inputState = vaultService
                .findByExternalId(source.externalId!!)
                .singleOrNull()

        if (inputState == null) {
            throw FlowException("A FundingResponseState with externalId '${source.externalId}' does not exist.")
        } else {
            val outputState = inputState.state.data.reject()
            return InputAndOutput(inputState, outputState)
        }
    }
}