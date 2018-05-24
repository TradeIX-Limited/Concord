package com.tradeix.concord.tests.unit.validators

import com.tradeix.concord.shared.validators.OwnershipRequestMessageValidator
import com.tradeix.concord.tests.utils.TestMessages
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OwnershipRequestMessageValidatorValidationTests {

    @Test
    fun `OwnershipMessageValidator produces the expected validation messages`() {
        val validator = OwnershipRequestMessageValidator()
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
    fun `OwnershipMessageValidator does not throw a ValidationException when the message state is valid`() {
        val message = TestMessages.INVOICE_OWNERSHIP_REQUEST_MESSAGE
        val validator = OwnershipRequestMessageValidator()
        validator.validate(message)
    }
}