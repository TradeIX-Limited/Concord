package com.tradeix.concord.tests.unit.validators.messages

import com.tradeix.concord.shared.mockdata.MockInvoices.INVOICE_REQUEST_MESSAGE
import com.tradeix.concord.shared.validators.InvoiceRequestMessageValidator
import com.tradeix.concord.shared.validators.InvoiceTransactionRequestMessageValidator
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InvoiceTransactionRequestMessageValidatorValidationTests {

    @Test
    fun `InvoiceTransactionRequestMessageValidator produces the expected validation messages`(){
        val validator = InvoiceTransactionRequestMessageValidator()
        val expectedValidationMessages = listOf(
                "Property 'assets' must contain at least one element.",
                "Property 'assets[index].externalId' must not be null, empty or blank.",
                "Property 'assets[index].buyer' must be a valid X500 name.",
                "Property 'assets[index].buyerCompanyReference' must not be null, empty or blank.",
                "Property 'assets[index].supplier' must be a valid X500 name.",
                "Property 'assets[index].supplierCompanyReference' must not be null, empty or blank.",
                "Property 'assets[index].invoiceNumber' must not be null, empty or blank.",
                "Property 'assets[index].reference' must not be null, empty or blank.",
                "Property 'assets[index].dueDate' must not be null.",
                "Property 'assets[index].amount' must not be null.",
                "Property 'assets[index].amount' must be greater than the specified value.",
                "Property 'assets[index].totalOutstanding' must not be null.",
                "Property 'assets[index].totalOutstanding' must be greater than or equal to the specified value.",
                "Property 'assets[index].settlementDate' must not be null.",
                "Property 'assets[index].invoiceDate' must not be null.",
                "Property 'assets[index].invoicePayments' must not be null.",
                "Property 'assets[index].invoiceDilutions' must not be null.",
                "Property 'assets[index].originationNetwork' must not be null, empty or blank.",
                "Property 'assets[index].currency' must not be null.",
                "Property 'assets[index].currency' must be a valid currency code.",
                "Property 'assets[index].siteId' must not be null, empty or blank.",
                "Property 'attachments[index]' must be a valid secure hash.",
                "Property 'observers[index]' must be a valid X500 name."
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
    fun `InvoiceTransactionRequestMessageValidator does not throw a ValidationException when the message state is valid`() {
        val message = INVOICE_REQUEST_MESSAGE
        val validator = InvoiceRequestMessageValidator()
        validator.validate(message)
    }
}