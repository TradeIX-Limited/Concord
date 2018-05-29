package com.tradeix.concord.shared.extensions

import net.corda.core.crypto.SecureHash

fun SecureHash.Companion.isParsable(hash: String): Boolean {
    return try {
        SecureHash.parse(hash)
        true
    } catch (ex: Exception) {
        false
    }
}

fun SecureHash.Companion.tryParse(hash: String?): SecureHash? {
    return try {
        SecureHash.parse(hash ?: "")
    } catch (ex: Exception) {
        null
    }
}