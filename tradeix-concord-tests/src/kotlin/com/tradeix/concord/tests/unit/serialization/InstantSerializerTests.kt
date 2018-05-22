package com.tradeix.concord.tests.unit.serialization

import com.google.gson.GsonBuilder
import com.tradeix.concord.shared.extensions.getConfiguredSerializer
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.test.assertEquals

@RunWith(Parameterized::class)
class InstantSerializerTests(private val candidate: Instant, private val expected: String) {

    companion object {
        @JvmStatic
        @Parameters
        fun data(): Collection<Array<Any>> {
            return listOf(
                    arrayOf(
                            Instant.EPOCH,
                            "\"1970-01-01T00:00:00Z\""
                    ),
                    arrayOf(
                            LocalDateTime.of(2000, 1, 1, 0, 0, 0, 500).toInstant(ZoneOffset.UTC),
                            "\"2000-01-01T00:00:00.000000500Z\""
                    ),
                    arrayOf(
                            LocalDateTime.of(2018, 5, 9, 15, 11, 50, 249056).toInstant(ZoneOffset.UTC),
                            "\"2018-05-09T15:11:50.000249056Z\""
                    )
            )
        }
    }

    @Test
    fun `Instant instance should serialize into a valid JSON string`() {
        // Arrange
        val systemUnderTest = GsonBuilder().getConfiguredSerializer()

        // Act
        val actual = systemUnderTest.toJson(candidate)

        // Assert
        assertEquals(expected, actual)
    }
}