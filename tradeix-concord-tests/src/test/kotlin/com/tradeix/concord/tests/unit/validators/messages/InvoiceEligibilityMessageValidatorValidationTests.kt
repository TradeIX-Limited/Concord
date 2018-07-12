package com.tradeix.concord.tests.unit.validators.messages

import com.tradeix.concord.shared.mockdata.MockInvoiceEligibility
import com.tradeix.concord.shared.validators.InvoiceEligibilityMessageValidator
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InvoiceEligibilityMessageValidatorValidationTests {
    @Test
    fun `InvoiceEligibilityMessageValidator produces the expected validation messages`(){
        val validator = InvoiceEligibilityMessageValidator()
        val expectedValidationMessages = listOf(
                "Property 'invoiceId' must not be null, empty or blank.",
                "Property 'supplier' must not be null, empty or blank.",
                "Property 'supplier' must be a valid X500 name.",
                "Property 'eligible' must not be null."
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
    fun `InvoiceEligibilityMessageValidator does not throw a ValidationException when the message state is valid`() {
        val message = MockInvoiceEligibility.INVOICE_ELIGIBILITY_REQUEST_MESSAGE
        val validator = InvoiceEligibilityMessageValidator()
        validator.validate(message)
    }
}