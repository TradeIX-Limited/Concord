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
import net.corda.core.utilities.loggerFor
import org.slf4j.Logger

class FundingResponseIssuanceMapper(private val serviceHub: ServiceHub)
    : Mapper<FundingResponseIssuanceRequestMessage, FundingResponseState>() {

    companion object {
        private val logger: Logger = loggerFor<FundingResponseIssuanceMapper>()
    }

    override fun map(source: FundingResponseIssuanceRequestMessage): FundingResponseState {

        val invoiceVaultService = VaultService.fromServiceHub<InvoiceState>(serviceHub)
        val fundingResponseVaultService = VaultService.fromServiceHub<FundingResponseState>(serviceHub)
        val identityService = IdentityService(serviceHub)

        logger.info("Querying vault for FundingResponseState with externalId '${source.externalId!!}.")

        val state = fundingResponseVaultService
                .findByExternalId(source.externalId, status = Vault.StateStatus.UNCONSUMED)
                .singleOrNull()

        if (state != null) {
            throw FlowException("A FundingResponseState with externalId '${source.externalId}' already exists.")
        }

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
                // TODO : This should be mapped to an existing funding request from the vault.
                fundingRequestLinearId = UniqueIdentifier(externalId = source.fundingRequestExternalId),
                invoiceLinearIds = invoiceLinearIds,
                supplier = supplier,
                funder = funder,
                purchaseValue = Amount.fromValueAndCurrency(source.purchaseValue!!, source.currency!!),
                status = FundingResponseStatus.PENDING,
                advanceInvoiceValue = Amount.fromValueAndCurrency(source.advanceInvoiceValue!!, source.currency),
                discountValue = Amount.fromValueAndCurrency(source.discountValue!!, source.currency),
                baseRate = source.baseRate!!,
                bankAccount = null
        )
    }
}