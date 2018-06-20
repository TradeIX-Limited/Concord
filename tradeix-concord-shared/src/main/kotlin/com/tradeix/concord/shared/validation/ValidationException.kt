package com.tradeix.concord.shared.validation

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
class ValidationException(val validationMessages: Iterable<String>, cause: Throwable? = null)
    : Exception("Validation failed.\n${validationMessages.joinToString("\n")}", cause)