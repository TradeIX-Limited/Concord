package com.tradeix.concord.shared.validation

fun <T0, T1> PropertyValidator<Collection<T0>?>.map(selector: (T0) -> T1): PropertyValidator<Collection<T1>?> {
    return PropertyValidator(validator, propertyName, propertyValue?.map(selector))
}

fun <TValue> PropertyValidator<Collection<TValue>?>.isEmpty(
        message: String? = null): PropertyValidator<Collection<TValue>?> {

    if (!validator.isValidating || propertyValue != null && propertyValue.isNotEmpty()) {
        addOrThrow(message ?: format("must not contain any elements"))
    }

    return this
}

fun <TValue> PropertyValidator<Collection<TValue>?>.isNotEmpty(
        message: String? = null): PropertyValidator<Collection<TValue>?> {

    if (!validator.isValidating || propertyValue != null && propertyValue.isEmpty()) {
        addOrThrow(message ?: format("must contain at least one element"))
    }

    return this
}

fun <TValue> PropertyValidator<Collection<TValue>?>.hasSize(
        value: Int, message: String? = null): PropertyValidator<Collection<TValue>?> {

    if (!validator.isValidating || propertyValue != null && propertyValue.size != value) {
        addOrThrow(message ?: format("must have the specified number of elements"))
    }

    return this
}

fun <TValue> PropertyValidator<Collection<TValue>?>.containsAll(
        elements: Collection<TValue>, message: String? = null): PropertyValidator<Collection<TValue>?> {

    if (!validator.isValidating || propertyValue != null && !elements.containsAll(propertyValue)) {
        addOrThrow(message ?: format("must contain all of the specified elements"))
    }

    return this
}