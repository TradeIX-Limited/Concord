package com.tradeix.concord.model

import net.corda.core.contracts.Amount
import net.corda.core.serialization.CordaSerializable
import java.util.*

@CordaSerializable
data class PurchaseOrder(val amount: Amount<Currency>)