package com.tradeix.concord.messages

import net.corda.core.contracts.Amount
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.CordaX500Name
import java.math.BigDecimal
import java.util.*

data class TradeAssetIssuanceRequestMessage(
        override val correlationId: String?,
        override val tryCount: Int,
        val externalId: String?,
        val buyer: CordaX500Name?,
        val supplier: CordaX500Name?,
        val conductor: CordaX500Name = CordaX500Name("TradeIX", "London", "GB"),
        val status: String?,
        val value: BigDecimal?,
        val currency: String?,
        val attachmentId: String?
) : Message() {
    val linearId: UniqueIdentifier get() = UniqueIdentifier(externalId!!)
    val amount: Amount<Currency> get() = Amount.fromDecimal(value!!, Currency.getInstance(currency!!))
}