package com.tradeix.concord.shared.messages.purchaseorders

import net.corda.core.serialization.CordaSerializable
import java.math.BigDecimal
import java.time.LocalDateTime

@CordaSerializable
data class PurchaseOrderRequestMessage(
        val externalId: String? = null,
        val buyer: String? = null,
        val supplier: String? = null,
        val reference: String? = null,
        val value: BigDecimal? = null,
        val currency: String? = null,
        val created: LocalDateTime? = null,
        val earliestShipment: LocalDateTime? = null,
        val latestShipment: LocalDateTime? = null,
        val portOfShipment: String? = null,
        val descriptionOfGoods: String? = null,
        val deliveryTerms: String? = null
)