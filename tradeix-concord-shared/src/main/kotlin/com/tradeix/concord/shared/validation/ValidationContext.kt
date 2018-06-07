package com.tradeix.concord.shared.validation

data class ValidationContext(
        val validator: Validator,
        val emulating: Boolean,
        val propertyName: String?) {

    fun withMemberAccessor(propertyName: String): ValidationContext {
        return copy(propertyName = listOfNotNull(this.propertyName, propertyName).joinToString("."))
    }

    fun withIndexAccessor(propertyName: String): ValidationContext {
        return copy(propertyName = listOfNotNull(this.propertyName, propertyName).joinToString(".") + "[index]")
    }
}