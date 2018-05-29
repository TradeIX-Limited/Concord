package com.tradeix.concord.shared.validation

fun <TValue> PropertyValidator<Comparable<TValue>?>.isLessThan(
        value: TValue, message: String? = null): PropertyValidator<Comparable<TValue>?> {

    if (!validator.isValidating || propertyValue != null && propertyValue >= value) {
        addOrThrow(message ?: format("must be less than the specified value"))
    }

    return this
}

fun <TValue> PropertyValidator<Comparable<TValue>?>.isLessThanOrEqualTo(
        value: TValue, message: String? = null): PropertyValidator<Comparable<TValue>?> {

    if (!validator.isValidating || propertyValue != null && propertyValue > value) {
        addOrThrow(message ?: format("must be less than or equal to the specified value"))
    }

    return this
}

fun <TValue> PropertyValidator<Comparable<TValue>?>.isGreaterThan(
        value: TValue, message: String? = null): PropertyValidator<Comparable<TValue>?> {

    if (!validator.isValidating || propertyValue != null && propertyValue <= value) {
        addOrThrow(message ?: format("must be greater than the specified value"))
    }

    return this
}

fun <TValue> PropertyValidator<Comparable<TValue>?>.isGreaterThanOrEqualTo(
        value: TValue, message: String? = null): PropertyValidator<Comparable<TValue>?> {

    if (!validator.isValidating || propertyValue != null && propertyValue < value) {
        addOrThrow(message ?: format("must be greater than or equal to the specified value"))
    }

    return this
}

fun <TValue> PropertyValidator<Comparable<TValue>?>.isWithinRangeExclusive(
        min: TValue, max: TValue, message: String? = null): PropertyValidator<Comparable<TValue>?> {

    if (!validator.isValidating || propertyValue != null && propertyValue > min && propertyValue < max) {
        addOrThrow(message ?: format("must be within the specified range"))
    }

    return this
}

fun <TValue> PropertyValidator<Comparable<TValue>?>.isWithinRangeInclusive(
        min: TValue, max: TValue, message: String? = null): PropertyValidator<Comparable<TValue>?> {

    if (!validator.isValidating || propertyValue != null && propertyValue >= min && propertyValue <= max) {
        addOrThrow(message ?: format("must be within the specified range"))
    }

    return this
}