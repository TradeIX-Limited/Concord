package com.tradeix.concord.messages

abstract class RequestMessage() {
    val isValid: Boolean get() = getValidationErrors().isEmpty()
    abstract fun getValidationErrors(): ArrayList<String>
}