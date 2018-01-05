package com.tradeix.concord.flowmodels.tradeasset

import com.tradeix.concord.messages.SingleIdentityMessage
import net.corda.core.contracts.Amount
import net.corda.core.identity.CordaX500Name
import java.math.BigDecimal
import java.util.*

data class TradeAssetIssuanceFlowModel(
        override val externalId: String?,
        val attachmentId: String? = null,
        val buyer: CordaX500Name? = null,
        val supplier: CordaX500Name?,
        val conductor: CordaX500Name?,
        val status: String?,
        val value: BigDecimal?,
        val currency: String?
) : SingleIdentityMessage {
    val amount: Amount<Currency> get() = Amount.fromDecimal(value!!, Currency.getInstance(currency!!))
}