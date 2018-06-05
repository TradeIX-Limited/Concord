package com.tradeix.concord.shared.cordapp.mapping.invoices

import com.tradeix.concord.shared.data.VaultRepository
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.extensions.fromValueAndCurrency
import com.tradeix.concord.shared.extensions.getPartyFromLegalNameOrMe
import com.tradeix.concord.shared.extensions.getPartyFromLegalNameOrNull
import com.tradeix.concord.shared.extensions.tryParse
import com.tradeix.concord.shared.mapper.ServiceHubMapperConfiguration
import com.tradeix.concord.shared.messages.invoices.InvoiceRequestMessage
import net.corda.core.contracts.Amount
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.flows.FlowException
import net.corda.core.identity.CordaX500Name
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.Vault

class InvoiceIssuanceMapperConfiguration
    : ServiceHubMapperConfiguration<InvoiceRequestMessage, InvoiceState>() {

    override fun map(source: InvoiceRequestMessage, serviceHub: ServiceHub): InvoiceState {

        val repository = VaultRepository.fromServiceHub<InvoiceState>(serviceHub)

        val state = repository
                .findByExternalId(source.externalId!!, status = Vault.StateStatus.UNCONSUMED)
                .singleOrNull()

        if (state != null) {
            throw FlowException("An InvoiceState with '${source.externalId}' already exists.")
        }

        val buyer = serviceHub.networkMapCache.getPartyFromLegalNameOrNull(
                CordaX500Name.tryParse(source.buyer)
        )

        val supplier = serviceHub.networkMapCache.getPartyFromLegalNameOrMe(
                serviceHub,
                CordaX500Name.tryParse(source.supplier)
        )

        return InvoiceState(
                linearId = UniqueIdentifier(source.externalId!!),
                owner = supplier,
                buyer = buyer,
                supplier = supplier,
                invoiceNumber = source.invoiceNumber!!,
                reference = source.reference!!,
                dueDate = source.dueDate!!,
                amount = Amount.fromValueAndCurrency(source.amount!!, source.currency!!),
                totalOutstanding = Amount.fromValueAndCurrency(source.totalOutstanding!!, source.currency!!),
                settlementDate = source.settlementDate!!,
                invoiceDate = source.invoiceDate!!,
                invoicePayments = Amount.fromValueAndCurrency(source.invoicePayments!!, source.currency!!),
                invoiceDilutions = Amount.fromValueAndCurrency(source.invoiceDilutions!!, source.currency!!),
                originationNetwork = source.originationNetwork!!,
                siteId = source.siteId!!
        )
    }
}