package com.tradeix.concord.shared.messagecontracts

import java.math.BigDecimal
import java.time.Instant

interface PurchaseOrderMessage {
    val externalId: String?
    val attachmentId: String?
    val buyer: String?
    val supplier: String?
    val conductor: String?
    val reference: String?
    val value: BigDecimal?
    val currency: String?
    val created: Instant?
    val earliestShipment: Instant?
    val latestShipment: Instant?
    val portOfShipment: String?
    val descriptionOfGoods: String?
    val deliveryTerms: String?
}