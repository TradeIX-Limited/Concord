package com.tradeix.concord.tests.unit.validators.messages

import com.tradeix.concord.cordapp.supplier.validators.invoices.InvoiceCancellationRequestMessageValidator
import com.tradeix.concord.shared.mockdata.MockInvoices
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CancellationRequestMessageValidatorValidationTests {

    @Test
    fun `CancellationRequestMessageValidator produces the expected validation messages`() {
        val validator = InvoiceCancellationRequestMessageValidator()
        val expectedValidationMessages = listOf(
                "Property 'externalId' must not be null, empty or blank."
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
    fun `CancellationRequestMessageValidator does not throw a ValidationException when the message state is valid`() {
        val message = MockInvoices.INVOICE_CANCELLATION_REQUEST_MESSAGE
        val validator = InvoiceCancellationRequestMessageValidator()
        validator.validate(message)
    }
}