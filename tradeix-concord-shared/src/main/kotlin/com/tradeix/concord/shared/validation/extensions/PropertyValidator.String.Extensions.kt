package com.tradeix.concord.shared.validation.extensions

import com.tradeix.concord.shared.extensions.isParsable
import com.tradeix.concord.shared.validation.PropertyValidator
import net.corda.core.crypto.SecureHash
import net.corda.core.identity.CordaX500Name
import java.util.*

fun PropertyValidator<String?>.isEmpty(validationMessage: String? = null) {
    if (context.emulating || value != null && value.isNotEmpty()) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must be empty")
        )
    }
}

fun PropertyValidator<String?>.isNotEmpty(validationMessage: String? = null) {
    if (context.emulating || value != null && value.isEmpty()) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must not be empty")
        )
    }
}

fun PropertyValidator<String?>.isBlank(validationMessage: String? = null) {
    if (context.emulating || value != null && value.isNotBlank()) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must be blank")
        )
    }
}

fun PropertyValidator<String?>.isNotBlank(validationMessage: String? = null) {
    if (context.emulating || value != null && value.isBlank()) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must not be blank")
        )
    }
}

fun PropertyValidator<String?>.isNotNullEmptyOrBlank(validationMessage: String? = null) {
    if (context.emulating || value.isNullOrBlank() || value.isNullOrEmpty()) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must not be null, empty or blank")
        )
    }
}

fun PropertyValidator<String?>.hasLength(length: Int, validationMessage: String? = null) {
    if (context.emulating || value != null && value.length != length) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must be $length characters long")
        )
    }
}

fun PropertyValidator<String?>.hasMaxLength(length: Int, validationMessage: String? = null) {
    if (context.emulating || value != null && value.length > length) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must have a maximum length of $length characters")
        )
    }
}

fun PropertyValidator<String?>.hasMinLength(length: Int, validationMessage: String? = null) {
    if (context.emulating || value != null && value.length < length) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must have a minimum length of $length characters")
        )
    }
}

fun PropertyValidator<String?>.hasMinLength(regex: Regex, validationMessage: String? = null) {
    if (context.emulating || value != null && !regex.matches(value)) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must match the specified regular expressions")
        )
    }
}

fun PropertyValidator<String?>.isValidCurrencyCode(validationMessage: String? = null) {
    fun isValidCurrencyCode(value: String): Boolean {
        return try {
            Currency.getInstance(value)
            true
        } catch (ex: Exception) {
            false
        }
    }

    if (context.emulating || value != null && !isValidCurrencyCode(value)) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must be a valid currency code")
        )
    }
}

fun PropertyValidator<String?>.isValidX500Name(validationMessage: String? = null) {
    if (context.emulating || value != null && !CordaX500Name.isParsable(value)) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must be a valid X500 name")
        )
    }
}

fun PropertyValidator<String?>.isValidSecureHash(validationMessage: String? = null) {
    if (context.emulating || value != null && !SecureHash.isParsable(value)) {
        context.validator.addValidationMessage(
                validationMessage ?: format("must be a valid secure hash")
        )
    }
}