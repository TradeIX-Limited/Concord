package com.tradeix.concord.tests.integration

import com.tradeix.concord.cordapp.funder.messages.fundingresponses.FundingResponseIssuanceResponseMessage
import com.tradeix.concord.cordapp.supplier.client.receiver.controllers.InvoiceController
import com.tradeix.concord.cordapp.supplier.messages.fundingresponses.FundingResponseConfirmationResponseMessage
import com.tradeix.concord.cordapp.supplier.messages.invoices.InvoiceTransactionResponseMessage
import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.messages.ErrorResponseMessage
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.BUYER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.FUNDER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.SUPPLIER_1_NAME
import com.tradeix.concord.shared.mockdata.MockFundingResponses.FUNDING_RESPONSE_ACCEPTANCE_REQUEST_MESSAGE
import com.tradeix.concord.shared.mockdata.MockFundingResponses.FUNDING_RESPONSE_ISSUANCE_REQUEST_MESSAGE
import com.tradeix.concord.shared.mockdata.MockFundingResponses.FUNDING_RESPONSE_REJECTION_REQUEST_MESSAGE
import com.tradeix.concord.shared.mockdata.MockInvoices.createMockInvoiceAmendments
import com.tradeix.concord.shared.mockdata.MockInvoices.createMockInvoiceCancellations
import com.tradeix.concord.shared.mockdata.MockInvoices.createMockInvoiceTransfers
import com.tradeix.concord.shared.mockdata.MockInvoices.createMockInvoices
import com.tradeix.concord.shared.mockdata.ParticipantType
import junit.framework.Assert.fail
import net.corda.core.utilities.getOrThrow
import net.corda.testing.driver.DriverParameters
import net.corda.testing.driver.NodeHandle
import net.corda.testing.driver.driver
import net.corda.testing.node.StartedMockNode
import net.corda.testing.node.User
import org.junit.After
import org.junit.Before
import org.springframework.http.ResponseEntity

typealias SupplierFundingResponseController =
        com.tradeix.concord.cordapp.supplier.client.receiver.controllers.FundingResponseController

typealias FunderFundingResponseController =
        com.tradeix.concord.cordapp.funder.client.receiver.controllers.FundingResponseController

abstract class ControllerIntegrationTest {

    protected lateinit var buyer: NodeHandle
    protected lateinit var funder: NodeHandle
    protected lateinit var supplier: NodeHandle

    private lateinit var invoiceController: InvoiceController
    private lateinit var supplierFundingResponseController: SupplierFundingResponseController
    private lateinit var funderFundingResponseController: FunderFundingResponseController

    private lateinit var buyerRpc: RPCConnectionProvider
    private lateinit var funderRpc: RPCConnectionProvider
    private lateinit var supplierRpc: RPCConnectionProvider

    @Before
    fun setup() {
    }

    @After
    fun tearDown() {
    }

    fun withDriver(action: () -> Unit) {
        val cordapps = listOf(
                "com.tradeix.concord.shared.domain",
                "com.tradeix.concord.shared.cordapp",
                "com.tradeix.concord.cordapp.supplier",
                "com.tradeix.concord.cordapp.funder"
        )

        driver(DriverParameters(
                startNodesInProcess = true,
                extraCordappPackagesToScan = cordapps)) {

            val user = User("user1", "test", permissions = setOf("ALL"))

            supplier = startNode(providedName = SUPPLIER_1_NAME, rpcUsers = listOf(user)).getOrThrow()
            buyer = startNode(providedName = BUYER_1_NAME, rpcUsers = listOf(user)).getOrThrow()
            funder = startNode(providedName = FUNDER_1_NAME, rpcUsers = listOf(user)).getOrThrow()

            initialize()
            action()
        }
    }

    protected open fun configureNode(node: StartedMockNode, type: ParticipantType) {
    }

    protected open fun initialize() {
        buyerRpc = RPCConnectionProvider(
                username = "user1",
                password = "test",
                host = buyer.rpcAddress.host,
                port = buyer.rpcAddress.port
        )

        supplierRpc = RPCConnectionProvider(
                username = "user1",
                password = "test",
                host = supplier.rpcAddress.host,
                port = supplier.rpcAddress.port
        )

        funderRpc = RPCConnectionProvider(
                username = "user1",
                password = "test",
                host = funder.rpcAddress.host,
                port = funder.rpcAddress.port
        )

        invoiceController = InvoiceController(supplierRpc)
        funderFundingResponseController = FunderFundingResponseController(funderRpc)
        supplierFundingResponseController = SupplierFundingResponseController(supplierRpc)
    }

    protected fun issueInvoicesOrThrow(): ResponseEntity<InvoiceTransactionResponseMessage> {
        val request = createMockInvoices(
                count = 3,
                buyer = BUYER_1_NAME,
                supplier = SUPPLIER_1_NAME,
                observers = listOf(FUNDER_1_NAME)
        )

        val response = invoiceController.issueInvoice(request).call()

        return try {
            response as ResponseEntity<InvoiceTransactionResponseMessage>
        } catch (ex: Exception) {
            val errorResponse = response as ResponseEntity<ErrorResponseMessage>
            fail(errorResponse.body.error)
            throw Exception("${ex.message}\n${errorResponse.body.error}")
        }
    }

    protected fun amendInvoicesOrThrow(): ResponseEntity<InvoiceTransactionResponseMessage> {
        val request = createMockInvoiceAmendments(
                count = 3,
                buyer = BUYER_1_NAME,
                supplier = SUPPLIER_1_NAME,
                observers = listOf(FUNDER_1_NAME)
        )

        val response = invoiceController.amendInvoice(request).call()

        return try {
            response as ResponseEntity<InvoiceTransactionResponseMessage>
        } catch (ex: Exception) {
            val errorResponse = response as ResponseEntity<ErrorResponseMessage>
            fail(errorResponse.body.error)
            throw Exception("${ex.message}\n${errorResponse.body.error}")
        }
    }

    protected fun transferInvoicesOrThrow(): ResponseEntity<InvoiceTransactionResponseMessage> {
        val request = createMockInvoiceTransfers(
                count = 3,
                owner = FUNDER_1_NAME
        )

        val response = invoiceController.transferInvoice(request).call()

        return try {
            response as ResponseEntity<InvoiceTransactionResponseMessage>
        } catch (ex: Exception) {
            val errorResponse = response as ResponseEntity<ErrorResponseMessage>
            fail(errorResponse.body.error)
            throw Exception("${ex.message}\n${errorResponse.body.error}")
        }
    }

    protected fun cancelInvoicesOrThrow(): ResponseEntity<InvoiceTransactionResponseMessage> {
        val request = createMockInvoiceCancellations(
                count = 3,
                observers = listOf(FUNDER_1_NAME)
        )

        val response = invoiceController.cancelInvoice(request).call()

        return try {
            response as ResponseEntity<InvoiceTransactionResponseMessage>
        } catch (ex: Exception) {
            val errorResponse = response as ResponseEntity<ErrorResponseMessage>
            fail(errorResponse.body.error)
            throw Exception("${ex.message}\n${errorResponse.body.error}")
        }
    }

    protected fun issueFundingResponseOrThrow(): ResponseEntity<FundingResponseIssuanceResponseMessage> {
        val request = FUNDING_RESPONSE_ISSUANCE_REQUEST_MESSAGE
        val response = funderFundingResponseController.issueFundingResponse(request).call()

        return try {
            response as ResponseEntity<FundingResponseIssuanceResponseMessage>
        } catch (ex: Exception) {
            val errorResponse = response as ResponseEntity<ErrorResponseMessage>
            fail(errorResponse.body.error)
            throw Exception("${ex.message}\n${errorResponse.body.error}")
        }
    }

    protected fun acceptFundingResponseOrThrow(): ResponseEntity<FundingResponseConfirmationResponseMessage> {
        val request = FUNDING_RESPONSE_ACCEPTANCE_REQUEST_MESSAGE
        val response = supplierFundingResponseController.acceptFundingResponse(request).call()

        return try {
            response as ResponseEntity<FundingResponseConfirmationResponseMessage>
        } catch (ex: Exception) {
            val errorResponse = response as ResponseEntity<ErrorResponseMessage>
            fail(errorResponse.body.error)
            throw Exception("${ex.message}\n${errorResponse.body.error}")
        }
    }

    protected fun rejectFundingResponseOrThrow(): ResponseEntity<FundingResponseConfirmationResponseMessage> {
        val request = FUNDING_RESPONSE_REJECTION_REQUEST_MESSAGE
        val response = supplierFundingResponseController.acceptFundingResponse(request).call()

        return try {
            response as ResponseEntity<FundingResponseConfirmationResponseMessage>
        } catch (ex: Exception) {
            val errorResponse = response as ResponseEntity<ErrorResponseMessage>
            fail(errorResponse.body.error)
            throw Exception("${ex.message}\n${errorResponse.body.error}")
        }
    }
}