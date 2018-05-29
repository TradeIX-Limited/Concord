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
            invoiceVersion = "001",
            invoiceVersionDate = LOCAL_DATE_TIME_PAST_1,
            tixInvoiceVersion = 1,
            invoiceNumber = "001",
            invoiceType = "Generic Invoice",
            reference = "REF_INVOICE_001",
            dueDate = LOCAL_DATE_TIME_FUTURE_1,
            offerId = null,
            amount = BigDecimal.valueOf(123.45),
            totalOutstanding = BigDecimal.valueOf(123.45),
            created = LOCAL_DATE_TIME_PAST_1,
            updated = LOCAL_DATE_TIME_PAST_1,
            expectedSettlementDate = LOCAL_DATE_TIME_PAST_1,
            settlementDate = null,
            mandatoryReconciliationDate = null,
            invoiceDate = LOCAL_DATE_TIME_PAST_1,
            status = "ELIGIBLE",
            rejectionReason = null,
            eligibleValue = BigDecimal.valueOf(123.45),
            invoicePurchaseValue = BigDecimal.valueOf(123.45),
            tradeDate = null,
            tradePaymentDate = null,
            invoicePayments = BigDecimal.ZERO,
            invoiceDilutions = BigDecimal.ZERO,
            cancelled = false,
            closeDate = null,
            originationNetwork = "Mock Corda Network",
            currency = "GBP",
            siteId = "MOCK_CORDA_NETWORK",
            purchaseOrderNumber = "PURCHASE_ORDER_001",
            purchaseOrderId = "PURCHASE_ORDER_001",
            composerProgramId = 1
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