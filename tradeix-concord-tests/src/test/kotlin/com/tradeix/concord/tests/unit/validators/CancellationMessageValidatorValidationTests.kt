package com.tradeix.concord.tests.unit.validators

import com.tradeix.concord.shared.mockdata.MockInvoices.INVOICE_CANCELLATION_REQUEST_MESSAGE
import com.tradeix.concord.shared.validators.CancellationRequestMessageValidator
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CancellationMessageValidatorValidationTests {

    @Test
    fun `CancellationMessageValidator produces the expected validation messages`() {
        val validator = CancellationRequestMessageValidator()
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
    fun `CancellationMessageValidator does not throw a ValidationException when the message state is valid`() {
        val message = INVOICE_CANCELLATION_REQUEST_MESSAGE
        val validator = CancellationRequestMessageValidator()
        validator.validate(message)
    }
}