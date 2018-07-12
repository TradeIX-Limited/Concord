package com.tradeix.concord.tests.unit.validators.messages

import com.tradeix.concord.shared.mockdata.MockInvoices.INVOICE_CHANGE_OWNER_REQUEST_MESSAGE
import com.tradeix.concord.shared.validators.OwnershipRequestMessageValidator
import com.tradeix.concord.shared.validators.OwnershipTransactionRequestMessageValidator
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OwnershipTransactionRequestMessageValidatorValidationTests {
    @Test
    fun `OwnershipTransactionRequestMessageValidator produces the expected validation messages`(){
        val validator = OwnershipTransactionRequestMessageValidator()
        val expectedValidationMessages = listOf(
                "Property 'assets' must contain at least one element.",
                "Property 'assets[index].externalId' must not be null, empty or blank.",
                "Property 'assets[index].owner' must not be null, empty or blank.",
                "Property 'assets[index].owner' must be a valid X500 name.",
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
    fun `OwnershipTransactionRequestMessageValidator does not throw a ValidationException when the message state is valid`() {
        val message = INVOICE_CHANGE_OWNER_REQUEST_MESSAGE
        val validator = OwnershipRequestMessageValidator()
        validator.validate(message)
    }
}