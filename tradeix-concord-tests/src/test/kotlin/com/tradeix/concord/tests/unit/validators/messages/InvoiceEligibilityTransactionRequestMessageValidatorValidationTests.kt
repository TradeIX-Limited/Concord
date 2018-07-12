package com.tradeix.concord.tests.unit.validators.messages

import com.tradeix.concord.shared.mockdata.MockInvoiceEligibility.INVOICE_ELIGIBILITY_REQUEST_MESSAGE
import com.tradeix.concord.shared.validators.InvoiceEligibilityMessageValidator
import com.tradeix.concord.shared.validators.InvoiceEligibilityTransactionRequestMessageValidator
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InvoiceEligibilityTransactionRequestMessageValidatorValidationTests {

    @Test
    fun `InvoiceEligibilityTransactionRequestMessageValidator produces the expected validation messages`(){
        val validator = InvoiceEligibilityTransactionRequestMessageValidator()
        val expectedValidationMessages = listOf(
                "Property 'assets' must contain at least one element.",
                "Property 'assets[index].invoiceId' must not be null, empty or blank.",
                "Property 'assets[index].supplier' must not be null, empty or blank.",
                "Property 'assets[index].supplier' must be a valid X500 name.",
                "Property 'assets[index].eligible' must not be null."
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
    fun `InvoiceEligibilityTransactionRequestMessageValidator does not throw a ValidationException when the message state is valid`() {
        val message = INVOICE_ELIGIBILITY_REQUEST_MESSAGE
        val validator = InvoiceEligibilityMessageValidator()
        validator.validate(message)
    }
}