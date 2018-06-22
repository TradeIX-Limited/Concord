package com.tradeix.concord.shared.cordapp.mapping.fundingresponse

import com.tradeix.concord.shared.domain.enumerations.FundingResponseStatus
import com.tradeix.concord.shared.domain.schemas.FundingResponseSchemaV1.PersistentFundingResponseSchemaV1
import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.mapper.InputAndOutput
import com.tradeix.concord.shared.mapper.Mapper
import com.tradeix.concord.shared.messages.fundingresponse.FundingResponseAcceptMessage
import com.tradeix.concord.shared.services.VaultService
import net.corda.core.flows.FlowException
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.Vault
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.node.services.vault.builder

class FundingResponseAcceptMapper(private val serviceHub: ServiceHub)
    : Mapper<FundingResponseAcceptMessage, InputAndOutput<FundingResponseState>>() {

    override fun map(source: FundingResponseAcceptMessage): InputAndOutput<FundingResponseState> {

        val vaultService = VaultService.fromServiceHub<FundingResponseState>(serviceHub)

        val state = builder {
            val expression = PersistentFundingResponseSchemaV1::linearExternalId.equal(source.fundingResponseExternalId)
            val criteria = QueryCriteria.VaultCustomQueryCriteria(expression, status = Vault.StateStatus.UNCONSUMED)
            vaultService.findByCriteria(criteria).singleOrNull()
        }

        if (state == null) {
            throw FlowException("FundingResponseState with externalId '${source.fundingResponseExternalId}' and that is unconsumed does not exist.")
        } else {
            val outputState = state.state.data.copy(status = FundingResponseStatus.ACCEPTED)
            return InputAndOutput(state, outputState)
        }
    }

}