package com.tradeix.concord.tests.integration.funder

import com.tradeix.concord.tests.integration.ControllerIntegrationTest
import org.junit.Test

class FundingResponseControllerIntegrationTests : ControllerIntegrationTest() {

    @Test
    fun `Can issue a funding response`() {
        withDriver {
            issueInvoicesOrThrow()
            val result = issueFundingResponseOrThrow()

            assert(result.body.externalId == "FUNDING_RESPONSE_1")
        }
    }
}