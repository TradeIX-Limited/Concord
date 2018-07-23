package com.tradeix.concord.tests.unit.validators.messages

import com.tradeix.concord.cordapp.supplier.validators.invoices.InvoiceRequestMessageValidator
import com.tradeix.concord.shared.mockdata.MockInvoices.INVOICE_REQUEST_MESSAGE
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InvoiceRequestMessageValidatorTests {

    @Test
    fun `InvoiceRequestMessageValidator produces the expected validation messages`() {
        val validator = InvoiceRequestMessageValidator()
        val actualValidationMessages: Iterable<String> = validator.getValidationMessages()
        val expectedValidationMessages = listOf(
                "Property 'externalId' must not be null, empty or blank.",
                "Property 'buyer' must be a valid X500 name.",
                "Property 'buyerCompanyReference' must not be null, empty or blank.",
                "Property 'supplier' must be a valid X500 name.",
                "Property 'supplierCompanyReference' must not be null, empty or blank.",
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

        assertEquals(expectedValidationMessages.count(), actualValidationMessages.count())
        expectedValidationMessages.forEach {
            assertTrue {
                actualValidationMessages.contains(it)
            }
        }
    }

    @Test
    fun `InvoiceRequestMessageValidator does not throw a ValidationException when the message state is valid`() {
        val message = INVOICE_REQUEST_MESSAGE
        val validator = InvoiceRequestMessageValidator()
        validator.validate(message)
    }
}