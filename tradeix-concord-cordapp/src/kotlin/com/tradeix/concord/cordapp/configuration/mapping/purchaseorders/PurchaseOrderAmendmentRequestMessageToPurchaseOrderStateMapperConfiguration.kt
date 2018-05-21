package com.tradeix.concord.cordapp.configuration.mapping.purchaseorders

import com.tradeix.concord.shared.data.VaultRepository
import com.tradeix.concord.shared.domain.states.PurchaseOrderState
import com.tradeix.concord.shared.extensions.*
import com.tradeix.concord.shared.mapper.MapperConfiguration
import com.tradeix.concord.shared.messages.purchaseorders.PurchaseOrderAmendmentRequestMessage
import net.corda.core.contracts.Amount
import net.corda.core.flows.FlowException
import net.corda.core.identity.CordaX500Name
import net.corda.core.node.ServiceHub
import net.corda.core.node.services.Vault

class PurchaseOrderAmendmentRequestMessageToPurchaseOrderStateMapperConfiguration
    : MapperConfiguration<PurchaseOrderAmendmentRequestMessage, PurchaseOrderState>() {

    override fun map(source: PurchaseOrderAmendmentRequestMessage, serviceHub: ServiceHub): PurchaseOrderState {

        val repository = VaultRepository.fromServiceHub<PurchaseOrderState>(serviceHub)

        val inputState = repository
                .findByExternalId(source.externalId!!, Vault.StateStatus.UNCONSUMED)
                .singleOrNull()

        if (inputState == null) {
            throw FlowException("PurchaseOrderState with externalId '${source.externalId}' does not exist.")
        } else {

            val buyer = serviceHub
                    .networkMapCache
                    .getPartyFromLegalNameOrThrow(
                            CordaX500Name.tryParse(source.buyer)
                    )

            val supplier = serviceHub
                    .networkMapCache
                    .getPartyFromLegalNameOrMe(
                            serviceHub,
                            CordaX500Name.tryParse(source.supplier)
                    )

            val conductor = serviceHub
                    .networkMapCache
                    .getPartyFromLegalNameOrDefault(
                            CordaX500Name.tryParse(source.conductor),
                            CordaX500Name.defaultConductor
                    )

            return inputState.state.data.copy(
                    owner = buyer,
                    buyer = buyer,
                    supplier = supplier,
                    conductor = conductor,
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
}