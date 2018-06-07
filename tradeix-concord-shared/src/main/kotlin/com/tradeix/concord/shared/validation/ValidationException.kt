package com.tradeix.concord.shared.validation

class ValidationException(val validationMessages: Iterable<String>, cause: Throwable? = null)
    : Exception("Validation failed.\n${validationMessages.joinToString("\n")}", cause)