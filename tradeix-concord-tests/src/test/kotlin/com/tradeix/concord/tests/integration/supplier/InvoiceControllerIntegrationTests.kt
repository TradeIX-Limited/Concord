package com.tradeix.concord.tests.integration.supplier

import com.tradeix.concord.shared.extensions.canParse
import com.tradeix.concord.tests.integration.ControllerIntegrationTest
import net.corda.core.crypto.SecureHash
import org.junit.Test

class InvoiceControllerIntegrationTests : ControllerIntegrationTest() {

    @Test
    fun `Can issue an invoice`() {
        withDriver {
            val result = issueInvoicesOrThrow()

            assert(result.body.externalIds.containsAll(listOf("INVOICE_1", "INVOICE_2", "INVOICE_3")))
        }
    }

    @Test
    fun `Can amend an invoice`() {
        withDriver {
            issueInvoicesOrThrow()
            val result = amendInvoicesOrThrow()

            assert(result.body.externalIds.containsAll(listOf("INVOICE_1", "INVOICE_2", "INVOICE_3")))
        }
    }

    @Test
    fun `Can transfer an invoice`() {
        withDriver {
            issueInvoicesOrThrow()
            val result = transferInvoicesOrThrow()

            assert(result.body.externalIds.containsAll(listOf("INVOICE_1", "INVOICE_2", "INVOICE_3")))
        }
    }

    @Test
    fun `Can cancel an invoice`() {
        withDriver {
            issueInvoicesOrThrow()
            val result = cancelInvoicesOrThrow()

            assert(result.body.externalIds.containsAll(listOf("INVOICE_1", "INVOICE_2", "INVOICE_3")))
        }
    }

    @Test
    fun `Can get the most recent invoice hash`() {
        withDriver {
            issueInvoicesOrThrow()
            val result = getMostRecentInvoiceHashOrThrow()
            assert(SecureHash.canParse(result.values.single()))
        }
    }

    @Test
    fun `Can get the unique invoice count`() {
        withDriver {
            issueInvoicesOrThrow()
            val result = getUniqueInvoiceCountOrThrow()
            assert(result.values.single() != 0)
        }
    }

    @Test
    fun `Can get unconsumed invoice state by externalId`() {
        withDriver {
            issueInvoicesOrThrow()
            val result = getUnconsumedInvoiceStateByExternalIdOrThrow()
            assert(result.values.first().externalId.equals("INVOICE_1"))
        }
    }

    @Test
    fun `Can get the invoice states`() {
        withDriver {
            issueInvoicesOrThrow()
            val result = getInvoiceStatesOrThrow()

            result.values.forEach {
                it.forEach {
                    assert(it.externalId.equals("INVOICE_${it.invoiceNumber}"))
                }

            }
        }
    }
}