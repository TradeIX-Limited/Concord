package com.tradeix.concord.shared.cordapp.mapping.fundingResponse

import com.tradeix.concord.shared.domain.enumerations.FundingResponseStatus
import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.extensions.fromValueAndCurrency
import com.tradeix.concord.shared.extensions.tryParse
import com.tradeix.concord.shared.mapper.Mapper
import com.tradeix.concord.shared.messages.fundingResponse.FundingResponseMessage
import com.tradeix.concord.shared.services.IdentityService
import com.tradeix.concord.shared.services.VaultService
import net.corda.core.contracts.Amount
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.flows.FlowException
import net.corda.core.identity.CordaX500Name
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.Vault

class FundingResponseMapper(private val serviceHub: ServiceHub)
    : Mapper<FundingResponseMessage, FundingResponseState>() {

    override fun map(source: FundingResponseMessage): FundingResponseState {

        val vaultService = VaultService.fromServiceHub<FundingResponseState>(serviceHub)
        val identityService = IdentityService(serviceHub)

        val state = vaultService
                .findByExternalId(source.externalId, status = Vault.StateStatus.UNCONSUMED)
                .singleOrNull()

        if (state != null) {
            throw FlowException("An FundingResponseState with externalId '${source.externalId}' already exists.")
        }

        val funder = identityService.getPartyFromLegalNameOrThrow(CordaX500Name.tryParse(source.funder))
        val supplier = identityService.getPartyFromLegalNameOrMe(CordaX500Name.tryParse(source.supplier))

        return FundingResponseState(
                linearId = UniqueIdentifier(source.externalId),
               // fundingRequestId= UniqueIdentifier(source.externalId!!)? = null,
                invoiceNumber =  source.invoiceNumber, //TODO : List of Invoices
                supplier =  supplier,
                funder = funder,
                purchaseValue = Amount.fromValueAndCurrency(source.purchaseValue, source.currency),//Price
                status =  FundingResponseStatus.valueOf(source.status)
        )
    }
}