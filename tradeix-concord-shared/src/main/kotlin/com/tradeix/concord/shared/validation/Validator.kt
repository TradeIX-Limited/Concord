package com.tradeix.concord.shared.validation

abstract class Validator {

    protected var emulating: Boolean = false
    protected val validationMessages = mutableListOf<String>()

    abstract fun getValidationMessages(): Iterable<String>

    internal abstract fun addValidationMessage(validationMessage: String)
}