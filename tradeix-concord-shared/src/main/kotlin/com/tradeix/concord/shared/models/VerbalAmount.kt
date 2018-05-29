package com.tradeix.concord.shared.domain.models

import java.math.BigDecimal

class VerbalAmount(private val amount: BigDecimal) {
    private val units = listOf(
            "Zero",
            "One",
            "Two",
            "Three",
            "Four",
            "Five",
            "Six",
            "Seven",
            "Eight",
            "Nine",
            "Ten",
            "Eleven",
            "Twelve",
            "Thirteen",
            "Fourteen",
            "Fifteen",
            "Sixteen",
            "Seventeen",
            "Eighteen",
            "Nineteen"
    )

    private val tens = listOf(
            "Zero",
            "Ten",
            "Twenty",
            "Thirty",
            "Forty",
            "Fifty",
            "Sixty",
            "Seventy",
            "Eighty",
            "Ninety"
    )

    fun getVerbalAmountString(): String = numberToWords(amount)

    private fun numberToWords(value: BigDecimal): String {
        val magnitude = mapOf(
                "Million" to BigDecimal(1000000),
                "Thousand" to BigDecimal(1000),
                "Hundred" to BigDecimal(100)
        )

        var number = value.setScale(2)
        val words = ArrayList<String>()

        if (number == BigDecimal.ZERO.setScale(2)) {
            return units[0]
        }

        if (number < BigDecimal.ZERO.setScale(2)) {
            return "Negative " + numberToWords(number.negate())
        }

        magnitude.forEach {
            if ((number / it.value).toInt() > 0) {
                words.add(numberToWords(number / it.value))
                words.add(it.key)
                number %= it.value
            }
        }

        if (number > BigDecimal.ZERO) {
            if (words.isNotEmpty()) {
                words.add("And")
            }

            if (number < BigDecimal(20)) {
                words.add(units[number.toInt()])
            } else {
                words.add(tens[number.toInt() / 10])
                if ((number % BigDecimal.TEN) > BigDecimal.ZERO) {
                    words.add(units[number.toInt() % 10])
                }
            }
        }

        return words.joinToString(separator = " ")
    }
}