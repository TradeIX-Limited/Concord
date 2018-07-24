package com.tradeix.concord.tests.integration.supplier

import com.tradeix.concord.cordapp.supplier.client.receiver.controllers.InvoiceController
import com.tradeix.concord.cordapp.supplier.messages.invoices.InvoiceTransactionResponseMessage
import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.cordapp.flows.CollectSignaturesResponderFlow
import com.tradeix.concord.shared.cordapp.flows.ObserveTransactionResponderFlow
import com.tradeix.concord.shared.mockdata.MockInvoices.createMockInvoiceAmendments
import com.tradeix.concord.shared.mockdata.MockInvoices.createMockInvoiceCancellations
import com.tradeix.concord.shared.mockdata.MockInvoices.createMockInvoiceTransfers
import com.tradeix.concord.shared.mockdata.MockInvoices.createMockInvoices
import com.tradeix.concord.shared.mockdata.ParticipantType
import com.tradeix.concord.tests.integration.ControllerIntegrationTest
import net.corda.testing.node.StartedMockNode
import org.junit.Test
import org.springframework.http.ResponseEntity
import java.util.concurrent.Callable

class InvoiceControllerIntegrationTests : ControllerIntegrationTest() {

    private val controller = InvoiceController(RPCConnectionProvider(username = "user1", password = "test", host = "", port = 1)) // TODO : COMPLETE

    override fun configureNode(node: StartedMockNode, type: ParticipantType) {
        if (type == ParticipantType.BUYER) {
            node.registerInitiatedFlow(CollectSignaturesResponderFlow::class.java)
        }
        if (type == ParticipantType.FUNDER) {
            node.registerInitiatedFlow(ObserveTransactionResponderFlow::class.java)
        }
    }

    @Test
    fun `Can issue an invoice`() {
        val request = createMockInvoices(3, buyer1.name, supplier1.name, listOf(funder1.name, funder2.name))
        val response: Callable<ResponseEntity<InvoiceTransactionResponseMessage>> = controller.issueInvoice(request) as Callable<ResponseEntity<InvoiceTransactionResponseMessage>>

        response.call().body.externalIds.containsAll(listOf("INVOICE_1", "INVOICE_2", "INVOICE_3"))
    }

    @Test // TODO : check
    fun `Can amend an invoice`() {
        val request = createMockInvoiceAmendments(3, buyer1.name, supplier1.name, listOf(funder1.name, funder2.name))
        val response: Callable<ResponseEntity<InvoiceTransactionResponseMessage>> = controller.amendInvoice(request) as Callable<ResponseEntity<InvoiceTransactionResponseMessage>>

        response.call().body.externalIds.containsAll(listOf("INVOICE_1", "INVOICE_2", "INVOICE_3"))
    }

    @Test // TODO : check
    fun `Can transfer an invoice`() {
        val request = createMockInvoiceTransfers(3, buyer2.name)
        val response: Callable<ResponseEntity<InvoiceTransactionResponseMessage>> = controller.transferInvoice(request) as Callable<ResponseEntity<InvoiceTransactionResponseMessage>>

        response.call().body.externalIds.containsAll(listOf("INVOICE_1", "INVOICE_2", "INVOICE_3"))
    }

    @Test // TODO : check
    fun `Can cancel an invoice`() {
        val request = createMockInvoiceCancellations(3, listOf(funder1.name, funder2.name))
        val response: Callable<ResponseEntity<InvoiceTransactionResponseMessage>> = controller.cancelInvoice(request) as Callable<ResponseEntity<InvoiceTransactionResponseMessage>>

        response.call().body.externalIds.containsAll(listOf("INVOICE_1", "INVOICE_2", "INVOICE_3"))
    }
}