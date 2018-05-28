package com.tradeix.concord.tests.utils

import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.domain.states.PromissoryNoteState
import com.tradeix.concord.shared.domain.states.PurchaseOrderState
import com.tradeix.concord.tests.utils.TestAddresses.BANK_OF_ENGLAND
import com.tradeix.concord.tests.utils.TestAmounts.ONE_POUNDS
import com.tradeix.concord.tests.utils.TestAmounts.ZERO_POUNDS
import com.tradeix.concord.tests.utils.TestIdentities.BUYER_1
import com.tradeix.concord.tests.utils.TestIdentities.GUARANTOR_1
import com.tradeix.concord.tests.utils.TestIdentities.OBLIGEE_1
import com.tradeix.concord.tests.utils.TestIdentities.OBLIGOR_1
import com.tradeix.concord.tests.utils.TestIdentities.SUPPLIER_1
import com.tradeix.concord.tests.utils.TestLocalDateTimes.LOCAL_DATE_TIME_FUTURE_1
import com.tradeix.concord.tests.utils.TestLocalDateTimes.LOCAL_DATE_TIME_FUTURE_2
import com.tradeix.concord.tests.utils.TestLocalDateTimes.LOCAL_DATE_TIME_FUTURE_3
import com.tradeix.concord.tests.utils.TestLocalDateTimes.LOCAL_DATE_TIME_PAST_1
import com.tradeix.concord.tests.utils.TestLocalDateTimes.LOCAL_DATE_TIME_PAST_2
import com.tradeix.concord.tests.utils.TestLocalDateTimes.LOCAL_DATE_TIME_PAST_3
import net.corda.core.contracts.UniqueIdentifier
import java.util.*

object TestStates {

    val PURCHASE_ORDER_STATE = PurchaseOrderState(
            linearId = UniqueIdentifier("PO_EXTERNAL_ID", UUID.fromString("EECBB2AB-8640-49F5-B80C-2DEC95C0F1E4")),
            owner = BUYER_1.party,
            buyer = BUYER_1.party,
            supplier = SUPPLIER_1.party,
            reference = "PURCHASE ORDER REFERENCE",
            amount = ONE_POUNDS,
            created = LOCAL_DATE_TIME_PAST_1,
            earliestShipment = LOCAL_DATE_TIME_PAST_2,
            latestShipment = LOCAL_DATE_TIME_PAST_3,
            portOfShipment = "PORT OF SHIPMENT",
            descriptionOfGoods = "DESCRIPTION OF GOODS",
            deliveryTerms = "DELIVERY TERMS"
    )

    val INVOICE_STATE = InvoiceState(
            linearId = UniqueIdentifier("INV_EXTERNAL_ID", UUID.fromString("B7EA4592-CA47-4F8E-93C3-5FB30F569458")),
            owner = SUPPLIER_1.party,
            buyer = BUYER_1.party,
            supplier = SUPPLIER_1.party,
            invoiceVersion = "INVOICE VERSION",
            invoiceVersionDate = LOCAL_DATE_TIME_PAST_1,
            tixInvoiceVersion = 1,
            invoiceNumber = "INVOICE NUMBER",
            invoiceType = "INVOICE TYPE",
            reference = "INVOICE REFERENCE",
            dueDate = LOCAL_DATE_TIME_FUTURE_1,
            offerId = null,
            amount = ONE_POUNDS,
            totalOutstanding = ONE_POUNDS,
            created = LOCAL_DATE_TIME_FUTURE_2,
            updated = LOCAL_DATE_TIME_FUTURE_2,
            expectedSettlementDate = LOCAL_DATE_TIME_FUTURE_3,
            settlementDate = null,
            mandatoryReconciliationDate = null,
            invoiceDate = LOCAL_DATE_TIME_FUTURE_2,
            status = "STATUS",
            rejectionReason = "REJECTION REASON",
            eligibleValue = ONE_POUNDS,
            invoicePurchaseValue = ONE_POUNDS,
            tradeDate = null,
            tradePaymentDate = null,
            invoicePayments = ZERO_POUNDS,
            invoiceDilutions = ZERO_POUNDS,
            cancelled = false,
            closeDate = null,
            originationNetwork = "ORIGINATION NETWORK",
            currency = Currency.getInstance("GBP"),
            siteId = "SITE ID",
            purchaseOrderNumber = "PURCHASE ORDER NUMBER",
            purchaseOrderId = "PURCHASE ORDER ID",
            composerProgramId = 1
    )

    val PROMISSORY_NOTE_STATE = PromissoryNoteState(
            linearId = UniqueIdentifier("PN_EXTERNAL_ID", UUID.fromString("5B62807C-72BA-402C-A701-94713181660B")),
            owner = OBLIGEE_1.party,
            obligor = OBLIGOR_1.party,
            obligee = OBLIGEE_1.party,
            guarantor = GUARANTOR_1.party,
            amount = ONE_POUNDS,
            placeOfIssue = BANK_OF_ENGLAND,
            dateOfIssue = LOCAL_DATE_TIME_PAST_1,
            dateOfMaturity = LOCAL_DATE_TIME_FUTURE_1
    )
}