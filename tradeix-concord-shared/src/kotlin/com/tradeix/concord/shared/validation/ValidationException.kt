package com.tradeix.concord.shared.validation

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
class ValidationException(
        val validationMessages: Iterable<String>,
        override val message: String? = null,
        override val cause: Throwable? = null
) : Exception(message ?: "Validation failed.\n${validationMessages.joinToString("\n")}", cause)