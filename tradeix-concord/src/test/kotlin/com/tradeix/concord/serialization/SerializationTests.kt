package com.tradeix.concord.serialization

import com.google.gson.GsonBuilder
import com.tradeix.concord.messages.rabbit.purchaseorder.PurchaseOrderIssuanceRequestMessage
import net.corda.core.identity.CordaX500Name
import org.junit.Test
import kotlin.test.assertEquals

class SerializationTests {

    @Test
    fun `CordaX500Name instance serializes into a valid JSON string`() {
        val message = ConductorTestMessage(CordaX500Name("TradeIX", "London", "GB"))

        val serializer = GsonBuilder()
                .registerTypeAdapter(CordaX500Name::class.java, CordaX500NameSerializer())
                .disableHtmlEscaping()
                .create()

        val json = serializer.toJson(message)

        assertEquals("{\"conductor\":\"C=GB,L=London,O=TradeIX\"}", json)
    }

    @Test
    fun `JSON string de-serializes into a valid CordaX500Name instance`() {
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

    @Test
    fun `Corda X500Name should deserialize as part of TradeAssetIssuanceRequestMessage`() {
        val testRequestMessageJson = "{\"externalId\":\"1\",\"buyer\":\"CN=Buyer One, OU=ABC, O=Buyer1, L=London, S=Whitehall, C=GB\",\"supplier\":\"CN=Supplier One, OU=ABC, O=Supplier1, L=London, S=Whitehall, C=GB\",\"conductor\":\"CN=Conductor One, OU=ABC, O=Conductor1, L=London, S=Whitehall, C=GB\",\"status\":\"Test\",\"value\":100.0,\"currency\":\"GBP\",\"attachmentId\":\"c026064d-1c4d-44d8-8932-d47205a7f863\",\"correlationId\":\"fe3f3cd7-4ab3-41d8-8b5d-705667b8e7c1\",\"tryCount\":0}"

        val serializer = GsonBuilder()
                .registerTypeAdapter(CordaX500Name::class.java, CordaX500NameSerializer())
                .disableHtmlEscaping()
                .create()

        val result = serializer
                .fromJson<PurchaseOrderIssuanceRequestMessage>(
                        testRequestMessageJson,
                        PurchaseOrderIssuanceRequestMessage::class.java)

        assertEquals("London", result.buyer?.locality)
    }


    data class ConductorTestMessage(val conductor: CordaX500Name)
}