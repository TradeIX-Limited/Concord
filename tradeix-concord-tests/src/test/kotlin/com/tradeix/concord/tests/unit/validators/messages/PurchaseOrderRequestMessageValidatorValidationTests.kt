package com.tradeix.concord.tests.unit.validators.messages

import com.tradeix.concord.shared.mockdata.MockMessages
import com.tradeix.concord.shared.validators.PurchaseOrderRequestMessageValidator
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PurchaseOrderRequestMessageValidatorValidationTests {

    @Test
    fun `PurchaseOrderRequestMessageValidator produces the expected validation messages`(){
        val validator = PurchaseOrderRequestMessageValidator()
        val expectedValidationMessages = listOf(
                "Property 'externalId' must not be null, empty or blank.",
                "Property 'buyer' must be a valid X500 name.",
                "Property 'supplier' must not be null, empty or blank.",
                "Property 'supplier' must be a valid X500 name.",
                "Property 'reference' must not be null, empty or blank.",
                "Property 'value' must not be null.",
                "Property 'value' must be greater than the specified value.",
                "Property 'currency' must not be null, empty or blank.",
                "Property 'currency' must be a valid currency code.",
                "Property 'earliestShipment' must not be null.",
                "Property 'latestShipment' must not be null.",
                "Property 'portOfShipment' must not be null, empty or blank.",
                "Property 'descriptionOfGoods' must not be null, empty or blank.",
                "Property 'deliveryTerms' must not be null, empty or blank."
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
    fun `PurchaseOrderRequestMessageValidator does not throw a ValidationException when the message state is valid`() {
        val message = MockMessages.PURCHASE_ORDER_ISSUANCE_REQUEST_MESSAGE
        val validator = PurchaseOrderRequestMessageValidator()
        validator.validate(message)
    }
}