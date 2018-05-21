package com.tradeix.concord.shared.validation

class PropertyValidator<out TValue>(
        internal val validator: Validator,
        internal val propertyName: String,
        internal val propertyValue: TValue?) {

    internal fun format(message: String): String {
        return "Property '$propertyName' $message."
    }

    internal fun addOrThrow(message: String) {
        if (!validator.isValidating || validator.validationBehavior == ValidationBehavior.ADD_MESSAGE_ON_ERROR) {
            validator.addValidationMessage(message)
        } else {
            throw PropertyValidationException(message)
        }
    }
}