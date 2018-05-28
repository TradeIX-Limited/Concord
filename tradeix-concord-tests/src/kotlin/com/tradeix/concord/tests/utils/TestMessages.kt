package com.tradeix.concord.tests.utils

import com.tradeix.concord.shared.messages.CancellationRequestMessage
import com.tradeix.concord.shared.messages.invoices.InvoiceRequestMessage
import com.tradeix.concord.shared.messages.OwnershipRequestMessage
import com.tradeix.concord.shared.messages.purchaseorders.PurchaseOrderRequestMessage
import com.tradeix.concord.tests.utils.TestLocalDateTimes.LOCAL_DATE_TIME_FUTURE_1
import com.tradeix.concord.tests.utils.TestLocalDateTimes.LOCAL_DATE_TIME_PAST_1
import com.tradeix.concord.tests.utils.TestLocalDateTimes.LOCAL_DATE_TIME_PAST_2
import com.tradeix.concord.tests.utils.TestLocalDateTimes.LOCAL_DATE_TIME_PAST_3
import java.math.BigDecimal

object TestMessages {

    val INVOICE_CANCELLATION_REQUEST_MESSAGE = CancellationRequestMessage(
            externalId = "EXTERNAL_ID"
    )

    val INVOICE_OWNERSHIP_REQUEST_MESSAGE = OwnershipRequestMessage(
            externalId = "EXTERNAL_ID",
            owner = "O=TradeIX Test Funder 1, L=London, C=GB"
    )

    val INVOICE_ISSUANCE_REQUEST_MESSAGE = InvoiceRequestMessage(
            externalId = "INV_EXTERNAL_ID",
            buyer = "O=TradeIX Test Buyer 1, L=London, C=GB",
            supplier = "O=TradeIX Test Supplier 1, L=London, C=GB",
            invoiceVersion = "INV_VER_001",
            invoiceVersionDate = LOCAL_DATE_TIME_PAST_1,
            tixInvoiceVersion = 1,
            invoiceNumber = "INV_NUM_001",
            invoiceType = "INVOICE_TYPE",
            reference = "INVOICE_REFERENCE",
            dueDate = LOCAL_DATE_TIME_FUTURE_1,
            offerId = null,
            amount = BigDecimal.TEN,
            totalOutstanding = BigDecimal.TEN,
            created = LOCAL_DATE_TIME_PAST_1,
            updated = LOCAL_DATE_TIME_PAST_1,
            expectedSettlementDate = LOCAL_DATE_TIME_PAST_1,
            settlementDate = null,
            mandatoryReconciliationDate = null,
            invoiceDate = LOCAL_DATE_TIME_PAST_1,
            status = "INVOICE_STATUS",
            rejectionReason = null,
            eligibleValue = BigDecimal.TEN,
            invoicePurchaseValue = BigDecimal.TEN,
            tradeDate = null,
            tradePaymentDate = null,
            invoicePayments = BigDecimal.ZERO,
            invoiceDilutions = BigDecimal.ZERO,
            cancelled = false,
            closeDate = null,
            originationNetwork = "ORIGINATION_NETWORK",
            currency = "GBP",
            siteId = "SITE_ID",
            purchaseOrderNumber = "PURCHASE_ORDER_001",
            purchaseOrderId = "PO_EXTERNAL_ID",
            composerProgramId = 1
    )

    val PURCHASE_ORDER_ISSUANCE_REQUEST_MESSAGE = PurchaseOrderRequestMessage(
            externalId = "PO_EXTERNAL_ID",
            attachmentId = "8E2A8777D0B5F0A70CFEBEFA218C65B61289150C6F5472F9C060BA46E960F667",
            buyer = "O=TradeIX Test Buyer 1, L=London, C=GB",
            supplier = "O=TradeIX Test Supplier 1, L=London, C=GB",
            reference = "PURCHASE_ORDER_REFERENCE",
            value = BigDecimal.TEN,
            currency = "GBP",
            created = LOCAL_DATE_TIME_PAST_1,
            earliestShipment = LOCAL_DATE_TIME_PAST_2,
            latestShipment = LOCAL_DATE_TIME_PAST_3,
            portOfShipment = "PORT OF SHIPMENT",
            descriptionOfGoods = "DESCRIPTION OF GOODS",
            deliveryTerms = "DELIVERY TERMS"
    )
}