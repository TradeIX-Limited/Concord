package com.tradeix.concord.shared.messages.purchaseorders

import net.corda.core.serialization.CordaSerializable
import java.math.BigDecimal
import java.time.Instant

@CordaSerializable
data class PurchaseOrderRequestMessage(
        val externalId: String? = null,
        val attachmentId: String? = null,
        val buyer: String? = null,
        val supplier: String? = null,
        val conductor: String? = null,
        val reference: String? = null,
        val value: BigDecimal? = null,
        val currency: String? = null,
        val created: Instant? = null,
        val earliestShipment: Instant? = null,
        val latestShipment: Instant? = null,
        val portOfShipment: String? = null,
        val descriptionOfGoods: String? = null,
        val deliveryTerms: String? = null
)