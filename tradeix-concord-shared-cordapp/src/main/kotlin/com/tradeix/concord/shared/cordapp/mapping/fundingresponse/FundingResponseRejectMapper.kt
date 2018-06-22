package com.tradeix.concord.shared.cordapp.mapping.fundingresponse

import com.tradeix.concord.shared.domain.enumerations.FundingResponseStatus
import com.tradeix.concord.shared.domain.schemas.FundingResponseSchemaV1
import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.mapper.InputAndOutput
import com.tradeix.concord.shared.mapper.Mapper
import com.tradeix.concord.shared.messages.fundingresponse.FundingResponseRejectMessage
import com.tradeix.concord.shared.services.VaultService
import net.corda.core.flows.FlowException
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.Vault
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.node.services.vault.builder

class FundingResponseRejectMapper(private val serviceHub: ServiceHub)
    : Mapper<FundingResponseRejectMessage, InputAndOutput<FundingResponseState>>() {

    override fun map(source: FundingResponseRejectMessage): InputAndOutput<FundingResponseState> {

        val vaultService = VaultService.fromServiceHub<FundingResponseState>(serviceHub)

        val state = builder {
            val expression = FundingResponseSchemaV1.PersistentFundingResponseSchemaV1::linearExternalId.equal(source.fundingResponseExternalId)
            val criteria = QueryCriteria.VaultCustomQueryCriteria(expression, status = Vault.StateStatus.UNCONSUMED)
            vaultService.findByCriteria(criteria).singleOrNull()
        }

        if (state == null) {
            throw FlowException("Funding Response State with externalId '${source.externalId}' that is unconsumed does not exist.")
        } else {
            val outputState = state.state.data.copy(status = FundingResponseStatus.REJECTED)
            return InputAndOutput(state, outputState)
        }
    }

}