package com.tradeix.concord.shared.cordapp.mapping.purchaseorders

import com.tradeix.concord.shared.domain.states.PurchaseOrderState
import com.tradeix.concord.shared.extensions.fromValueAndCurrency
import com.tradeix.concord.shared.extensions.tryParse
import com.tradeix.concord.shared.mapper.ServiceHubMapperConfiguration
import com.tradeix.concord.shared.messages.purchaseorders.PurchaseOrderMessage
import com.tradeix.concord.shared.services.IdentityService
import com.tradeix.concord.shared.services.VaultService
import net.corda.core.contracts.Amount
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.flows.FlowException
import net.corda.core.identity.CordaX500Name
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.Vault

class PurchaseOrderIssuanceMapperConfiguration
    : ServiceHubMapperConfiguration<PurchaseOrderMessage, PurchaseOrderState>() {

    override fun map(source: PurchaseOrderMessage, serviceHub: ServiceHub): PurchaseOrderState {

        val vaultService = VaultService.fromServiceHub<PurchaseOrderState>(serviceHub)
        val identityService = IdentityService(serviceHub)

        val state = vaultService
                .findByExternalId(source.externalId!!, status = Vault.StateStatus.UNCONSUMED)
                .singleOrNull()

        if (state != null) {
            throw FlowException("A PurchaseOrderState with '${source.externalId}' already exists.")
        }

        val buyer = identityService.getPartyFromLegalNameOrThrow(CordaX500Name.tryParse(source.buyer))
        val supplier = identityService.getPartyFromLegalNameOrMe(CordaX500Name.tryParse(source.supplier))

        return PurchaseOrderState(
                linearId = UniqueIdentifier(source.externalId!!),
                owner = buyer,
                buyer = buyer,
                supplier = supplier,
                reference = source.reference!!,
                amount = Amount.fromValueAndCurrency(source.value!!, source.currency!!),
                created = source.created!!,
                earliestShipment = source.earliestShipment!!,
                latestShipment = source.latestShipment!!,
                portOfShipment = source.portOfShipment!!,
                descriptionOfGoods = source.descriptionOfGoods!!,
                deliveryTerms = source.deliveryTerms!!
        )
    }
}