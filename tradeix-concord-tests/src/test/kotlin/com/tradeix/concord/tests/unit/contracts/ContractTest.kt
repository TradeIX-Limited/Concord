package com.tradeix.concord.tests.unit.contracts

import com.tradeix.concord.shared.validation.ValidationException
import net.corda.core.contracts.TransactionVerificationException
import net.corda.testing.node.MockServices
import kotlin.test.assertFailsWith

abstract class ContractTest {
    protected val services = MockServices(listOf("com.tradeix.concord.shared.domain.contracts"))

    fun assertValidationFails(message: String, block: () -> Unit) {

        val exception = assertFailsWith(TransactionVerificationException::class, message, block)

        if (exception.cause is ValidationException) {
            val validationException = exception.cause as ValidationException
            if (!validationException.validationMessages.contains(message)) {
                throw AssertionError("Expected validation exception to contain the message: '$message' but didn't.")
            }
        } else {
            throw AssertionError(
                    "Cause of the transaction verification exception was '${exception.javaClass.name}'."
            )
        }
    }
}