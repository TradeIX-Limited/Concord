package com.tradeix.concord.shared.cordapp.mapping.purchaseorders

import com.tradeix.concord.shared.domain.states.PurchaseOrderState
import com.tradeix.concord.shared.extensions.fromValueAndCurrency
import com.tradeix.concord.shared.extensions.tryParse
import com.tradeix.concord.shared.mapper.ServiceHubMapperConfiguration
import com.tradeix.concord.shared.messages.purchaseorders.PurchaseOrderMessage
import com.tradeix.concord.shared.services.IdentityService
import com.tradeix.concord.shared.services.VaultService
import net.corda.core.contracts.Amount
import net.corda.core.flows.FlowException
import net.corda.core.identity.CordaX500Name
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.Vault

class PurchaseOrderAmendmentMapperConfiguration
    : ServiceHubMapperConfiguration<PurchaseOrderMessage, PurchaseOrderState>() {

    override fun map(source: PurchaseOrderMessage, serviceHub: ServiceHub): PurchaseOrderState {

        val vaultService = VaultService.fromServiceHub<PurchaseOrderState>(serviceHub)
        val identityService = IdentityService(serviceHub)

        val inputState = vaultService
                .findByExternalId(source.externalId!!, status = Vault.StateStatus.UNCONSUMED)
                .singleOrNull()

        if (inputState == null) {
            throw FlowException("PurchaseOrderState with externalId '${source.externalId}' does not exist.")
        } else {

            val buyer = identityService.getPartyFromLegalNameOrThrow(CordaX500Name.tryParse(source.buyer))
            val supplier = identityService.getPartyFromLegalNameOrMe(CordaX500Name.tryParse(source.supplier))

            return inputState.state.data.copy(
                    owner = buyer,
                    buyer = buyer,
                    supplier = supplier,
                    reference = source.reference!!,
                    amount = Amount.fromValueAndCurrency(source.value!!, source.currency!!),
                    earliestShipment = source.earliestShipment!!,
                    latestShipment = source.latestShipment!!,
                    portOfShipment = source.portOfShipment!!,
                    descriptionOfGoods = source.descriptionOfGoods!!,
                    deliveryTerms = source.deliveryTerms!!
            )
        }
    }
}