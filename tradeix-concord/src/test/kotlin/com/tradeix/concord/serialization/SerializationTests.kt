package com.tradeix.concord.serialization

import com.google.common.base.Predicates.instanceOf
import com.google.gson.GsonBuilder
import com.tradeix.concord.messages.rabbit.invoice.InvoiceIssuanceRequestMessage
import com.tradeix.concord.messages.rabbit.purchaseorder.PurchaseOrderIssuanceRequestMessage
import net.corda.core.identity.CordaX500Name
import org.hamcrest.core.IsInstanceOf
import org.junit.Assert.assertThat
import org.junit.Test
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SerializationTests {

    @Test
    fun `CordaX500Name instance serializes into a valid JSON string`() {
        val message = ConductorTestMessage(CordaX500Name("TradeIX", "London", "GB"))

        val serializer = GsonBuilder()
                .registerTypeAdapter(CordaX500Name::class.java, CordaX500NameSerializer())
                .disableHtmlEscaping()
                .create()

        val json = serializer.toJson(message)

        assertEquals("{\"conductor\":\"O=TradeIX, L=London, C=GB\"}", json)
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
    fun `TIX date string will deserialize to instant`() {
        val json = "{\"theDate\":\"2018-05-09T15:11:50.0249056Z\"}"

        val serializer = GsonBuilder()
                .registerTypeAdapter(CordaX500Name::class.java, CordaX500NameSerializer())
                .registerTypeAdapter(Instant::class.java, DateInstantSerializer())
                .disableHtmlEscaping()
                .create()

        val result = serializer.fromJson<DateTestMessage>(json, DateTestMessage::class.java)
        assertThat(result.theDate, IsInstanceOf.instanceOf(Instant::class.java))
    }

    @Test
    fun `Instant date will serialize to string`() {
        val currentDateTime = Instant.now()
        val serializer = GsonBuilder()
                .registerTypeAdapter(CordaX500Name::class.java, CordaX500NameSerializer())
                .registerTypeAdapter(Instant::class.java, DateInstantSerializer())
                .disableHtmlEscaping()
                .create()

        val dateTestMessage = DateTestMessage(currentDateTime)

        val json = serializer.toJson(dateTestMessage)
        assertThat(json, IsInstanceOf.instanceOf(String::class.java))
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

    @Test
    fun `InvoiceIssuance Json string will deserialize to InvoiceIssuanceRequestMessage`() {
        val testInvoiceIssuanceMessage = "{ \"externalId\": \"28|NID123-INVNUM-001|INVNUM-001\", \"buyer\": \"O=TradeIX Test Buyer 1,L=Tokyo,C=JP\", \"supplier\": \"O=TradeIX Test Supplier 1,L=New York,C=US\", \"conductor\": \"O=TradeIX,L=London,C=GB\", \"invoiceVersion\": \"1.0\", \"invoiceVersionDate\": \"2018-05-09T15:11:50.0249056Z\", \"tixInvoiceVersion\": 1, \"invoiceNumber\": \"INVNUM-001\", \"invoiceType\": \"Insured\", \"reference\": \"REFERENCE\", \"dueDate\": \"2018-05-14T15:11:50.0249072Z\", \"offerId\": null, \"amount\": 123000.0, \"totalOutstanding\": 11300.0, \"created\": \"2018-05-09T15:47:58.940311Z\", \"updated\": \"2018-05-09T15:47:58.940311Z\", \"expectedSettlementDate\": \"2018-05-14T15:11:50.0249072Z\", \"settlementDate\": \"2018-05-14T15:11:50.0249068Z\", \"mandatoryReconciliationtDate\": null, \"invoiceDate\": \"2018-05-02T15:11:50.024906Z\", \"status\": \"Accepted\", \"rejectionReason\": null, \"eligibleValue\": 0.0, \"invoicePurchaseValue\": 11300.0, \"tradeDate\": null, \"tradePaymentDate\": null, \"invoicePayments\": 1000.0, \"invoiceDilutions\": 110700.0, \"cancelled\": false, \"closeDate\": \"2018-05-16T15:11:50.0249068Z\", \"originationNetwork\": \"28\", \"hash\": \"HASH-1234567890-AAA\", \"currency\": \"GBP\", \"siteId\": \"SID123\", \"purchaseOrderNumber\": \"PONumber1\", \"purchaseOrderId\": null, \"composerProgramId\": 2, \"correlationId\": \"4437be5f-0bf8-43ae-96cc-794e36b3e2be\", \"tryCount\": 0 }"

        val serializer = GsonBuilder()
                .registerTypeAdapter(CordaX500Name::class.java, CordaX500NameSerializer())
                .registerTypeAdapter(Instant::class.java, DateInstantSerializer())
                .disableHtmlEscaping()
                .create()

        val result = serializer.fromJson<InvoiceIssuanceRequestMessage>(testInvoiceIssuanceMessage, InvoiceIssuanceRequestMessage::class.java)
        assertEquals("Tokyo", result.buyer?.locality)
        assertEquals(1, result.tixInvoiceVersion)
        assertEquals("28|NID123-INVNUM-001|INVNUM-001", result.externalId)
        assertEquals("New York", result.supplier?.locality)
    }


    data class ConductorTestMessage(val conductor: CordaX500Name)
    data class DateTestMessage(val theDate: Instant)
}