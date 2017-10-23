package com.tradeix.concord.exceptions

import net.corda.core.flows.FlowException

class ValidationException(
        override val message: String = "Request validation failed",
        override val cause: Throwable? = null,
        val validationErrors: ArrayList<String>) : FlowException(message, cause)