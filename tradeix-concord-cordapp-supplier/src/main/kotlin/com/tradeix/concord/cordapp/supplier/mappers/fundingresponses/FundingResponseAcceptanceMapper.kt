package com.tradeix.concord.cordapp.supplier.mappers.fundingresponses

import com.tradeix.concord.cordapp.supplier.messages.fundingresponses.FundingResponseConfirmationRequestMessage
import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.mapper.InputAndOutput
import com.tradeix.concord.shared.mapper.Mapper
import com.tradeix.concord.shared.services.VaultService
import net.corda.core.flows.FlowException
import net.corda.core.node.ServiceHub

class FundingResponseAcceptanceMapper(private val serviceHub: ServiceHub)
    : Mapper<FundingResponseConfirmationRequestMessage, InputAndOutput<FundingResponseState>>() {

    override fun map(source: FundingResponseConfirmationRequestMessage): InputAndOutput<FundingResponseState> {

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