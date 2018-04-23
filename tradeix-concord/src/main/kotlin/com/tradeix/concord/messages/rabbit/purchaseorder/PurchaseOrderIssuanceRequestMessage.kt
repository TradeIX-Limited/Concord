package com.tradeix.concord.messages.rabbit.purchaseorder

import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderIssuanceFlowModel
import com.tradeix.concord.messages.AttachmentMessage
import com.tradeix.concord.messages.SingleIdentityMessage
import com.tradeix.concord.messages.rabbit.RabbitRequestMessage
import com.tradeix.concord.states.PurchaseOrderState
import net.corda.core.identity.CordaX500Name
import java.math.BigDecimal
import java.time.Instant

class PurchaseOrderIssuanceRequestMessage(
        override val correlationId: String?,
        override var tryCount: Int,
        override val externalId: String?,
        override val attachmentId: String?,
        val buyer: CordaX500Name?,
        val supplier: CordaX500Name?,
        val conductor: CordaX500Name?,
        val owner: CordaX500Name? = null,
        val reference: String?,
        val value: BigDecimal?,
        val currency: String?,
        val created: Instant?,
        val earliestShipment: Instant?,
        val latestShipment: Instant?,
        val portOfShipment: String?,
        val descriptionOfGoods: String?,
        val deliveryTerms: String?
) : RabbitRequestMessage(), SingleIdentityMessage, AttachmentMessage {

    companion object {
        fun fromState(state: PurchaseOrderState): PurchaseOrderIssuanceRequestMessage {
            return PurchaseOrderIssuanceRequestMessage(
                    correlationId = "",
                    tryCount = 0,
                    externalId = state.linearId.externalId.toString(),
                    attachmentId = "",
                    buyer = state.buyer.nameOrNull(),
                    supplier = state.supplier.nameOrNull(),
                    conductor = state.conductor.nameOrNull(),
                    owner = state.owner.nameOrNull(),
                    reference = state.reference,
                    value = state.amount.toDecimal(),
                    currency = state.amount.token.currencyCode,
                    created = state.created,
                    earliestShipment = state.earliestShipment,
                    latestShipment = state.latestShipment,
                    portOfShipment = state.portOfShipment,
                    descriptionOfGoods = state.descriptionOfGoods,
                    deliveryTerms = state.deliveryTerms
            )
        }
    }

    fun toModel() = PurchaseOrderIssuanceFlowModel(
            externalId,
            attachmentId,
            buyer,
            supplier,
            conductor,
            reference,
            value,
            currency,
            created,
            earliestShipment,
            latestShipment,
            portOfShipment,
            descriptionOfGoods,
            deliveryTerms
    )
}