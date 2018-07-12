package com.tradeix.concord.tests.unit.validators.messages

import com.tradeix.concord.shared.mockdata.MockInvoices
import com.tradeix.concord.shared.validators.CancellationRequestMessageValidator
import com.tradeix.concord.shared.validators.CancellationTransactionRequestMessageValidator
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CancellationTransactionRequestMessageValidatorValidationTests {

    @Test
    fun `CancellationTransactionRequestMessageValidator produces the expected validation messages`(){
        val validator = CancellationTransactionRequestMessageValidator()
        val expectedValidationMessages = listOf(
                "Property 'assets' must contain at least one element.",
                "Property 'assets[index].externalId' must not be null, empty or blank.",
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
    fun `CancellationTransactionRequestMessageValidator does not throw a ValidationException when the message state is valid`() {
        val message = MockInvoices.INVOICE_CANCELLATION_REQUEST_MESSAGE
        val validator = CancellationRequestMessageValidator()
        validator.validate(message)
    }
}