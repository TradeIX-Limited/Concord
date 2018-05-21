package com.tradeix.concord.shared.validation

import com.tradeix.concord.shared.extensions.isParsable
import net.corda.core.crypto.SecureHash
import net.corda.core.identity.CordaX500Name
import java.util.*

fun PropertyValidator<String?>.isEmpty(
        message: String? = null): PropertyValidator<String?> {

    if (!validator.isValidating || propertyValue != null && propertyValue.isNotEmpty()) {
        addOrThrow(message ?: format("must be empty"))
    }

    return this
}

fun PropertyValidator<String?>.isNotEmpty(
        message: String? = null): PropertyValidator<String?> {

    if (!validator.isValidating || propertyValue != null && propertyValue.isEmpty()) {
        addOrThrow(message ?: format("must not be empty"))
    }

    return this
}

fun PropertyValidator<String?>.isBlank(
        message: String? = null): PropertyValidator<String?> {

    if (!validator.isValidating || propertyValue != null && propertyValue.isNotBlank()) {
        addOrThrow(message ?: format("must be blank"))
    }

    return this
}

fun PropertyValidator<String?>.isNotBlank(
        message: String? = null): PropertyValidator<String?> {

    if (!validator.isValidating || propertyValue != null && propertyValue.isBlank()) {
        addOrThrow(message ?: format("must not be blank"))
    }

    return this
}

fun PropertyValidator<String?>.isNotNullEmptyOrBlank(
        message: String? = null): PropertyValidator<String?> {

    if (!validator.isValidating || propertyValue.isNullOrBlank() || propertyValue.isNullOrEmpty()) {
        addOrThrow(message ?: format("must not be null, empty or blank"))
    }

    return this
}

fun PropertyValidator<String?>.hasLength(
        length: Int, message: String? = null): PropertyValidator<String?> {

    if (!validator.isValidating || propertyValue != null && propertyValue.length != length) {
        addOrThrow(message ?: format("must be of the specified length"))
    }

    return this
}

fun PropertyValidator<String?>.hasMaxLength(
        length: Int, message: String? = null): PropertyValidator<String?> {

    if (!validator.isValidating || propertyValue != null && propertyValue.length > length) {
        addOrThrow(message ?: format("must not be longer than the specified maximum length"))
    }

    return this
}

fun PropertyValidator<String?>.hasMinLength(
        length: Int, message: String? = null): PropertyValidator<String?> {

    if (!validator.isValidating || propertyValue != null && propertyValue.length < length) {
        addOrThrow(message ?: format("must not be shorter than the specified minimum length"))
    }

    return this
}

fun PropertyValidator<String?>.matches(
        regex: Regex, message: String? = null): PropertyValidator<String?> {

    if (!validator.isValidating || propertyValue != null && !regex.matches(propertyValue)) {
        addOrThrow(message ?: format("must match the specified regular expression"))
    }

    return this
}

fun PropertyValidator<String?>.isValidCurrencyCode(
        message: String? = null): PropertyValidator<String?> {

    fun isValidCurrencyCode(value: String): Boolean {
        return try {
            Currency.getInstance(value)
            true
        } catch (ex: Exception) {
            false
        }
    }

    if (!validator.isValidating || propertyValue != null && !isValidCurrencyCode(propertyValue)) {
        addOrThrow(message ?: format("must be a valid currency code"))
    }

    return this
}

fun PropertyValidator<String?>.isValidCordaX500Name(
        message: String? = null): PropertyValidator<String?> {

    if (!validator.isValidating || propertyValue != null && !CordaX500Name.isParsable(propertyValue)) {
        addOrThrow(message ?: format("must be a valid X500 name"))
    }

    return this
}

fun PropertyValidator<String?>.isValidSecureHash(
        message: String? = null): PropertyValidator<String?> {

    if (!validator.isValidating || propertyValue != null && !SecureHash.isParsable(propertyValue)) {
        addOrThrow(message ?: format("must be a valid secure hash"))
    }

    return this
}