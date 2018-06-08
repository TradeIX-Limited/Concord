package com.tradeix.concord.shared.validation.extensions

import com.tradeix.concord.shared.validation.ObjectValidator
import com.tradeix.concord.shared.validation.PropertyValidator
import com.tradeix.concord.shared.validation.Validator

fun <TValue> PropertyValidator<TValue>.isNotNull(validationMessage: String? = null) {
    if (context.emulating || value == null) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must not be null")
        )
    }
}

fun <TValue> PropertyValidator<TValue>.isNull(validationMessage: String? = null) {
    if (context.emulating || value != null) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must be null")
        )
    }
}

fun <TValue> PropertyValidator<TValue>.isEqualTo(other: TValue, validationMessage: String? = null) {
    if (context.emulating || value != other) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must be equal to the specified value ($other)")
        )
    }
}

fun <TValue> PropertyValidator<TValue>.isNotEqualTo(other: TValue, validationMessage: String? = null) {
    if (context.emulating || value == other) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must not be equal to the specified value ($other)")
        )
    }
}

fun <TValue> PropertyValidator<TValue>.satisfiedBy(predicate: (TValue) -> Boolean, validationMessage: String? = null) {
    if (context.emulating || value != null && !predicate(value)) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must satisfy the predicate condition")
        )
    }
}

fun <TValue, TDerived : TValue> PropertyValidator<TValue>.validateWith(validator: ObjectValidator<TDerived>) {
    @Suppress("UNCHECKED_CAST")
    validator.validateInternal(context, value as? TDerived)
}