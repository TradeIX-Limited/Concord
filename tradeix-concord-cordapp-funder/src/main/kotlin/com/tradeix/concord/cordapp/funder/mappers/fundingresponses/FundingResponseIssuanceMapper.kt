package com.tradeix.concord.cordapp.funder.mappers.fundingresponses

import com.tradeix.concord.cordapp.funder.messages.fundingresponses.FundingResponseIssuanceRequestMessage
import com.tradeix.concord.shared.domain.enumerations.FundingResponseStatus
import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.extensions.fromValueAndCurrency
import com.tradeix.concord.shared.extensions.tryParse
import com.tradeix.concord.shared.mapper.Mapper
import com.tradeix.concord.shared.services.IdentityService
import com.tradeix.concord.shared.services.VaultService
import net.corda.core.contracts.Amount
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.flows.FlowException
import net.corda.core.identity.CordaX500Name
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.Vault

class FundingResponseIssuanceMapper(private val serviceHub: ServiceHub)
    : Mapper<FundingResponseIssuanceRequestMessage, FundingResponseState>() {

    override fun map(source: FundingResponseIssuanceRequestMessage): FundingResponseState {

        val invoiceVaultService = VaultService.fromServiceHub<InvoiceState>(serviceHub)
        val fundingResponseVaultService = VaultService.fromServiceHub<FundingResponseState>(serviceHub)
        val identityService = IdentityService(serviceHub)

        val state = fundingResponseVaultService
                .findByExternalId(source.externalId!!, status = Vault.StateStatus.UNCONSUMED)
                .singleOrNull()

        if (state != null) {
            throw FlowException("A FundingResponseState with externalId '${source.externalId}' already exists.")
        }

        // TODO : Add checks to find existing funding request.

        val invoiceLinearIds: MutableCollection<UniqueIdentifier> = mutableListOf()

        source.invoiceExternalIds!!.forEach {
            val invoiceStateAndRef = invoiceVaultService
                    .findByExternalId(it, status = Vault.StateStatus.UNCONSUMED)
                    .singleOrNull() ?: throw FlowException("Could not find an InvoiceState with externalId '$it'.")

            invoiceLinearIds.add(invoiceStateAndRef.state.data.linearId)
        }

        val supplier = identityService.getPartyFromLegalNameOrThrow(CordaX500Name.tryParse(source.supplier))
        val funder = identityService.getPartyFromLegalNameOrMe(CordaX500Name.tryParse(source.funder))

        return FundingResponseState(
                linearId = UniqueIdentifier(source.externalId),
                fundingRequestLinearId = null, // TODO : See above and use this when implemented
                invoiceLinearIds = invoiceLinearIds,
                supplier = supplier,
                funder = funder,
                purchaseValue = Amount.fromValueAndCurrency(source.purchaseValue!!, source.currency!!),
                status = FundingResponseStatus.PENDING,
                advanceInvoiceValue = Amount.fromValueAndCurrency(source.advanceInvoiceValue!!, source.currency),
                discountValue = Amount.fromValueAndCurrency(source.discountValue!!, source.currency),
                baseRate = source.baseRate!!

        )
    }
}