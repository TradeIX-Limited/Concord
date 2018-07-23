package com.tradeix.concord.tools.postman.configuration

import com.tradeix.concord.shared.mockdata.MockCordaX500Names.FUNDER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.SUPPLIER_1_NAME
import com.tradeix.concord.shared.mockdata.MockFundingResponses.FUNDING_RESPONSE_ACCEPTANCE_REQUEST_MESSAGE
import com.tradeix.concord.shared.mockdata.MockFundingResponses.FUNDING_RESPONSE_ISSUANCE_REQUEST_MESSAGE
import com.tradeix.concord.shared.mockdata.MockFundingResponses.FUNDING_RESPONSE_REJECTION_REQUEST_MESSAGE
import com.tradeix.concord.shared.mockdata.MockInvoices.createMockInvoiceAmendments
import com.tradeix.concord.shared.mockdata.MockInvoices.createMockInvoiceCancellations
import com.tradeix.concord.shared.mockdata.MockInvoices.createMockInvoiceTransfers
import com.tradeix.concord.shared.mockdata.MockInvoices.createMockInvoices
import com.tradeix.concord.tools.postman.model.*

class ERPPostmanConfiguration : PostmanConfiguration("Concord-ERP", "TradeIX Concord ERP REST API") {

    private val host = "localhost"

    private val supplierNodes = listOf(Node(SUPPLIER_1_NAME.organisation, "8000"))
    private val funderNodes = listOf(Node(FUNDER_1_NAME.organisation, "8050"))

    override fun configureCollection(collection: EndpointCollection) {
        supplierNodes.forEach {
            val group = EndpointGroup(it.displayName)
            configureCommon(it, group)
            configureSupplierNode(it, group)
            collection.item.add(group)
        }

        funderNodes.forEach {
            val group = EndpointGroup(it.displayName)
            configureCommon(it, group)
            configureFunderNode(it, group)
            collection.item.add(group)
        }
    }

    private fun configureSupplierNode(node: Node, group: EndpointGroup) {

        group.item.add(Endpoint("help", Request(
                method = "GET",
                description = "Gets the help documentation.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help"
                )
        )))

        group.item.add(Endpoint("help/invoices", Request(
                method = "GET",
                description = "Gets the help documentation for the invoices endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/invoices"
                )
        )))

        group.item.add(Endpoint("help/invoices/externalId", Request(
                method = "GET",
                description = "Gets the help documentation for the invoices/{externalId} endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/invoices/externalId"
                )
        )))

        group.item.add(Endpoint("help/invoices/count", Request(
                method = "GET",
                description = "Gets the help documentation for the invoices/count endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/invoices/count"
                )
        )))

        group.item.add(Endpoint("help/invoices/hash", Request(
                method = "GET",
                description = "Gets the help documentation for the invoices/hash endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/invoices/hash"
                )
        )))

        group.item.add(Endpoint("help/invoices/issue", Request(
                method = "GET",
                description = "Gets the help documentation for the invoices/issue endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/invoices/issue"
                )
        )))

        group.item.add(Endpoint("help/invoices/amend", Request(
                method = "GET",
                description = "Gets the help documentation for the invoices/amend endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/invoices/amend"
                )
        )))

        group.item.add(Endpoint("help/invoices/transfer", Request(
                method = "GET",
                description = "Gets the help documentation for the invoices/transfer endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/invoices/transfer"
                )
        )))

        group.item.add(Endpoint("help/invoices/cancel", Request(
                method = "GET",
                description = "Gets the help documentation for the invoices/cancel endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/invoices/cancel"
                )
        )))

        group.item.add(Endpoint("help/fundingresponses", Request(
                method = "GET",
                description = "Gets the help documentation for the fundingresponses endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/fundingresponses"
                )
        )))

        group.item.add(Endpoint("help/fundingresponses/externalId", Request(
                method = "GET",
                description = "Gets the help documentation for the fundingresponses/{externalId} endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/fundingresponses/externalId"
                )
        )))

        group.item.add(Endpoint("help/fundingresponses/count", Request(
                method = "GET",
                description = "Gets the help documentation for the fundingresponses/count endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/fundingresponses/count"
                )
        )))

        group.item.add(Endpoint("help/fundingresponses/hash", Request(
                method = "GET",
                description = "Gets the help documentation for the fundingresponses/hash endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/fundingresponses/hash"
                )
        )))

        group.item.add(Endpoint("help/fundingresponses/accept", Request(
                method = "GET",
                description = "Gets the help documentation for the fundingresponses/accept endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/fundingresponses/accept"
                )
        )))

        group.item.add(Endpoint("help/fundingresponses/reject", Request(
                method = "GET",
                description = "Gets the help documentation for the fundingresponses/reject endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/fundingresponses/reject"
                )
        )))

        group.item.add(Endpoint("help/nodes/all", Request(
                method = "GET",
                description = "Gets the help documentation for the nodes/all endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/nodes/all"
                )
        )))

        group.item.add(Endpoint("help/nodes/peers", Request(
                method = "GET",
                description = "Gets the help documentation for the nodes/peers endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/nodes/peers"
                )
        )))

        group.item.add(Endpoint("help/nodes/local", Request(
                method = "GET",
                description = "Gets the help documentation for the nodes/local endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/nodes/local"
                )
        )))

        group.item.add(Endpoint("invoices", Request(
                method = "GET",
                description = "Performs a vault query for invoice states.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "invoices?externalId=INVOICE_001&status=all&pageNumber=1&pageSize=50"
                )
        )))

        group.item.add(Endpoint("invoices/{externalId}", Request(
                method = "GET",
                description = "Performs a vault query for the latest invoice state by externalId.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "invoices/INVOICE_1"
                )
        )))

        group.item.add(Endpoint("invoices/count", Request(
                method = "GET",
                description = "Performs a vault query to count the total number of unconsumed invoice states.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "invoices/count"
                )
        )))

        group.item.add(Endpoint("invoices/hash", Request(
                method = "GET",
                description = "Performs a vault query to get the last known invoice state hash.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "invoices/hash"
                )
        )))

        group.item.add(Endpoint("invoices/issue", Request(
                method = "POST",
                description = "Issues a collection of invoice states to the network.",
                header = RequestHeader.APPLICATION_JSON,
                body = JsonRequestBody(createMockInvoices(10, null, null, listOf(FUNDER_1_NAME))),
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "invoices/issue"
                )
        )))

        group.item.add(Endpoint("invoices/amend", Request(
                method = "PUT",
                description = "Amends a collection of invoice states on the network.",
                header = RequestHeader.APPLICATION_JSON,
                body = JsonRequestBody(createMockInvoiceAmendments(10, null, null, listOf(FUNDER_1_NAME))),
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "invoices/amend"
                )
        )))

        group.item.add(Endpoint("invoices/transfer", Request(
                method = "PUT",
                description = "Transfers a collection of invoice states on the network.",
                header = RequestHeader.APPLICATION_JSON,
                body = JsonRequestBody(createMockInvoiceTransfers(10, FUNDER_1_NAME)),
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "invoices/transfer"
                )
        )))

        group.item.add(Endpoint("invoices/cancel", Request(
                method = "DELETE",
                description = "Cancels a collection of invoice states on the network.",
                header = RequestHeader.APPLICATION_JSON,
                body = JsonRequestBody(createMockInvoiceCancellations(10, listOf(FUNDER_1_NAME))),
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "invoices/cancel"
                )
        )))

        group.item.add(Endpoint("fundingresponses", Request(
                method = "GET",
                description = "Performs a vault query for funding response states.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "fundingresponses?externalId=INVOICE_001&status=all&pageNumber=1&pageSize=50"
                )
        )))

        group.item.add(Endpoint("fundingresponses/{externalId}", Request(
                method = "GET",
                description = "Performs a vault query for the latest funding response state by externalId.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "fundingresponses/FUNDING_RESPONSE_1"
                )
        )))

        group.item.add(Endpoint("fundingresponses/count", Request(
                method = "GET",
                description = "Performs a vault query to count the total number of unconsumed funding response states.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "fundingresponses/count"
                )
        )))

        group.item.add(Endpoint("fundingresponses/hash", Request(
                method = "GET",
                description = "Performs a vault query to get the last known funding response state hash.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "fundingresponses/hash"
                )
        )))

        group.item.add(Endpoint("fundingresponses/accept", Request(
                method = "PUT",
                description = "Accepts an existing funding response.",
                header = RequestHeader.APPLICATION_JSON,
                body = JsonRequestBody(FUNDING_RESPONSE_ACCEPTANCE_REQUEST_MESSAGE),
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "fundingresponses/accept"
                )
        )))

        group.item.add(Endpoint("fundingresponses/reject", Request(
                method = "PUT",
                description = "Rejects an existing funding response.",
                header = RequestHeader.APPLICATION_JSON,
                body = JsonRequestBody(FUNDING_RESPONSE_REJECTION_REQUEST_MESSAGE),
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "fundingresponses/reject"
                )
        )))
    }

    private fun configureFunderNode(node: Node, group: EndpointGroup) {

        group.item.add(Endpoint("help", Request(
                method = "GET",
                description = "Gets the help documentation.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help"
                )
        )))

        group.item.add(Endpoint("help/fundingresponses", Request(
                method = "GET",
                description = "Gets the help documentation for the fundingresponses endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/fundingresponses"
                )
        )))

        group.item.add(Endpoint("help/fundingresponses/externalId", Request(
                method = "GET",
                description = "Gets the help documentation for the fundingresponses/{externalId} endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/fundingresponses/externalId"
                )
        )))

        group.item.add(Endpoint("help/fundingresponses/count", Request(
                method = "GET",
                description = "Gets the help documentation for the fundingresponses/count endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/fundingresponses/count"
                )
        )))

        group.item.add(Endpoint("help/fundingresponses/hash", Request(
                method = "GET",
                description = "Gets the help documentation for the fundingresponses/hash endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/fundingresponses/hash"
                )
        )))

        group.item.add(Endpoint("help/fundingresponses/issue", Request(
                method = "GET",
                description = "Gets the help documentation for the fundingresponses/issue endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/fundingresponses/issue"
                )
        )))

        group.item.add(Endpoint("help/nodes/all", Request(
                method = "GET",
                description = "Gets the help documentation for the nodes/all endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/nodes/all"
                )
        )))

        group.item.add(Endpoint("help/nodes/peers", Request(
                method = "GET",
                description = "Gets the help documentation for the nodes/peers endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/nodes/peers"
                )
        )))

        group.item.add(Endpoint("help/nodes/local", Request(
                method = "GET",
                description = "Gets the help documentation for the nodes/local endpoint.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "help/nodes/local"
                )
        )))

        group.item.add(Endpoint("fundingresponses", Request(
                method = "GET",
                description = "Performs a vault query for funding response states.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "fundingresponses?externalId=INVOICE_001&status=all&pageNumber=1&pageSize=50"
                )
        )))

        group.item.add(Endpoint("fundingresponses/{externalId}", Request(
                method = "GET",
                description = "Performs a vault query for the latest funding response state by externalId.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "fundingresponses/FUNDING_RESPONSE_1"
                )
        )))

        group.item.add(Endpoint("fundingresponses/count", Request(
                method = "GET",
                description = "Performs a vault query to count the total number of unconsumed funding response states.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "fundingresponses/count"
                )
        )))

        group.item.add(Endpoint("fundingresponses/hash", Request(
                method = "GET",
                description = "Performs a vault query to get the last known funding response state hash.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "fundingresponses/hash"
                )
        )))

        group.item.add(Endpoint("fundingresponses/issue", Request(
                method = "POST",
                description = "Creates a new funding response.",
                header = RequestHeader.APPLICATION_JSON,
                body = JsonRequestBody(FUNDING_RESPONSE_ISSUANCE_REQUEST_MESSAGE),
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "fundingresponses/accept"
                )
        )))
    }

    private fun configureCommon(node: Node, group: EndpointGroup) {
        // TODO : Add attachment upload endpoints

        group.item.add(Endpoint("nodes/all", Request(
                method = "GET",
                description = "Gets all known X500 identities from the network map cache.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "nodes/all"
                )
        )))

        group.item.add(Endpoint("nodes/peers", Request(
                method = "GET",
                description = "Gets all known X500 peer identities from the network map cache.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "nodes/peers"
                )
        )))

        group.item.add(Endpoint("nodes/local", Request(
                method = "GET",
                description = "Gets the local node's X500 identity.",
                header = RequestHeader.EMPTY,
                body = RequestBody.EMPTY,
                url = RequestUrl.from(
                        protocol = "http",
                        host = host,
                        port = node.port,
                        path = "nodes/local"
                )
        )))
    }
}