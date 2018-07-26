package com.tradeix.concord.tests.integration.supplier

import com.tradeix.concord.tests.integration.ControllerIntegrationTest
import org.junit.Test


class SupplierFundingResponseControllerIntegrationTests : ControllerIntegrationTest() {

    @Test
    fun `Can accept a funding response`() {
        withDriver {
            issueInvoicesOrThrow()
            issueFundingResponseOrThrow()
            val result = acceptFundingResponseOrThrow()

            assert(result.body.externalId == "FUNDING_RESPONSE_1")
        }
    }

    @Test
    fun `Can reject a funding response`() {
        withDriver {
            issueInvoicesOrThrow()
            issueFundingResponseOrThrow()
            val result = rejectFundingResponseOrThrow()

            assert(result.body.externalId == "FUNDING_RESPONSE_1")
        }
    }
}