package com.tradeix.concord.tests.unit.validators.messages

import com.tradeix.concord.shared.mockdata.MockFundingResponses
import com.tradeix.concord.shared.validators.FundingResponseRejectMessageValidator
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FundingResponseRejectMessageValidatorValidationTests {

    @Test
    fun `FundingResponseRejectMessageValidator produces the expected validation messages`(){
        val validator = FundingResponseRejectMessageValidator()
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
    fun `FundingResponseRejectMessageValidator does not throw a ValidationException when the message state is valid`() {
        val message = MockFundingResponses.FUNDING_RESPONSE_REJECTION_REQUEST_MESSAGE
        val validator = FundingResponseRejectMessageValidator()
        validator.validate(message)
    }
}