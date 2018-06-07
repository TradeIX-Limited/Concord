package com.tradeix.concord.shared.validation.extensions

import com.tradeix.concord.shared.validation.PropertyValidator

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