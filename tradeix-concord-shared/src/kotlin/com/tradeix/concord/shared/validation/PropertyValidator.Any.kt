package com.tradeix.concord.shared.validation

fun <TValue> PropertyValidator<TValue>.isNotNull(
        message: String? = null): PropertyValidator<TValue> {

    if(!validator.isValidating || propertyValue == null) {
        addOrThrow(message ?: format("must not be null"))
    }

    return this
}

fun <TValue> PropertyValidator<TValue>.isNull(
        message: String? = null): PropertyValidator<TValue> {

    if(!validator.isValidating || propertyValue != null) {
        addOrThrow(message ?: format("must be null"))
    }

    return this
}

fun <TValue> PropertyValidator<TValue>.isEqualTo(
        value: TValue, message: String? = null): PropertyValidator<TValue> {

    if (!validator.isValidating || propertyValue != value) {
        addOrThrow(message ?: format("must be equal to the specified value"))
    }

    return this
}

fun <TValue> PropertyValidator<TValue>.isNotEqualTo(
        value: TValue, message: String? = null): PropertyValidator<TValue> {

    if (!validator.isValidating || propertyValue == value) {
        addOrThrow(message ?: format("must not be equal to the specified value"))
    }

    return this
}

fun <TValue> PropertyValidator<TValue>.satisfies(
        predicate: (TValue) -> Boolean, message: String? = null): PropertyValidator<TValue> {

    if (!validator.isValidating || propertyValue != null && !predicate(propertyValue)) {
        addOrThrow(message ?: format("must satisfy the predicate condition"))
    }

    return this
}