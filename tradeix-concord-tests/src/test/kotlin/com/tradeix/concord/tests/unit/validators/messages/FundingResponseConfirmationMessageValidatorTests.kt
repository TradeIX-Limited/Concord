package com.tradeix.concord.tests.unit.validators.messages

import com.tradeix.concord.cordapp.supplier.validators.fundingresponses.FundingResponseConfirmationRequestMessageAcceptanceValidator
import com.tradeix.concord.cordapp.supplier.validators.fundingresponses.FundingResponseConfirmationRequestMessageRejectionValidator
import com.tradeix.concord.shared.mockdata.MockFundingResponses.FUNDING_RESPONSE_ACCEPTANCE_REQUEST_MESSAGE
import com.tradeix.concord.shared.mockdata.MockFundingResponses.FUNDING_RESPONSE_REJECTION_REQUEST_MESSAGE
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class FundingResponseConfirmationMessageValidatorTests {

    @Test
    fun `FundingResponseConfirmationRequestMessageAcceptanceValidator produces the expected validation messages`() {
        val validator = FundingResponseConfirmationRequestMessageAcceptanceValidator()
        val expectedValidationMessages = listOf(
                "Property 'externalId' must not be null, empty or blank.",
                "Property 'bankAccount' must not be null."
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
    fun `FundingResponseConfirmationRequestMessageAcceptanceValidator does not throw a ValidationException when the message state is valid`() {
        val message = FUNDING_RESPONSE_ACCEPTANCE_REQUEST_MESSAGE
        val validator = FundingResponseConfirmationRequestMessageAcceptanceValidator()
        validator.validate(message)
    }

    @Test
    fun `FundingResponseConfirmationRequestMessageRejectionValidator produces the expected validation messages`() {
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
    fun `FundingResponseConfirmationRequestMessageRejectionValidator does not throw a ValidationException when the message state is valid`() {
        val message = FUNDING_RESPONSE_REJECTION_REQUEST_MESSAGE
        val validator = FundingResponseConfirmationRequestMessageRejectionValidator()
        validator.validate(message)
    }
}