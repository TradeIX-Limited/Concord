package com.tradeix.concord.messages.webapi.purchaseorder

import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderIssuanceFlowModel
import com.tradeix.concord.messages.AttachmentMessage
import com.tradeix.concord.messages.SingleIdentityMessage
import net.corda.core.identity.CordaX500Name
import java.math.BigDecimal
import java.time.Instant

class PurchaseOrderIssuanceRequestMessage(
        override val externalId: String?,
        override val attachmentId: String?,
        val buyer: CordaX500Name?,
        val supplier: CordaX500Name?,
        val conductor: CordaX500Name? = CordaX500Name("TradeIX", "London", "GB"),
        val reference: String?,
        val value: BigDecimal?,
        val currency: String?,
        val created: Instant?,
        val earliestShipment: Instant?,
        val latestShipment: Instant?,
        val portOfShipment: String?,
        val descriptionOfGoods: String?,
        val deliveryTerms: String?
) : SingleIdentityMessage, AttachmentMessage {
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