package com.tradeix.concord.messages.rabbit.purchaseorder

import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderAmendmentFlowModel
import com.tradeix.concord.messages.AttachmentMessage
import com.tradeix.concord.messages.SingleIdentityMessage
import com.tradeix.concord.messages.rabbit.RabbitRequestMessage
import net.corda.core.identity.CordaX500Name
import java.math.BigDecimal
import java.time.Instant

class PurchaseOrderAmendmentRequestMessage(
        override val correlationId: String? = null,
        override var tryCount: Int = 0,
        override val externalId: String? = null,
        override val attachmentId: String? = null,
        val buyer: CordaX500Name? = null,
        val supplier: CordaX500Name? = null,
        val conductor: CordaX500Name? = null,
        val reference: String? = null,
        val value: BigDecimal? = null,
        val currency: String? = null,
        val created: Instant? = null,
        val earliestShipment: Instant? = null,
        val latestShipment: Instant? = null,
        val portOfShipment: String? = null,
        val descriptionOfGoods: String? = null,
        val deliveryTerms: String? = null
) : RabbitRequestMessage(), SingleIdentityMessage, AttachmentMessage {

    fun toModel() = PurchaseOrderAmendmentFlowModel(
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