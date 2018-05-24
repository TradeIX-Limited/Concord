package com.tradeix.concord.shared.messages.purchaseorders

import com.tradeix.concord.shared.messagecontracts.PurchaseOrderMessage
import net.corda.core.serialization.CordaSerializable
import java.math.BigDecimal
import java.time.Instant

@CordaSerializable
data class PurchaseOrderIssuanceRequestMessage(
        override val externalId: String? = null,
        override val attachmentId: String? = null,
        override val buyer: String? = null,
        override val supplier: String? = null,
        override val conductor: String? = null,
        override val reference: String? = null,
        override val value: BigDecimal? = null,
        override val currency: String? = null,
        override val created: Instant? = null,
        override val earliestShipment: Instant? = null,
        override val latestShipment: Instant? = null,
        override val portOfShipment: String? = null,
        override val descriptionOfGoods: String? = null,
        override val deliveryTerms: String? = null
) : PurchaseOrderMessage