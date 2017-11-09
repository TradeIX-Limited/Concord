package com.tradeix.concord.serialization

import com.google.gson.GsonBuilder
import net.corda.core.identity.CordaX500Name
import net.corda.core.serialization.serialize
import org.junit.Test
import java.lang.reflect.Type
import kotlin.test.assertEquals

class SerializationTests {

    @Test
    fun `Corda X500 name instance should produce a formatted string`() {
        val message = ConductorTestMessage(CordaX500Name("TradeIX", "London", "GB"))

        val serializer = GsonBuilder()
                .registerTypeAdapter(CordaX500Name::class.java, CordaX500NameSerializer())
                .disableHtmlEscaping()
                .create()

        val json = serializer.toJson(message)

        assertEquals("{\"conductor\":\"C=GB,L=London,O=TradeIX\"}", json)
    }

    @Test
    fun `Formatted Corda X500 name string should produce a CordaX500Name instance`() {
        val json = "{\"conductor\":\"C=GB,L=London,O=TradeIX\"}"

        val serializer = GsonBuilder()
                .registerTypeAdapter(CordaX500Name::class.java, CordaX500NameSerializer())
                .disableHtmlEscaping()
                .create()

        val result = serializer.fromJson<ConductorTestMessage>(json, ConductorTestMessage::class.java)

        assertEquals("GB", result.conductor.country)
        assertEquals("London", result.conductor.locality)
        assertEquals("TradeIX", result.conductor.organisation)
    }

    data class ConductorTestMessage(val conductor: CordaX500Name)
}