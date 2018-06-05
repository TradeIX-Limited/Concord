package com.tradeix.concord.shared.mockdata

import com.tradeix.concord.shared.messages.CancellationRequestMessage
import com.tradeix.concord.shared.messages.OwnershipRequestMessage
import com.tradeix.concord.shared.messages.invoices.InvoiceRequestMessage
import com.tradeix.concord.shared.messages.purchaseorders.PurchaseOrderRequestMessage
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.BUYER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.FUNDER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.SUPPLIER_1_NAME
import com.tradeix.concord.shared.mockdata.MockLocalDateTimes.LOCAL_DATE_TIME_FUTURE_1
import com.tradeix.concord.shared.mockdata.MockLocalDateTimes.LOCAL_DATE_TIME_PAST_1
import com.tradeix.concord.shared.mockdata.MockLocalDateTimes.LOCAL_DATE_TIME_PAST_2
import com.tradeix.concord.shared.mockdata.MockLocalDateTimes.LOCAL_DATE_TIME_PAST_3
import java.math.BigDecimal

object MockMessages {

    val INVOICE_ISSUANCE_REQUEST_MESSAGE = InvoiceRequestMessage(
            externalId = "INVOICE_001",
            buyer = BUYER_1_NAME.toString(),
            supplier = SUPPLIER_1_NAME.toString(),
            invoiceNumber = "001",
            reference = "REF_INVOICE_001",
            dueDate = LOCAL_DATE_TIME_FUTURE_1,
            amount = BigDecimal.valueOf(123.45),
            totalOutstanding = BigDecimal.valueOf(123.45),
            settlementDate = LOCAL_DATE_TIME_FUTURE_1,
            invoiceDate = LOCAL_DATE_TIME_PAST_1,
            invoicePayments = BigDecimal.ZERO,
            invoiceDilutions = BigDecimal.ZERO,
            originationNetwork = "Mock Corda Network",
            currency = "GBP",
            siteId = "MOCK_CORDA_NETWORK"
    )

    val INVOICE_CHANGE_OWNER_REQUEST_MESSAGE = OwnershipRequestMessage(
            externalId = "INVOICE_001",
            owner = FUNDER_1_NAME.toString()
    )

    val INVOICE_CANCELLATION_REQUEST_MESSAGE = CancellationRequestMessage(
            externalId = "INVOICE_001"
    )

    val PURCHASE_ORDER_ISSUANCE_REQUEST_MESSAGE = PurchaseOrderRequestMessage(
            externalId = "PURCHASE_ORDER_001",
            buyer = BUYER_1_NAME.toString(),
            supplier = SUPPLIER_1_NAME.toString(),
            reference = "REF_PURCHASE_ORDER_001",
            value = BigDecimal.valueOf(123.45),
            currency = "GBP",
            created = LOCAL_DATE_TIME_PAST_1,
            earliestShipment = LOCAL_DATE_TIME_PAST_2,
            latestShipment = LOCAL_DATE_TIME_PAST_3,
            portOfShipment = "Portsmouth",
            descriptionOfGoods = "100 units of stock",
            deliveryTerms = "Subject to contract"
    )

    val PURCHASE_ORDER_AMENDMENT_REQUEST_MESSAGE = PurchaseOrderRequestMessage(
            externalId = "PURCHASE_ORDER_001",
            buyer = BUYER_1_NAME.toString(),
            supplier = SUPPLIER_1_NAME.toString(),
            reference = "REF_PURCHASE_ORDER_001_AMENDED",
            value = BigDecimal.valueOf(678.90),
            currency = "GBP",
            created = LOCAL_DATE_TIME_PAST_1,
            earliestShipment = LOCAL_DATE_TIME_PAST_2,
            latestShipment = LOCAL_DATE_TIME_PAST_3,
            portOfShipment = "Portsmouth",
            descriptionOfGoods = "300 units of stock (amended)",
            deliveryTerms = "Subject to contract"
    )

    val PURCHASE_ORDER_CHANGE_OWNER_REQUEST_MESSAGE = OwnershipRequestMessage(
            externalId = "PURCHASE_ORDER_001",
            owner = FUNDER_1_NAME.toString()
    )

    val PURCHASE_ORDER_CANCELLATION_REQUEST_MESSAGE = CancellationRequestMessage(
            externalId = "PURCHASE_ORDER_001"
    )
}