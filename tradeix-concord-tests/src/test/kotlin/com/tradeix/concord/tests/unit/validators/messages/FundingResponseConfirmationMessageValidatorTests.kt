package com.tradeix.concord.tests.unit.validators.messages

import com.tradeix.concord.cordapp.supplier.validators.fundingresponses.FundingResponseConfirmationRequestMessageRejectionValidator
import com.tradeix.concord.shared.mockdata.MockFundingResponses
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FundingResponseConfirmationMessageValidatorTests {

    @Test
    fun `FundingResponseAcceptMessageValidator produces the expected validation messages`() {
        val validator = FundingResponseConfirmationRequestMessageRejectionValidator()
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
    fun `FundingResponseAcceptMessageValidator does not throw a ValidationException when the message state is valid`() {
        val message = MockFundingResponses.FUNDING_RESPONSE_ACCEPTANCE_REQUEST_MESSAGE
        val validator = FundingResponseConfirmationRequestMessageRejectionValidator()
        validator.validate(message)
    }
}