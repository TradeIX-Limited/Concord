package com.tradeix.concord.tests.integration.supplier

import com.tradeix.concord.tests.integration.ControllerIntegrationTest
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
}