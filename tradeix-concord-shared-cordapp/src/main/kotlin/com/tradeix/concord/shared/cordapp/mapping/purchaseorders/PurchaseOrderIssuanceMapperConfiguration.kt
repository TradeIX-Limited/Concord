package com.tradeix.concord.shared.cordapp.mapping.purchaseorders

import com.tradeix.concord.shared.data.VaultRepository
import com.tradeix.concord.shared.domain.states.PurchaseOrderState
import com.tradeix.concord.shared.extensions.*
import com.tradeix.concord.shared.mapper.MapperConfiguration
import com.tradeix.concord.shared.mapper.ServiceHubMapperConfiguration
import com.tradeix.concord.shared.messages.purchaseorders.PurchaseOrderRequestMessage
import net.corda.core.contracts.Amount
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.flows.FlowException
import net.corda.core.identity.CordaX500Name
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.Vault

class PurchaseOrderIssuanceMapperConfiguration
    : ServiceHubMapperConfiguration<PurchaseOrderRequestMessage, PurchaseOrderState>() {

    override fun map(source: PurchaseOrderRequestMessage, serviceHub: ServiceHub): PurchaseOrderState {

        val repository = VaultRepository.fromServiceHub<PurchaseOrderState>(serviceHub)

        val state = repository
                .findByExternalId(source.externalId!!, status = Vault.StateStatus.UNCONSUMED)
                .singleOrNull()

        if (state != null) {
            throw FlowException("A PurchaseOrderState with '${source.externalId}' already exists.")
        }

        val buyer = serviceHub.networkMapCache.getPartyFromLegalNameOrThrow(
                CordaX500Name.tryParse(source.buyer)
        )

        val supplier = serviceHub.networkMapCache.getPartyFromLegalNameOrMe(
                serviceHub,
                CordaX500Name.tryParse(source.supplier)
        )

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