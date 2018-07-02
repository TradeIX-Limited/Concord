package com.tradeix.concord.shared.mockdata

import com.tradeix.concord.shared.messages.fundingresponse.FundingResponseAcceptanceRequestMessage
import com.tradeix.concord.shared.messages.fundingresponse.FundingResponseRejectionRequestMessage
import com.tradeix.concord.shared.messages.fundingresponse.FundingResponseRequestMessage
import com.tradeix.concord.shared.mockdata.MockAmounts.ONE_POUNDS
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.FUNDER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.SUPPLIER_1_NAME

object MockFundingResponses {

    val FUNDING_RESPONSE_REQUEST_MESSAGE = FundingResponseRequestMessage(
            externalId = "FUNDING_RESPONSE_1",
            fundingRequestExternalId = null, // TODO : Add test funding request
            invoiceExternalIds = listOf("INVOICE_1", "INVOICE_2", "INVOICE_3"),
            supplier = SUPPLIER_1_NAME.toString(),
            funder = FUNDER_1_NAME.toString(),
            purchaseValue = ONE_POUNDS.toDecimal(),
            currency = ONE_POUNDS.token.currencyCode
    )

    val FUNDING_RESPONSE_ACCEPTANCE_REQUEST_MESSAGE = FundingResponseAcceptanceRequestMessage(
            externalId = "FUNDING_RESPONSE_1"
    )

    val FUNDING_RESPONSE_REJECTION_REQUEST_MESSAGE = FundingResponseRejectionRequestMessage(
            externalId = "FUNDING_RESPONSE_1"
    )
}