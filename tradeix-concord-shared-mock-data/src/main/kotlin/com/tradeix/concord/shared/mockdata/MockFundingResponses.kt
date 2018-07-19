package com.tradeix.concord.shared.mockdata

import com.tradeix.concord.cordapp.funder.messages.fundingresponses.FundingResponseIssuanceRequestMessage
import com.tradeix.concord.cordapp.supplier.messages.fundingresponses.FundingResponseConfirmationRequestMessage
import com.tradeix.concord.shared.mockdata.MockAmounts.ONE_POUNDS
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.FUNDER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.SUPPLIER_1_NAME

object MockFundingResponses {

    val FUNDING_RESPONSE_ISSUANCE_REQUEST_MESSAGE = FundingResponseIssuanceRequestMessage(
            externalId = "FUNDING_RESPONSE_1",
            fundingRequestExternalId = null, // TODO : Add test funding request
            invoiceExternalIds = listOf("INVOICE_1", "INVOICE_2", "INVOICE_3"),
            supplier = SUPPLIER_1_NAME.toString(),
            funder = FUNDER_1_NAME.toString(),
            purchaseValue = ONE_POUNDS.toDecimal(),
            currency = ONE_POUNDS.token.currencyCode,
            advanceInvoiceValue = ONE_POUNDS.toDecimal(),
            discountValue = ONE_POUNDS.toDecimal(),
            baseRate = ONE_POUNDS.toDecimal()
    )

    val FUNDING_RESPONSE_ACCEPTANCE_REQUEST_MESSAGE = FundingResponseConfirmationRequestMessage(
            externalId = "FUNDING_RESPONSE_1"
    )

    val FUNDING_RESPONSE_REJECTION_REQUEST_MESSAGE = FundingResponseConfirmationRequestMessage(
            externalId = "FUNDING_RESPONSE_1"
    )
}