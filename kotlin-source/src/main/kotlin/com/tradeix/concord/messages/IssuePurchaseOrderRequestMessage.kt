package com.tradeix.concord.messages

import net.corda.core.contracts.Amount
import net.corda.core.identity.CordaX500Name
import java.math.BigDecimal
import java.util.*

data class IssuePurchaseOrderRequestMessage(
        val amount: BigDecimal?,
        val currency: String?,
        val supplier: CordaX500Name?)