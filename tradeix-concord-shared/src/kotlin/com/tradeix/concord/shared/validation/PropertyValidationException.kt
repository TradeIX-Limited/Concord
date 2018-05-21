package com.tradeix.concord.shared.validation

class PropertyValidationException(
        override val message: String,
        override val cause: Throwable? = null
) : Exception(message, cause)