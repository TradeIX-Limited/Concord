package com.tradeix.concord.validators

import com.tradeix.concord.messages.Message

abstract class MessageValidator<out T : Message>(protected val message: T) {
    val isValid: Boolean get() = getValidationErrorMessages().isEmpty()
    abstract fun getValidationErrorMessages(): ArrayList<String>
}