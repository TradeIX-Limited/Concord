package com.tradeix.concord.tests.unit.contracts

import com.tradeix.concord.shared.validation.PropertyValidationException
import net.corda.core.contracts.TransactionVerificationException
import net.corda.testing.node.MockServices
import kotlin.test.assertFailsWith

abstract class ContractTest {
    protected val services = MockServices(listOf("com.tradeix.concord.shared.domain.contracts"))

    fun assertValidationFails(message: String, block: () -> Unit) {

        val exception = assertFailsWith(TransactionVerificationException::class, message, block)

        if (exception.cause is PropertyValidationException) {
            val validationException = exception.cause as PropertyValidationException
            val exceptionMessage = validationException.message
            if (exceptionMessage != message) {
                throw AssertionError("Expected validation message was '$message' but got '$exceptionMessage'")
            }
        } else {
            throw AssertionError(
                    "Cause of the transaction verification exception was not a validation exception."
            )
        }
    }
}