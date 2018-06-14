package com.tradeix.concord.tests.unit.validators

import com.tradeix.concord.shared.mockdata.MockInvoices.INVOICE_REQUEST_MESSAGE
import com.tradeix.concord.shared.validators.InvoiceRequestMessageValidator
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InvoiceRequestMessageValidatorValidationTests {

    @Test
    fun `InvoiceMessageValidator produces the expected validation messages`() {
        val validator = InvoiceRequestMessageValidator()
        val expectedValidationMessages = listOf(
                "Property 'externalId' must not be null, empty or blank.",
                "Property 'buyer' must be a valid X500 name.",
                "Property 'supplier' must be a valid X500 name.",
                "Property 'invoiceNumber' must not be null, empty or blank.",
                "Property 'reference' must not be null, empty or blank.",
                "Property 'dueDate' must not be null.",
                "Property 'amount' must not be null.",
                "Property 'amount' must be greater than the specified value.",
                "Property 'totalOutstanding' must not be null.",
                "Property 'totalOutstanding' must be greater than or equal to the specified value.",
                "Property 'settlementDate' must not be null.",
                "Property 'invoiceDate' must not be null.",
                "Property 'invoicePayments' must not be null.",
                "Property 'invoiceDilutions' must not be null.",
                "Property 'originationNetwork' must not be null, empty or blank.",
                "Property 'currency' must not be null.",
                "Property 'currency' must be a valid currency code.",
                "Property 'siteId' must not be null, empty or blank."
        )
        val actualValidationMessages: Iterable<String> = validator.getValidationMessages()

        assertEquals(expectedValidationMessages.count(), actualValidationMessages.count())
        expectedValidationMessages.forEach {
            assertTrue {
                actualValidationMessages.contains(it)
            }
        }
    }

    @Test
    fun `InvoiceMessageValidator does not throw a ValidationException when the message state is valid`() {
        val message = INVOICE_REQUEST_MESSAGE
        val validator = InvoiceRequestMessageValidator()
        validator.validate(message)
    }
}