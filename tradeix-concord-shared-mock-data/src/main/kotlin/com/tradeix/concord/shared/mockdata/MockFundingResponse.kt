package com.tradeix.concord.shared.mockdata

import com.tradeix.concord.shared.messages.fundingresponse.FundingResponseRequestMessage
import com.tradeix.concord.shared.mockdata.MockAmounts.ONE_POUNDS
import com.tradeix.concord.shared.mockdata.MockIdentities.FUNDER_1_IDENTITY
import com.tradeix.concord.shared.mockdata.MockIdentities.SUPPLIER_1_IDENTITY
import net.corda.core.identity.CordaX500Name

object MockFundingResponse {

    fun createFundingResponse(
            funder: CordaX500Name?,
            supplier: CordaX500Name?) : FundingResponseRequestMessage =
             FUNDING_RESPONSE_REQUEST_MESSAGE.copy(funder = funder.toString(), supplier = supplier.toString())

    val FUNDING_RESPONSE_REQUEST_MESSAGE =  FundingResponseRequestMessage(
            externalId = "FUND_RESPONSE_001",
            fundingRequestExternalId = "FUNDING_REQUEST_ID",
            invoiceExternalIds = listOf("1"),
            supplier = SUPPLIER_1_IDENTITY.toString(),
            funder = FUNDER_1_IDENTITY.toString(),
            purchaseValue  = ONE_POUNDS.toDecimal(),
            currency = ONE_POUNDS.token.currencyCode
    )

}