package com.tradeix.concord.tests.utils

import com.tradeix.concord.shared.messages.invoices.InvoiceCancellationRequestMessage
import com.tradeix.concord.shared.messages.invoices.InvoiceIssuanceRequestMessage
import com.tradeix.concord.shared.messages.invoices.InvoiceOwnershipRequestMessage
import com.tradeix.concord.shared.messages.purchaseorders.PurchaseOrderIssuanceRequestMessage
import com.tradeix.concord.tests.utils.TestInstants.INSTANT_FUTURE_1
import com.tradeix.concord.tests.utils.TestInstants.INSTANT_PAST_1
import com.tradeix.concord.tests.utils.TestInstants.INSTANT_PAST_2
import com.tradeix.concord.tests.utils.TestInstants.INSTANT_PAST_3
import java.math.BigDecimal

object TestMessages {

    val INVOICE_CANCELLATION_REQUEST_MESSAGE = InvoiceCancellationRequestMessage(
            externalId = "EXTERNAL_ID"
    )

    val INVOICE_OWNERSHIP_REQUEST_MESSAGE = InvoiceOwnershipRequestMessage(
            externalId = "EXTERNAL_ID",
            owner = "O=TradeIX Test Funder 1, L=London, C=GB"
    )

    val INVOICE_ISSUANCE_REQUEST_MESSAGE = InvoiceIssuanceRequestMessage(
            externalId = "INV_EXTERNAL_ID",
            attachmentId = "485157181EF8D087C3117F5F68E2DD21DCF318B277AE571AF6D6ACBF433DACF3",
            buyer = "O=TradeIX Test Buyer 1, L=London, C=GB",
            supplier = "O=TradeIX Test Supplier 1, L=London, C=GB",
            conductor = "O=TradeIX Test Conductor 1, L=London, C=GB",
            invoiceVersion = "INV_VER_001",
            invoiceVersionDate = INSTANT_PAST_1,
            tixInvoiceVersion = 1,
            invoiceNumber = "INV_NUM_001",
            invoiceType = "INVOICE_TYPE",
            reference = "INVOICE_REFERENCE",
            dueDate = INSTANT_FUTURE_1,
            offerId = null,
            amount = BigDecimal.TEN,
            totalOutstanding = BigDecimal.TEN,
            created = INSTANT_PAST_1,
            updated = INSTANT_PAST_1,
            expectedSettlementDate = INSTANT_PAST_1,
            settlementDate = null,
            mandatoryReconciliationDate = null,
            invoiceDate = INSTANT_PAST_1,
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

    val PURCHASE_ORDER_ISSUANCE_REQUEST_MESSAGE = PurchaseOrderIssuanceRequestMessage(
            externalId = "PO_EXTERNAL_ID",
            attachmentId = "8E2A8777D0B5F0A70CFEBEFA218C65B61289150C6F5472F9C060BA46E960F667",
            buyer = "O=TradeIX Test Buyer 1, L=London, C=GB",
            supplier = "O=TradeIX Test Supplier 1, L=London, C=GB",
            conductor = "O=TradeIX Test Conductor 1, L=London, C=GB",
            reference = "PURCHASE_ORDER_REFERENCE",
            value = BigDecimal.TEN,
            currency = "GBP",
            created = INSTANT_PAST_1,
            earliestShipment = INSTANT_PAST_2,
            latestShipment = INSTANT_PAST_3,
            portOfShipment = "PORT OF SHIPMENT",
            descriptionOfGoods = "DESCRIPTION OF GOODS",
            deliveryTerms = "DELIVERY TERMS"
    )
}