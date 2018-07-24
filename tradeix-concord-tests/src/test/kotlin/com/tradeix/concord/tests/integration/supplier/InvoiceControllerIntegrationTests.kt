package com.tradeix.concord.tests.integration.supplier

import com.tradeix.concord.cordapp.supplier.client.receiver.controllers.InvoiceController
import com.tradeix.concord.cordapp.supplier.messages.invoices.InvoiceTransactionResponseMessage
import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.messages.ErrorResponseMessage
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.BUYER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.FUNDER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.SUPPLIER_1_NAME
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
import kotlin.test.fail

class InvoiceControllerIntegrationTests : ControllerIntegrationTest() {

    private lateinit var controller: InvoiceController

    override fun initialize() {
        rpc = RPCConnectionProvider(
                username = "user1",
                password = "test",
                host = supplier.rpcAddress.host,
                port = supplier.rpcAddress.port
        )

        controller = InvoiceController(rpc)
    }

    override fun configureNode(node: StartedMockNode, type: ParticipantType) {
    }

    @Test
    fun `Can issue an invoice`() {
        withDriver {
            val response = issueInvoices() as Callable<ResponseEntity<InvoiceTransactionResponseMessage>>
            response.call().body.externalIds.containsAll(listOf("INVOICE_1", "INVOICE_2", "INVOICE_3"))
        }
    }

    @Test // TODO : check
    fun `Can amend an invoice`() {
        withDriver {

            issueInvoices().call()

            val request = createMockInvoiceAmendments(3, BUYER_1_NAME, SUPPLIER_1_NAME, listOf(FUNDER_1_NAME))
            val response = controller.amendInvoice(request)

            try {
                val successfulResponse = response as Callable<ResponseEntity<InvoiceTransactionResponseMessage>>
                successfulResponse.call().body.externalIds.containsAll(listOf("INVOICE_1", "INVOICE_2", "INVOICE_3"))
            } catch (ex: Exception) {
                val unsuccessfulResponse = response as Callable<ResponseEntity<ErrorResponseMessage>>
                fail(unsuccessfulResponse.call().body.error)
            }
        }
    }

    @Test // TODO : check
    fun `Can transfer an invoice`() {
        withDriver {

            issueInvoices().call()

            val request = createMockInvoiceTransfers(3, FUNDER_1_NAME)
            val response: Callable<ResponseEntity<InvoiceTransactionResponseMessage>> = controller.transferInvoice(request) as Callable<ResponseEntity<InvoiceTransactionResponseMessage>>

            response.call().body.externalIds.containsAll(listOf("INVOICE_1", "INVOICE_2", "INVOICE_3"))
        }
    }

    @Test // TODO : check
    fun `Can cancel an invoice`() {
        withDriver {

            issueInvoices().call()

            val request = createMockInvoiceCancellations(3, listOf(FUNDER_1_NAME))
            val response: Callable<ResponseEntity<InvoiceTransactionResponseMessage>> = controller.cancelInvoice(request) as Callable<ResponseEntity<InvoiceTransactionResponseMessage>>

            response.call().body.externalIds.containsAll(listOf("INVOICE_1", "INVOICE_2", "INVOICE_3"))
        }
    }

    private fun issueInvoices(): Callable<ResponseEntity<*>> {
        val request = createMockInvoices(3, BUYER_1_NAME, SUPPLIER_1_NAME, listOf(FUNDER_1_NAME))
        return controller.issueInvoice(request)
    }
}