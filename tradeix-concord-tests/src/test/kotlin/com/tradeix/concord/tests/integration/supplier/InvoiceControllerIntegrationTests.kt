package com.tradeix.concord.tests.integration.supplier

import com.tradeix.concord.cordapp.supplier.client.receiver.controllers.InvoiceController
import com.tradeix.concord.shared.mockdata.MockInvoices.createMockInvoices
import com.tradeix.concord.shared.mockdata.ParticipantType
import com.tradeix.concord.tests.integration.ControllerIntegrationTest
import net.corda.testing.node.StartedMockNode
import org.junit.Test

class InvoiceControllerIntegrationTests : ControllerIntegrationTest() {

    private val controller = InvoiceController(/* TODO : Get RPC Connection to Supplier's Node */)

    override fun configureNode(node: StartedMockNode, type: ParticipantType) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Test
    fun `Can issue an invoice`() {
        val request = createMockInvoices(3, buyer1.name, supplier1.name, listOf(funder1.name, funder2.name))
        val response = controller.issueInvoice(request)

        // Callable<ResponseEntity<*>>
        // Callable<ResponseEntity<InvoiceTransactionResponseMessage>>

        // INVOICE_1, INVOICE_2, INVOICE_3
    }
}