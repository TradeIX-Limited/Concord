package com.tradeix.concord.shared.validation

import com.tradeix.concord.shared.extensions.containsAll
import com.tradeix.concord.shared.extensions.isEmpty
import com.tradeix.concord.shared.extensions.isNotEmpty

fun <T0, T1> PropertyValidator<Iterable<T0>?>.map(selector: (T0) -> T1): PropertyValidator<Iterable<T1>?> {
    return PropertyValidator(validator, propertyName, propertyValue?.map(selector))
}

fun <TValue> PropertyValidator<Iterable<TValue>?>.isEmpty(
        message: String? = null): PropertyValidator<Iterable<TValue>?> {

    if (!validator.isValidating || propertyValue != null && propertyValue.isNotEmpty()) {
        addOrThrow(message ?: format("must not contain any elements"))
    }

    return this
}

fun <TValue> PropertyValidator<Iterable<TValue>?>.isNotEmpty(
        message: String? = null): PropertyValidator<Iterable<TValue>?> {

    if (!validator.isValidating || propertyValue != null && !propertyValue.isEmpty()) {
        addOrThrow(message ?: format("must contain at least one element"))
    }

    return this
}

fun <TValue> PropertyValidator<Iterable<TValue>?>.hasSize(
        value: Int, message: String? = null): PropertyValidator<Iterable<TValue>?> {

    if (!validator.isValidating || propertyValue != null && propertyValue.count() != value) {
        addOrThrow(message ?: format("must have the specified number of elements"))
    }

    return this
}

fun <TValue> PropertyValidator<Iterable<TValue>?>.containsAll(
        elements: Iterable<TValue>, message: String? = null): PropertyValidator<Iterable<TValue>?> {

    if (!validator.isValidating || propertyValue != null && !propertyValue.containsAll(elements)) {
        addOrThrow(message ?: format("must contain all of the specified elements"))
    }

    return this
}

fun <TValue> PropertyValidator<Iterable<TValue>?>.inverseContainsAll(
        elements: Iterable<TValue>, message: String? = null) : PropertyValidator<Iterable<TValue>?> {

    if(!validator.isValidating || propertyValue != null && !elements.containsAll(propertyValue)) {
        addOrThrow(message ?: format("must contain all of the specified elements"))
    }

    return this
}