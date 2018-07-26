package com.tradeix.concord.tests.integration.supplier

import com.tradeix.concord.cordapp.funder.messages.fundingresponses.FundingResponseIssuanceResponseMessage
import com.tradeix.concord.cordapp.supplier.client.receiver.controllers.InvoiceController
import com.tradeix.concord.cordapp.supplier.messages.fundingresponses.FundingResponseConfirmationResponseMessage
import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.messages.ErrorResponseMessage
import com.tradeix.concord.shared.mockdata.MockCordaX500Names
import com.tradeix.concord.shared.mockdata.MockFundingResponses
import com.tradeix.concord.shared.mockdata.MockFundingResponses.FUNDING_RESPONSE_ACCEPTANCE_REQUEST_MESSAGE
import com.tradeix.concord.shared.mockdata.MockFundingResponses.FUNDING_RESPONSE_REJECTION_REQUEST_MESSAGE
import com.tradeix.concord.shared.mockdata.MockInvoices
import com.tradeix.concord.shared.mockdata.ParticipantType
import com.tradeix.concord.tests.integration.ControllerIntegrationTest
import net.corda.testing.node.StartedMockNode
import org.junit.Test
import org.springframework.http.ResponseEntity
import java.util.concurrent.Callable
import kotlin.test.fail

class SupplierFundingResponseControllerIntegrationTests : ControllerIntegrationTest() {

    private lateinit var controller: com.tradeix.concord.cordapp.supplier.client.receiver.controllers.FundingResponseController
    private lateinit var controller2: InvoiceController
    private lateinit var controller3: com.tradeix.concord.cordapp.funder.client.receiver.controllers.FundingResponseController

    override fun initialize() {
        rpc = RPCConnectionProvider(
                username = "user1",
                password = "test",
                host = supplier.rpcAddress.host,
                port = supplier.rpcAddress.port
        )

        controller = com.tradeix.concord.cordapp.supplier.client.receiver.controllers.FundingResponseController(rpc)
        controller2 = InvoiceController(rpc)
        controller3 = com.tradeix.concord.cordapp.funder.client.receiver.controllers.FundingResponseController(rpc)
    }

    override fun configureNode(node: StartedMockNode, type: ParticipantType) {
    }

    @Test
    fun `Can accept a funding response`() {
        withDriver {

            issueInvoices().call()
            issueFundingResponse().call()

            val request = FUNDING_RESPONSE_ACCEPTANCE_REQUEST_MESSAGE
            val response = controller.acceptFundingResponse(request)

            try {
                val successfulResponse = response as Callable<ResponseEntity<FundingResponseConfirmationResponseMessage>>
                successfulResponse.call().body.externalId.contains("FUNDING_RESPONSE_1") // Test with another value
            } catch (ex: Exception) {
                val unsuccessfulResponse = response as Callable<ResponseEntity<ErrorResponseMessage>>
                fail(unsuccessfulResponse.call().body.error)
            }


        }
    }

    @Test
    fun `Can reject a funding response`() {
        withDriver {

            issueInvoices().call()
            val fundingresponse = issueFundingResponse().call()
            println("INFO FUNDING RESPONSE: "+fundingresponse.body.toString())

            val request = FUNDING_RESPONSE_REJECTION_REQUEST_MESSAGE
            val response = controller.rejectFundingResponse(request)

            try {
                val successfulResponse = response as Callable<ResponseEntity<FundingResponseConfirmationResponseMessage>>
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

    private fun issueFundingResponse(): Callable<ResponseEntity<*>> {

        val request = MockFundingResponses.FUNDING_RESPONSE_ISSUANCE_REQUEST_MESSAGE
        return controller3.issueFundingResponse(request)

        /*try {
            val successfulResponse = response as Callable<ResponseEntity<FundingResponseIssuanceResponseMessage>>
            successfulResponse.call().body.externalId.contains("FUNDING_RESPONSE_1")
            return successfulResponse
        } catch (ex: Exception) {
            val unsuccessfulResponse = response as Callable<ResponseEntity<ErrorResponseMessage>>
            fail(unsuccessfulResponse.call().body.error)
        }*/

    }
}