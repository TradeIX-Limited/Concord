package com.tradeix.concord.shared.mockdata

import com.tradeix.concord.shared.domain.states.InvoiceEligibilityState
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.domain.states.PromissoryNoteState
import com.tradeix.concord.shared.domain.states.PurchaseOrderState
import com.tradeix.concord.shared.mockdata.MockAddresses.BANK_OF_ENGLAND
import com.tradeix.concord.shared.mockdata.MockAmounts.ONE_POUNDS
import com.tradeix.concord.shared.mockdata.MockAmounts.ZERO_POUNDS
import com.tradeix.concord.shared.mockdata.MockIdentities.BUYER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.FUNDER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.GUARANTOR_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.OBLIGEE_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.OBLIGOR_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.SUPPLIER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockLocalDateTimes.LOCAL_DATE_TIME_FUTURE_1
import com.tradeix.concord.shared.mockdata.MockLocalDateTimes.LOCAL_DATE_TIME_FUTURE_3
import com.tradeix.concord.shared.mockdata.MockLocalDateTimes.LOCAL_DATE_TIME_PAST_1
import com.tradeix.concord.shared.mockdata.MockLocalDateTimes.LOCAL_DATE_TIME_PAST_2
import com.tradeix.concord.shared.mockdata.MockLocalDateTimes.LOCAL_DATE_TIME_PAST_3
import net.corda.core.contracts.UniqueIdentifier
import java.util.*

object MockStates {

    val PURCHASE_ORDER_STATE = PurchaseOrderState(
            linearId = UniqueIdentifier(
                    "PURCHASE_ORDER_EXTERNAL_ID",
                    UUID.fromString("00000000-0000-4000-0000-000000000001")
            ),
            owner = BUYER_1_IDENTITY.party,
            buyer = BUYER_1_IDENTITY.party,
            supplier = SUPPLIER_1_IDENTITY.party,
            reference = "PURCHASE ORDER REFERENCE",
            amount = ONE_POUNDS,
            earliestShipment = LOCAL_DATE_TIME_PAST_2,
            latestShipment = LOCAL_DATE_TIME_PAST_3,
            portOfShipment = "PORT OF SHIPMENT",
            descriptionOfGoods = "DESCRIPTION OF GOODS",
            deliveryTerms = "DELIVERY TERMS"
    )

    val INVOICE_STATE = InvoiceState(
            linearId = UniqueIdentifier(
                    "INVOICE_EXTERNAL_ID",
                    UUID.fromString("00000000-0000-4000-0000-000000000002")
            ),
            owner = SUPPLIER_1_IDENTITY.party,
            buyer = BUYER_1_IDENTITY.party,
            supplier = SUPPLIER_1_IDENTITY.party,
            invoiceNumber = "INVOICE NUMBER",
            reference = "INVOICE REFERENCE",
            dueDate = LOCAL_DATE_TIME_FUTURE_1,
            amount = ONE_POUNDS,
            totalOutstanding = ONE_POUNDS,
            settlementDate = LOCAL_DATE_TIME_FUTURE_3,
            invoiceDate = LOCAL_DATE_TIME_PAST_2,
            invoicePayments = ZERO_POUNDS,
            invoiceDilutions = ZERO_POUNDS,
            originationNetwork = "ORIGINATION NETWORK",
            siteId = "SITE ID"
    )

    val INVOICE_ELIGIBILITY_STATE = InvoiceEligibilityState(
            linearId = UniqueIdentifier(
                    "INVOICE_ELIGIBILITY_EXTERNAL_ID",
                    UUID.fromString("00000000-0000-4000-0000-000000000003")
            ),
            invoiceExternalId = "INVOICE_EXTERNAL_ID",
            supplier = SUPPLIER_1_IDENTITY.party,
            funder = FUNDER_1_IDENTITY.party,
            eligible = true
    )

    val PROMISSORY_NOTE_STATE = PromissoryNoteState(
            linearId = UniqueIdentifier(
                    "PROMISSORY_NOTE_EXTERNAL_ID",
                    UUID.fromString("00000000-0000-4000-0000-000000000004")
            ),
            owner = OBLIGEE_1_IDENTITY.party,
            obligor = OBLIGOR_1_IDENTITY.party,
            obligee = OBLIGEE_1_IDENTITY.party,
            guarantor = GUARANTOR_1_IDENTITY.party,
            amount = ONE_POUNDS,
            placeOfIssue = BANK_OF_ENGLAND,
            dateOfIssue = LOCAL_DATE_TIME_PAST_1,
            dateOfMaturity = LOCAL_DATE_TIME_FUTURE_1
    )
}