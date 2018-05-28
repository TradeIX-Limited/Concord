package com.tradeix.concord.tests.unit.validators

import com.tradeix.concord.shared.validators.InvoiceRequestMessageValidator
import com.tradeix.concord.tests.utils.TestMessages.INVOICE_ISSUANCE_REQUEST_MESSAGE
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class InvoiceRequestMessageValidatorValidationTests {

    @Test
    fun `InvoiceMessageValidator produces the expected validation messages`() {
        val validator = InvoiceRequestMessageValidator()
        val expectedValidationMessages = listOf(
                "Property 'externalId' must not be null, empty or blank.",
                "Property 'buyer' must be a valid X500 name.",
                "Property 'supplier' must be a valid X500 name.",
                "Property 'invoiceVersion' must not be null, empty or blank.",
                "Property 'invoiceVersionDate' must not be null.",
                "Property 'tixInvoiceVersion' must not be null.",
                "Property 'invoiceNumber' must not be null, empty or blank.",
                "Property 'invoiceType' must not be null, empty or blank.",
                "Property 'reference' must not be null, empty or blank.",
                "Property 'dueDate' must not be null.",
                "Property 'amount' must not be null.",
                "Property 'amount' must be greater than the specified value.",
                "Property 'totalOutstanding' must not be null.",
                "Property 'totalOutstanding' must be greater than or equal to the specified value.",
                "Property 'created' must not be null.",
                "Property 'updated' must not be null.",
                "Property 'expectedSettlementDate' must not be null.",
                "Property 'invoiceDate' must not be null.",
                "Property 'status' must not be null, empty or blank.",
                "Property 'rejectionReason' must not be empty.",
                "Property 'rejectionReason' must not be blank.",
                "Property 'eligibleValue' must not be null.",
                "Property 'invoicePurchaseValue' must not be null.",
                "Property 'invoicePayments' must not be null.",
                "Property 'invoiceDilutions' must not be null.",
                "Property 'cancelled' must not be null.",
                "Property 'originationNetwork' must not be null, empty or blank.",
                "Property 'currency' must not be null.",
                "Property 'currency' must be a valid currency code.",
                "Property 'siteId' must not be null, empty or blank.",
                "Property 'purchaseOrderNumber' must not be null, empty or blank.",
                "Property 'purchaseOrderId' must not be null, empty or blank.",
                "Property 'composerProgramId' must not be null."
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
    fun `InvoiceMessageValidator does not throw a ValidationException when the message state is valid`() {
        val message = INVOICE_ISSUANCE_REQUEST_MESSAGE
        val validator = InvoiceRequestMessageValidator()
        validator.validate(message)
    }
}