package com.tradeix.concord.exceptions

import net.corda.core.flows.FlowException

class FlowValidationException(
        override val message: String = "Request validation failed",
        override val cause: Throwable? = null,
        val validationErrors: List<String>) : FlowException(message, cause)