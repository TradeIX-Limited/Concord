package com.tradeix.concord.shared.mockdata

import net.corda.core.contracts.Amount
import java.math.BigDecimal
import java.util.*

object MockAmounts {

    val ZERO_POUNDS = Amount.fromDecimal(BigDecimal.ZERO, Currency.getInstance("GBP"))
    val ONE_POUNDS = Amount.fromDecimal(BigDecimal.ONE, Currency.getInstance("GBP"))
    val TEN_POUNDS = Amount.fromDecimal(BigDecimal.TEN, Currency.getInstance("GBP"))

    val ZERO_DOLLARS = Amount.fromDecimal(BigDecimal.ZERO, Currency.getInstance("USD"))
    val ONE_DOLLARS = Amount.fromDecimal(BigDecimal.ONE, Currency.getInstance("USD"))
    val TEN_DOLLARS = Amount.fromDecimal(BigDecimal.TEN, Currency.getInstance("USD"))

    val ZERO_EUROS = Amount.fromDecimal(BigDecimal.ZERO, Currency.getInstance("EUR"))
    val ONE_EUROS = Amount.fromDecimal(BigDecimal.ONE, Currency.getInstance("EUR"))
    val TEN_EUROS = Amount.fromDecimal(BigDecimal.TEN, Currency.getInstance("EUR"))

}