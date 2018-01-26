package com.tradeix.concord.extensions

import net.corda.core.crypto.SecureHash

object SecureHashExtensions {
    fun SecureHash.Companion.isValid(value: String): Boolean {
        return try {
            SecureHash.parse(value)
            true
        } catch(ex: Throwable) {
            false
        }
    }
}