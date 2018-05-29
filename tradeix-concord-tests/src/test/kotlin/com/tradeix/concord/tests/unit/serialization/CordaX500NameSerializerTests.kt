package com.tradeix.concord.tests.unit.serialization

import com.google.gson.GsonBuilder
import com.tradeix.concord.shared.extensions.getConfiguredSerializer
import net.corda.core.identity.CordaX500Name
import org.junit.Test
import kotlin.test.assertEquals

class CordaX500NameSerializerTests {

    @Test
    fun `CordaX500Name instance should serialize into a valid JSON string`() {
        // Arrange
        val candidate = CordaX500Name("TradeIX", "London", "GB")
        val systemUnderTest = GsonBuilder().getConfiguredSerializer()
        val expected = "\"O=TradeIX, L=London, C=GB\""

        // Act
        val actual = systemUnderTest.toJson(candidate)

        // Assert
        assertEquals(expected, actual)
    }

    @Test
    fun `JSON string should deserialize into a valid CordaX500Name instance`() {
        // Arrange
        val candidate = "\"O=TradeIX, L=London, C=GB\""
        val systemUnderTest = GsonBuilder().getConfiguredSerializer()
        val expected = CordaX500Name("TradeIX", "London", "GB")

        // Act
        val actual = systemUnderTest.fromJson<CordaX500Name>(candidate, CordaX500Name::class.java)

        // Assert
        assertEquals(expected, actual)
    }
}