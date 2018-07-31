package com.tradeix.concord.tests.integration.supplier

import com.tradeix.concord.shared.extensions.canParse
import com.tradeix.concord.tests.integration.ControllerIntegrationTest
import net.corda.core.crypto.SecureHash
import org.junit.Ignore
import org.junit.Test


class FundingResponseControllerIntegrationTests : ControllerIntegrationTest() {

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

    @Ignore
    fun `Can get the most recent funding response hash`() { // TODO : INVESTIGATE
        withDriver {
            issueInvoicesOrThrow()
            issueFundingResponseOrThrow()
            val result = getSuppliersMostRecentFundingResponseHashOrThrow()

            assert(SecureHash.canParse(result.values.single()))
        }
    }

    @Ignore
    fun `Can get the unique funding response count`() { // TODO : INVESTIGATE
        withDriver {
            issueInvoicesOrThrow()
            issueFundingResponseOrThrow()
            val result = getSuppliersUniqueFundingResponseCountOrThrow()
            println("RESPUESTA count: " + result.getValue("count").toString())

            assert(result.values.single() != 0)
        }
    }

    @Ignore
    fun `Can get unconsumed funding response state by externalId`() { // TODO : INVESTIGATE
        withDriver {
            issueInvoicesOrThrow()
            issueFundingResponseOrThrow()
            val result = getSuppliersUnconsumedFundingResponseStateByExternalIdOrThrow()

            assert(result.externalId == "FUNDING_RESPONSE_1")
        }
    }

    @Ignore
    fun `Can get the funding response states`() { // TODO : INVESTIGATE
        withDriver {
            issueInvoicesOrThrow()
            issueFundingResponseOrThrow()
            val result = getSuppliersFundingResponseStatesOrThrow()

            assert(result.map { it.externalId }.containsAll(listOf("FUNDING_RESPONSE_1")))
        }
    }

    @Ignore
    fun `The status is correctly changed`() { // TODO : INVESTIGATE
        withDriver {
            issueInvoicesOrThrow()
            issueFundingResponseOrThrow()
            acceptFundingResponseOrThrow()
            val result = getSuppliersFundingResponseStatesOrThrow()

            assert(result.map { it.status }.containsAll(listOf("ACCEPTED")))
        }
    }
}