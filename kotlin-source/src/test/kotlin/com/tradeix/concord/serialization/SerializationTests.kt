package com.tradeix.concord.serialization

import com.google.gson.GsonBuilder
import net.corda.core.identity.CordaX500Name
import org.junit.Test
import java.lang.reflect.Type
import kotlin.test.assertEquals

class SerializationTests {

    @Test
    fun `Formatted Corda X500 name string should produce a CordaX500Name instance`() {
        val json = "{\"conductor\":\"O=TradeIX, L=London, C=GB\"}"

        val serializer = GsonBuilder()
                .registerTypeAdapter(CordaX500Name::class.java, CordaX500NameDeserializer())
                .create()

        val result = serializer.fromJson<ConductorTestMessage>(json, ConductorTestMessage::class.java)

        assertEquals("GB", result.conductor.country)
        assertEquals("London", result.conductor.locality)
        assertEquals("TradeIX", result.conductor.organisation)
    }

    data class ConductorTestMessage(val conductor: CordaX500Name)
}