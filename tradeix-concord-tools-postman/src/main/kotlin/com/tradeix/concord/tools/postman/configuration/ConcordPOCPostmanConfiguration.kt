package com.tradeix.concord.tools.postman.configuration

import com.tradeix.concord.shared.mockdata.MockCordaX500Names.BUYER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.BUYER_2_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.FUNDER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.SUPPLIER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.SUPPLIER_2_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.SUPPLIER_3_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.TRADEIX_NAME
import com.tradeix.concord.shared.mockdata.MockMessages.PURCHASE_ORDER_AMENDMENT_REQUEST_MESSAGE
import com.tradeix.concord.shared.mockdata.MockMessages.PURCHASE_ORDER_CANCELLATION_REQUEST_MESSAGE
import com.tradeix.concord.shared.mockdata.MockMessages.PURCHASE_ORDER_CHANGE_OWNER_REQUEST_MESSAGE
import com.tradeix.concord.shared.mockdata.MockMessages.PURCHASE_ORDER_ISSUANCE_REQUEST_MESSAGE
import com.tradeix.concord.tools.postman.model.*

class ConcordPOCPostmanConfiguration : PostmanConfiguration("Concord-POC", "TradeIX Concord POC REST API") {

    private val host = "localhost"
    private val nodes = mapOf(
            TRADEIX_NAME.organisation to "10008",
            SUPPLIER_1_NAME.organisation to "10010",
            SUPPLIER_2_NAME.organisation to "10013",
            SUPPLIER_3_NAME.organisation to "10016",
            BUYER_1_NAME.organisation to "10019",
            BUYER_2_NAME.organisation to "10022",
            FUNDER_1_NAME.organisation to "10025"
    )

    override fun configureCollection(collection: EndpointCollection) {
        nodes.forEach {
            val group = EndpointGroup(it.key)

            group.item.add(Endpoint("upload/attachment", Request(
                    method = "POST",
                    description = "Uploads an attachment to the local node's vault",
                    header = RequestHeader.APPLICATION_JSON,
                    body = FormDataRequestBody.ATTACHMENT,
                    url = RequestUrl.from(
                            protocol = "http",
                            host = host,
                            port = it.value,
                            path = "upload/attachment"
                    )
            )))

            group.item.add(Endpoint("api/nodes/all", Request(
                    method = "GET",
                    description = "Gets all known X500 named identities from the network map cache",
                    header = RequestHeader.EMPTY,
                    body = RequestBody.EMPTY,
                    url = RequestUrl.from(
                            protocol = "http",
                            host = host,
                            port = it.value,
                            path = "api/nodes/all"
                    )
            )))

            group.item.add(Endpoint("api/nodes/local", Request(
                    method = "GET",
                    description = "Gets the local node's known X500 named identity from the network map cache",
                    header = RequestHeader.EMPTY,
                    body = RequestBody.EMPTY,
                    url = RequestUrl.from(
                            protocol = "http",
                            host = host,
                            port = it.value,
                            path = "api/nodes/local"
                    )
            )))

            group.item.add(Endpoint("api/purchaseorders", Request(
                    method = "GET",
                    description = "Gets all purchase orders from the local node's vault",
                    header = RequestHeader.EMPTY,
                    body = RequestBody.EMPTY,
                    url = RequestUrl.from(
                            protocol = "http",
                            host = host,
                            port = it.value,
                            path = "api/purchaseorders?externalId=EXTERNALID"
                    )
            )))

            group.item.add(Endpoint("api/purchaseorders/all", Request(
                    method = "GET",
                    description = "Gets all purchase orders from the local node's vault",
                    header = RequestHeader.EMPTY,
                    body = RequestBody.EMPTY,
                    url = RequestUrl.from(
                            protocol = "http",
                            host = host,
                            port = it.value,
                            path = "api/purchaseorders/all?page=1&count=50"
                    )
            )))

            group.item.add(Endpoint("api/purchaseorders/issue", Request(
                    method = "POST",
                    description = "Issues a new purchase order and stores it in the participants' vaults",
                    header = RequestHeader.APPLICATION_JSON,
                    body = JsonRequestBody(PURCHASE_ORDER_ISSUANCE_REQUEST_MESSAGE),
                    url = RequestUrl.from(
                            protocol = "http",
                            host = host,
                            port = it.value,
                            path = "api/purchaseorders/issue"
                    )
            )))

            group.item.add(Endpoint("api/purchaseorders/amend", Request(
                    method = "PUT",
                    description = "Amends an existing purchase order and stores it in the participants' vaults",
                    header = RequestHeader.APPLICATION_JSON,
                    body = JsonRequestBody(PURCHASE_ORDER_AMENDMENT_REQUEST_MESSAGE),
                    url = RequestUrl.from(
                            protocol = "http",
                            host = host,
                            port = it.value,
                            path = "api/purchaseorders/amend"
                    )
            )))

            group.item.add(Endpoint("api/purchaseorders/changeowner", Request(
                    method = "PUT",
                    description = "Changes ownership of a purchase order and stores it in the participants' vaults",
                    header = RequestHeader.APPLICATION_JSON,
                    body = JsonRequestBody(PURCHASE_ORDER_CHANGE_OWNER_REQUEST_MESSAGE),
                    url = RequestUrl.from(
                            protocol = "http",
                            host = host,
                            port = it.value,
                            path = "api/purchaseorders/changeowner"
                    )
            )))

            group.item.add(Endpoint("api/purchaseorders/cancel", Request(
                    method = "PUT",
                    description = "Cancels a purchase order and removes it from the participants' vaults",
                    header = RequestHeader.APPLICATION_JSON,
                    body = JsonRequestBody(PURCHASE_ORDER_CANCELLATION_REQUEST_MESSAGE),
                    url = RequestUrl.from(
                            protocol = "http",
                            host = host,
                            port = it.value,
                            path = "api/purchaseorders/cancel"
                    )
            )))

            group.item.add(Endpoint("api/invoices", Request(
                    method = "GET",
                    description = "Gets all invoices from the local node's vault",
                    header = RequestHeader.EMPTY,
                    body = RequestBody.EMPTY,
                    url = RequestUrl.from(
                            protocol = "http",
                            host = host,
                            port = it.value,
                            path = "api/invoices?externalId=INVOICE_001"
                    )
            )))

            group.item.add(Endpoint("api/invoices/all", Request(
                    method = "GET",
                    description = "Gets all invoices from the local node's vault",
                    header = RequestHeader.EMPTY,
                    body = RequestBody.EMPTY,
                    url = RequestUrl.from(
                            protocol = "http",
                            host = host,
                            port = it.value,
                            path = "api/invoices/all?page=1&count=50"
                    )
            )))

//            group.item.add(Endpoint("api/invoices/issue", Request(
//                    method = "POST",
//                    description = "Issues a new invoice and stores it in the participants' vaults",
//                    header = RequestHeader.APPLICATION_JSON,
//                    body = JsonRequestBody(INVOICE_REQUEST_MESSAGE),
//                    url = RequestUrl.from(
//                            protocol = "http",
//                            host = host,
//                            port = it.value,
//                            path = "api/invoices/issue"
//                    )
//            )))
//
//            group.item.add(Endpoint("api/invoices/changeowner", Request(
//                    method = "PUT",
//                    description = "Changes ownership of an invoice and stores it in the participants' vaults",
//                    header = RequestHeader.APPLICATION_JSON,
//                    body = JsonRequestBody(INVOICE_CHANGE_OWNER_REQUEST_MESSAGE),
//                    url = RequestUrl.from(
//                            protocol = "http",
//                            host = host,
//                            port = it.value,
//                            path = "api/invoices/changeowner"
//                    )
//            )))

            collection.item.add(group)
        }
    }
}