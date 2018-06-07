package com.tradeix.concord.shared.validation

class PropertyValidator<out TValue>(internal val context: ValidationContext, internal val value: TValue?) {

    internal fun format(validationMessage: String): String {
        return "Property '${context.propertyName}' $validationMessage."
    }
}