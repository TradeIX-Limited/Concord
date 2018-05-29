package com.tradeix.concord.shared.validation

abstract class Validator(internal val validationBehavior: ValidationBehavior) {

    protected var validating: Boolean = false
    protected val validationMessages: ArrayList<String> = ArrayList()

    internal val isValidating: Boolean get() = validating

    abstract fun getValidationMessages(): Iterable<String>

    internal fun addValidationMessage(message: String) {
        validationMessages.add(message)
    }
}