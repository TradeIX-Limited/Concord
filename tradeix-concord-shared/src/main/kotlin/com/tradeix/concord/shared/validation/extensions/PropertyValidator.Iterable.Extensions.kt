package com.tradeix.concord.shared.validation.extensions

import com.tradeix.concord.shared.extensions.containsAll
import com.tradeix.concord.shared.extensions.isEmpty
import com.tradeix.concord.shared.extensions.isNotEmpty
import com.tradeix.concord.shared.validation.PropertyValidator

fun <TValue> PropertyValidator<Iterable<TValue>?>.isEmpty(validationMessage: String? = null) {
    if (context.emulating || value != null && value.isNotEmpty()) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must not contain any elements")
        )
    }
}

fun <TValue> PropertyValidator<Iterable<TValue>?>.isNotEmpty(validationMessage: String? = null) {
    if (context.emulating || value != null && value.isEmpty()) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must contain at least one element")
        )
    }
}

fun <TValue> PropertyValidator<Iterable<TValue>?>.hasSize(size: Int, validationMessage: String? = null) {
    if (context.emulating || value != null && value.count() != size) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must have the specified number of elements")
        )
    }
}

fun <TValue> PropertyValidator<Iterable<TValue>?>.containsAll(
        elements: Iterable<TValue>,
        validationMessage: String? = null) {

    if (context.emulating || value != null && !value.containsAll(elements)) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must contain all of the specified elements")
        )
    }
}

fun <TValue> PropertyValidator<Iterable<TValue>?>.inverseContainsAll(
        elements: Iterable<TValue>,
        validationMessage: String? = null) {

    if (context.emulating || value != null && !elements.containsAll(value)) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must contain all of the specified elements")
        )
    }
}

fun <TValue, TMappedValue> PropertyValidator<Iterable<TValue>?>.distinct(
        map: (TValue) -> TMappedValue,
        propertyName: String,
        validationMessage: String? = null) {

    if (context.emulating || value != null && value.map(map).distinct().count() == value.count()) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must be distinct on property '$propertyName'")
        )
    }
}