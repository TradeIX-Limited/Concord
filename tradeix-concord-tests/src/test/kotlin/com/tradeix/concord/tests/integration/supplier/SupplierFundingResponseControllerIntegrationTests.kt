package com.tradeix.concord.tests.integration.supplier

import com.tradeix.concord.shared.extensions.canParse
import com.tradeix.concord.tests.integration.ControllerIntegrationTest
import net.corda.core.crypto.SecureHash
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

    @Test
    fun `Can get the most recent funding response hash`() {
        withDriver {
            issueInvoicesOrThrow()
            issueFundingResponseOrThrow()
            val result = getSuppliersMostRecentFundingResponseHashOrThrow()
            assert(SecureHash.canParse(result.values.single()))
        }
    }

    @Test
    fun `Can get the unique funding response count`() {
        withDriver {
            issueInvoicesOrThrow()
            issueFundingResponseOrThrow()
            val result = getSuppliersUniqueFundingResponseCountOrThrow()
            println("RESPUESTA count: "+result.values.toString())
            assert(result.values.single() != 0)
        }
    }

    @Test
    fun `Can get unconsumed funding response state by externalId`() {
        withDriver {
            issueInvoicesOrThrow()
            issueFundingResponseOrThrow()
            val result = getSuppliersUnconsumedFundingResponseStateByExternalIdOrThrow()
            assert(result.externalId.equals("FUNDING_RESPONSE_1"))
        }
    }

    @Test
    fun `Can get the funding response states`() {
        withDriver {
            issueInvoicesOrThrow()
            issueFundingResponseOrThrow()
            val result = getSuppliersFundingResponseStatesOrThrow()

            result.forEach {
                assert(it.externalId.equals("FUNDING_RESPONSE_${it.externalId.last()}"))
            }
        }
    }
}