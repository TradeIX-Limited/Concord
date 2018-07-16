package com.tradeix.concord.shared.cordapp.mapping.invoices

import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.extensions.fromValueAndCurrency
import com.tradeix.concord.shared.extensions.tryParse
import com.tradeix.concord.shared.mapper.Mapper
import com.tradeix.concord.shared.messages.invoices.InvoiceRequestMessage
import com.tradeix.concord.shared.models.Participant
import com.tradeix.concord.shared.services.IdentityService
import com.tradeix.concord.shared.services.VaultService
import net.corda.core.contracts.Amount
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.flows.FlowException
import net.corda.core.identity.CordaX500Name
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.Vault
import java.time.LocalDateTime

class InvoiceIssuanceRequestMapper(private val serviceHub: ServiceHub)
    : Mapper<InvoiceRequestMessage, InvoiceState>() {

    override fun map(source: InvoiceRequestMessage): InvoiceState {

        val vaultService = VaultService.fromServiceHub<InvoiceState>(serviceHub)
        val identityService = IdentityService(serviceHub)

        val state = vaultService
                .findByExternalId(source.externalId!!, status = Vault.StateStatus.UNCONSUMED)
                .singleOrNull()

        if (state != null) {
            throw FlowException("An InvoiceState with externalId '${source.externalId}' already exists.")
        }

        val buyer = identityService.getPartyFromLegalNameOrNull(CordaX500Name.tryParse(source.buyer))
        val supplier = identityService.getPartyFromLegalNameOrMe(CordaX500Name.tryParse(source.supplier))

        return InvoiceState(
                linearId = UniqueIdentifier(source.externalId!!),
                owner = supplier,
                buyer = Participant(buyer, source.buyerCompanyReference),
                supplier = Participant(supplier, source.supplierCompanyReference),
                invoiceNumber = source.invoiceNumber!!,
                invoiceVersion = "1.0",
                submitted = LocalDateTime.now(),
                reference = source.reference!!,
                dueDate = source.dueDate!!,
                amount = Amount.fromValueAndCurrency(source.amount!!, source.currency!!),
                totalOutstanding = Amount.fromValueAndCurrency(source.totalOutstanding!!, source.currency!!),
                settlementDate = source.settlementDate!!,
                invoiceDate = source.invoiceDate!!,
                invoicePayments = Amount.fromValueAndCurrency(source.invoicePayments!!, source.currency!!),
                invoiceDilutions = Amount.fromValueAndCurrency(source.invoiceDilutions!!, source.currency!!),
                originationNetwork = source.originationNetwork!!,
                siteId = source.siteId!!,
                tradeDate = source.tradeDate,
                tradePaymentDate = source.tradePaymentDate
        )
    }
}