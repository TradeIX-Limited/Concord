package com.tradeix.concord.shared.mockdata

import com.tradeix.concord.shared.domain.enumerations.FundingResponseStatus
import com.tradeix.concord.shared.domain.states.FundingResponseState
import com.tradeix.concord.shared.domain.states.InvoiceState
import com.tradeix.concord.shared.mockdata.MockAmounts.ONE_POUNDS
import com.tradeix.concord.shared.mockdata.MockAmounts.ZERO_POUNDS
import com.tradeix.concord.shared.mockdata.MockIdentities.FUNDER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.SUPPLIER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockLocalDateTimes.LOCAL_DATE_TIME_FUTURE_1
import com.tradeix.concord.shared.mockdata.MockLocalDateTimes.LOCAL_DATE_TIME_FUTURE_3
import com.tradeix.concord.shared.mockdata.MockLocalDateTimes.LOCAL_DATE_TIME_FUTURE_4
import com.tradeix.concord.shared.mockdata.MockLocalDateTimes.LOCAL_DATE_TIME_FUTURE_5
import com.tradeix.concord.shared.mockdata.MockLocalDateTimes.LOCAL_DATE_TIME_PAST_2
import com.tradeix.concord.shared.mockdata.MockParticipants.BUYER_1_PARTICIPANT
import com.tradeix.concord.shared.mockdata.MockParticipants.SUPPLIER_1_PARTICIPANT
import net.corda.core.contracts.UniqueIdentifier
import java.time.LocalDateTime
import java.util.*

object MockStates {

    val INVOICE_STATE = InvoiceState(
            linearId = UniqueIdentifier(
                    "INVOICE_EXTERNAL_ID",
                    UUID.fromString("00000000-0000-4000-0000-000000000002")
            ),
            owner = SUPPLIER_1_IDENTITY.party,
            buyer = BUYER_1_PARTICIPANT,
            supplier = SUPPLIER_1_PARTICIPANT,
            invoiceNumber = "INVOICE NUMBER",
            invoiceVersion = "1.0",
            submitted = LocalDateTime.now(),
            reference = "INVOICE REFERENCE",
            dueDate = LOCAL_DATE_TIME_FUTURE_1,
            amount = ONE_POUNDS,
            totalOutstanding = ONE_POUNDS,
            settlementDate = LOCAL_DATE_TIME_FUTURE_3,
            invoiceDate = LOCAL_DATE_TIME_PAST_2,
            invoicePayments = ZERO_POUNDS,
            invoiceDilutions = ZERO_POUNDS,
            originationNetwork = "ORIGINATION NETWORK",
            siteId = "SITE ID",
            tradeDate = LOCAL_DATE_TIME_FUTURE_4,
            tradePaymentDate = LOCAL_DATE_TIME_FUTURE_5
    )

    val FUNDING_RESPONSE_STATE = FundingResponseState(
            linearId = UniqueIdentifier(
                    "FUNDING_RESPONSE_EXTERNAL_ID",
                    UUID.fromString("00000000-0000-4000-0000-000000000005")
            ),
            fundingRequestLinearId = null,
            invoiceLinearIds = listOf(UniqueIdentifier(
                    "INVOICE_EXTERNAL_ID",
                    UUID.fromString("00000000-0000-4000-0000-000000000002")
            )),
            supplier = SUPPLIER_1_IDENTITY.party,
            funder = FUNDER_1_IDENTITY.party,
            purchaseValue = ONE_POUNDS,
            status = FundingResponseStatus.PENDING,
            advanceInvoiceValue = ONE_POUNDS,
            discountValue = ONE_POUNDS,
            baseRate = ONE_POUNDS.toDecimal(),
            bankAccount = null
    )
}