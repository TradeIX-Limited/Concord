package com.tradeix.concord.validators

abstract class Validator<T>(protected val message: T) {
    protected val errors = ArrayList<String>()

    init {
        validate()
    }

    val validationErrors: List<String> get() = errors
    val isValid: Boolean = errors.isEmpty()

    protected abstract fun validate()
}