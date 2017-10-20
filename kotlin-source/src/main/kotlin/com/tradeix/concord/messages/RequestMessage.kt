package com.tradeix.concord.messages

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
abstract class RequestMessage {
    val isValid: Boolean get() = getValidationErrors().isEmpty()
    abstract fun getValidationErrors(): ArrayList<String>
}