package com.tradeix.concord.cordapp.supplier.mappers.fundingresponses

import com.tradeix.concord.cordapp.supplier.messages.fundingresponses.FundingResponseConfirmationRequestMessage
import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.mapper.InputAndOutput
import com.tradeix.concord.shared.mapper.Mapper
import com.tradeix.concord.shared.services.VaultService
import net.corda.core.flows.FlowException
import net.corda.core.node.ServiceHub
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger

class FundingResponseRejectionMapper(private val serviceHub: ServiceHub)
    : Mapper<FundingResponseConfirmationRequestMessage, InputAndOutput<FundingResponseState>>() {

    companion object {
        private val logger: Logger = loggerFor<FundingResponseRejectionMapper>()
    }

    override fun map(source: FundingResponseConfirmationRequestMessage): InputAndOutput<FundingResponseState> {

        val vaultService = VaultService.fromServiceHub<FundingResponseState>(serviceHub)

        val inputState = vaultService
                .findByExternalId(source.externalId!!)
                .singleOrNull()

        logger.info("INPUT STATE"+inputState.toString())

        if (inputState == null) {
            logger.info("ANYTHING")
            throw FlowException("A FundingResponseState with externalId '${source.externalId}' does not exist.")
        } else {
            val outputState = inputState.state.data.reject()
            return InputAndOutput(inputState, outputState)
        }
    }
}