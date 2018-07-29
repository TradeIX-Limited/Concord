package com.tradeix.concord.tests.integration.funder

import com.tradeix.concord.shared.extensions.canParse
import com.tradeix.concord.tests.integration.ControllerIntegrationTest
import net.corda.core.crypto.SecureHash
import org.junit.Test

class FunderFundingResponseControllerIntegrationTests : ControllerIntegrationTest() {

    @Test
    fun `Can issue a funding response`() {
        withDriver {
            issueInvoicesOrThrow()
            val result = issueFundingResponseOrThrow()

            assert(result.body.externalId == "FUNDING_RESPONSE_1")
        }
    }

    @Test
    fun `Can get the most recent funding response hash`() {
        withDriver {
            issueInvoicesOrThrow()
            issueFundingResponseOrThrow()
            val result = getFundersMostRecentFundingResponseHashOrThrow()
            assert(SecureHash.canParse(result.values.single()))
        }
    }

    @Test
    fun `Can get the unique funding response count`() {
        withDriver {
            issueInvoicesOrThrow()
            issueFundingResponseOrThrow()
            val result = getFundersUniqueFundingResponseCountOrThrow()
            assert(result.values.single() != 0)
        }
    }

    @Test
    fun `Can get unconsumed funding response state by externalId`() {
        withDriver {
            issueInvoicesOrThrow()
            issueFundingResponseOrThrow()
            val result = getFundersUnconsumedFundingResponseStateByExternalIdOrThrow()
            assert(result.externalId.equals("FUNDING_RESPONSE_1"))
        }
    }

    @Test
    fun `Can get the funding response states`() {
        withDriver {
            issueInvoicesOrThrow()
            issueFundingResponseOrThrow()
            val result = getFundersFundingResponseStatesOrThrow()

            result.forEach {
                assert(it.externalId.equals("FUNDING_RESPONSE_${it.externalId.last()}"))
            }
        }
    }
}