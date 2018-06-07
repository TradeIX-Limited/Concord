package com.tradeix.concord.shared.validation.extensions

import com.tradeix.concord.shared.validation.PropertyValidator

fun <TValue> PropertyValidator<Comparable<TValue>?>.isLessThan(
        value: TValue, validationMessage: String? = null) {

    if (context.emulating || this.value != null && this.value >= value) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must be less than the specified value")
        )
    }
}

fun <TValue> PropertyValidator<Comparable<TValue>?>.isLessThanOrEqualTo(
        value: TValue, validationMessage: String? = null) {

    if (context.emulating || this.value != null && this.value > value) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must be less than or equal to the specified value")
        )
    }
}

fun <TValue> PropertyValidator<Comparable<TValue>?>.isGreaterThan(
        value: TValue, validationMessage: String? = null) {

    if (context.emulating || this.value != null && this.value <= value) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must be greater than the specified value")
        )
    }
}

fun <TValue> PropertyValidator<Comparable<TValue>?>.isGreaterThanOrEqualTo(
        value: TValue, validationMessage: String? = null) {

    if (context.emulating || this.value != null && this.value < value) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must be greater than or equal to the specified value")
        )
    }
}

fun <TValue> PropertyValidator<Comparable<TValue>?>.isWithinRangeExclusive(
        min: TValue, max: TValue, validationMessage: String? = null) {

    if (context.emulating || this.value != null && this.value > min && this.value < max) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must be within the specified range")
        )
    }
}

fun <TValue> PropertyValidator<Comparable<TValue>?>.isWithinRangeInclusive(
        min: TValue, max: TValue, validationMessage: String? = null) {

    if (context.emulating || this.value != null && this.value >= min && this.value <= max) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must be within the specified range")
        )
    }
}