package com.tradeix.concord.shared.validation

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
class PropertyValidationException(
        override val message: String,
        override val cause: Throwable? = null
) : Exception(message, cause)