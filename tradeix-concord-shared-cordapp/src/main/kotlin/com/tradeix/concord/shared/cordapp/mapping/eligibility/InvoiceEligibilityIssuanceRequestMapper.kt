package com.tradeix.concord.shared.cordapp.mapping.eligibility

import com.tradeix.concord.shared.domain.schemas.InvoiceEligibilitySchemaV1.PersistentInvoiceEligibilitySchemaV1
import com.tradeix.concord.shared.domain.states.InvoiceEligibilityState
import com.tradeix.concord.shared.extensions.tryParse
import com.tradeix.concord.shared.mapper.ServiceHubMapper
import com.tradeix.concord.shared.messages.InvoiceEligibilityRequestMessage
import com.tradeix.concord.shared.services.IdentityService
import com.tradeix.concord.shared.services.VaultService
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.flows.FlowException
import net.corda.core.identity.CordaX500Name
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.Vault
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.node.services.vault.builder

class InvoiceEligibilityIssuanceRequestMapper
    : ServiceHubMapper<InvoiceEligibilityRequestMessage, InvoiceEligibilityState>() {

    override fun map(source: InvoiceEligibilityRequestMessage, serviceHub: ServiceHub): InvoiceEligibilityState {

        val vaultService = VaultService.fromServiceHub<InvoiceEligibilityState>(serviceHub)
        val identityService = IdentityService(serviceHub)

        val state = builder {
            val expression = PersistentInvoiceEligibilitySchemaV1::invoiceExternalId.equal(source.invoiceId)
            val criteria = QueryCriteria.VaultCustomQueryCriteria(expression, status = Vault.StateStatus.UNCONSUMED)
            vaultService.findByCriteria(criteria).singleOrNull()
        }

        if (state != null) {
            throw FlowException("An InvoiceEligibilityState with invoiceId '${source.invoiceId}' already exists.")
        } else {
            return InvoiceEligibilityState(
                    linearId = UniqueIdentifier(),
                    invoiceExternalId = source.invoiceId!!,
                    supplier = identityService.getPartyFromLegalNameOrThrow(CordaX500Name.tryParse(source.supplier)),
                    funder = identityService.getPartyFromLegalNameOrMe(null),
                    eligible = source.eligible!!
            )
        }
    }
}