package com.tradeix.concord.flowmodels.purchaseorder

import com.tradeix.concord.messages.SingleIdentityMessage
import net.corda.core.contracts.Amount
import net.corda.core.identity.CordaX500Name
import java.math.BigDecimal
import java.time.Instant
import java.util.*

data class PurchaseOrderAmendmentFlowModel(
        override val externalId: String?,
        val attachmentId: String? = null,
        val buyer: CordaX500Name? = null,
        val supplier: CordaX500Name?,
        val reference: String?,
        val value: BigDecimal?,
        val currency: String?,
        val created: Instant?,
        val earliestShipment: Instant?,
        val latestShipment: Instant?,
        val portOfShipment: String?,
        val descriptionOfGoods: String?,
        val deliveryTerms: String?
) : SingleIdentityMessage {
    val amount: Amount<Currency> get() = Amount.fromDecimal(value!!, Currency.getInstance(currency!!))
}