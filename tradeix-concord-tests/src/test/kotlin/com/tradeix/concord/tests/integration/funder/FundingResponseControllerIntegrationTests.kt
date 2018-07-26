package com.tradeix.concord.tests.integration.funder

import com.tradeix.concord.cordapp.supplier.client.receiver.controllers.InvoiceController
import com.tradeix.concord.cordapp.funder.client.receiver.controllers.FundingResponseController
import com.tradeix.concord.cordapp.funder.messages.fundingresponses.FundingResponseIssuanceResponseMessage
import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.messages.ErrorResponseMessage
import com.tradeix.concord.shared.mockdata.MockCordaX500Names
import com.tradeix.concord.shared.mockdata.MockFundingResponses
import com.tradeix.concord.shared.mockdata.MockInvoices
import com.tradeix.concord.shared.mockdata.ParticipantType
import com.tradeix.concord.tests.integration.ControllerIntegrationTest
import net.corda.testing.node.StartedMockNode
import org.junit.Test
import org.springframework.http.ResponseEntity
import java.util.concurrent.Callable
import kotlin.test.fail

class FundingResponseControllerIntegrationTests : ControllerIntegrationTest() {

    private lateinit var controller: FundingResponseController
    private lateinit var controller2: InvoiceController

    override fun initialize() {
        rpc = RPCConnectionProvider(
                username = "user1",
                password = "test",
                host = supplier.rpcAddress.host,
                port = supplier.rpcAddress.port
        )

        controller = FundingResponseController(rpc)
        controller2 = InvoiceController(rpc)
    }

    override fun configureNode(node: StartedMockNode, type: ParticipantType) {
    }

    @Test
    fun `Can issue a funding response`() {
        withDriver {

            issueInvoices().call()

            val request = MockFundingResponses.FUNDING_RESPONSE_ISSUANCE_REQUEST_MESSAGE
            val response = controller.issueFundingResponse(request)

            try {
                val successfulResponse = response as Callable<ResponseEntity<FundingResponseIssuanceResponseMessage>>
                successfulResponse.call().body.externalId.contains("FUNDING_RESPONSE_1")
            } catch (ex: Exception) {
                val unsuccessfulResponse = response as Callable<ResponseEntity<ErrorResponseMessage>>
                fail(unsuccessfulResponse.call().body.error)
            }
        }
    }

    private fun issueInvoices(): Callable<ResponseEntity<*>> {
        val request = MockInvoices.createMockInvoices(3, MockCordaX500Names.BUYER_1_NAME, MockCordaX500Names.SUPPLIER_1_NAME, listOf(MockCordaX500Names.FUNDER_1_NAME))
        return controller2.issueInvoice(request)
    }

}