package com.tradeix.concord.tests.unit.validators.messages

import com.tradeix.concord.cordapp.supplier.validators.invoices.InvoiceTransferRequestMessageValidator
import com.tradeix.concord.shared.mockdata.MockInvoices.INVOICE_CHANGE_OWNER_REQUEST_MESSAGE
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OwnershipRequestMessageValidatorValidationTests {

    @Test
    fun `OwnershipRequestMessageValidator produces the expected validation messages`() {
        val validator = InvoiceTransferRequestMessageValidator()
        val expectedValidationMessages = listOf(
                "Property 'externalId' must not be null, empty or blank.",
                "Property 'owner' must not be null, empty or blank.",
                "Property 'owner' must be a valid X500 name."
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
    fun `OwnershipRequestMessageValidator does not throw a ValidationException when the message state is valid`() {
        val message = INVOICE_CHANGE_OWNER_REQUEST_MESSAGE
        val validator = InvoiceTransferRequestMessageValidator()
        validator.validate(message)
    }
}