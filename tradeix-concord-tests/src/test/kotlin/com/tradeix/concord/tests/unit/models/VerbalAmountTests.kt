package com.tradeix.concord.tests.unit.models

import com.tradeix.concord.shared.domain.models.VerbalAmount
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import java.math.BigDecimal
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class VerbalAmountTests(private val candidate: BigDecimal, private val expected: String) {

    companion object {
        @JvmStatic
        @Parameters
        fun data(): Collection<Array<Any>> {
            return listOf(
                    arrayOf(BigDecimal(100).negate(), "Negative One Hundred"),
                    arrayOf(BigDecimal(25).negate(), "Negative Twenty Five"),
                    arrayOf(BigDecimal(10).negate(), "Negative Ten"),
                    arrayOf(BigDecimal(1).negate(), "Negative One"),
                    arrayOf(BigDecimal(0), "Zero"),
                    arrayOf(BigDecimal(1), "One"),
                    arrayOf(BigDecimal(2), "Two"),
                    arrayOf(BigDecimal(3), "Three"),
                    arrayOf(BigDecimal(4), "Four"),
                    arrayOf(BigDecimal(5), "Five"),
                    arrayOf(BigDecimal(6), "Six"),
                    arrayOf(BigDecimal(7), "Seven"),
                    arrayOf(BigDecimal(8), "Eight"),
                    arrayOf(BigDecimal(9), "Nine"),
                    arrayOf(BigDecimal(10), "Ten"),
                    arrayOf(BigDecimal(11), "Eleven"),
                    arrayOf(BigDecimal(12), "Twelve"),
                    arrayOf(BigDecimal(13), "Thirteen"),
                    arrayOf(BigDecimal(14), "Fourteen"),
                    arrayOf(BigDecimal(15), "Fifteen"),
                    arrayOf(BigDecimal(16), "Sixteen"),
                    arrayOf(BigDecimal(17), "Seventeen"),
                    arrayOf(BigDecimal(18), "Eighteen"),
                    arrayOf(BigDecimal(19), "Nineteen"),
                    arrayOf(BigDecimal(20), "Twenty"),
                    arrayOf(BigDecimal(21), "Twenty One"),
                    arrayOf(BigDecimal(30), "Thirty"),
                    arrayOf(BigDecimal(31), "Thirty One"),
                    arrayOf(BigDecimal(40), "Forty"),
                    arrayOf(BigDecimal(41), "Forty One"),
                    arrayOf(BigDecimal(50), "Fifty"),
                    arrayOf(BigDecimal(51), "Fifty One"),
                    arrayOf(BigDecimal(60), "Sixty"),
                    arrayOf(BigDecimal(61), "Sixty One"),
                    arrayOf(BigDecimal(70), "Seventy"),
                    arrayOf(BigDecimal(71), "Seventy One"),
                    arrayOf(BigDecimal(80), "Eighty"),
                    arrayOf(BigDecimal(81), "Eighty One"),
                    arrayOf(BigDecimal(90), "Ninety"),
                    arrayOf(BigDecimal(91), "Ninety One"),
                    arrayOf(BigDecimal(100), "One Hundred"),
                    arrayOf(BigDecimal(101), "One Hundred And One"),
                    arrayOf(BigDecimal(110), "One Hundred And Ten"),
                    arrayOf(BigDecimal(111), "One Hundred And Eleven"),
                    arrayOf(BigDecimal(121), "One Hundred And Twenty One"),
                    arrayOf(BigDecimal(1000), "One Thousand"),
                    arrayOf(BigDecimal(1001), "One Thousand And One"),
                    arrayOf(BigDecimal(1100), "One Thousand One Hundred"),
                    arrayOf(BigDecimal(1101), "One Thousand One Hundred And One"),
                    arrayOf(BigDecimal(1110), "One Thousand One Hundred And Ten")
            )
        }
    }

    @Test
    fun `BigDecimal should produce the correct verbal amount`() {
        // Arrange
        val systemUnderTest = VerbalAmount(candidate)

        // Act
        val actual = systemUnderTest.getVerbalAmountString()

        // Assert
        assertEquals(expected, actual)
    }
}