package com.tradeix.concord.shared.extensions

import net.corda.core.contracts.Amount
import java.math.BigDecimal
import java.util.*

fun Amount.Companion.fromValueAndCurrency(value: BigDecimal, currencyCode: String): Amount<Currency> {
    return Amount.fromDecimal(value, Currency.getInstance(currencyCode))
}