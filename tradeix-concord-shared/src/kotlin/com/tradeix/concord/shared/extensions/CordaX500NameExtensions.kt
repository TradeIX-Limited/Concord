package com.tradeix.concord.shared.extensions

import net.corda.core.identity.CordaX500Name

fun CordaX500Name.Companion.isParsable(name: String?): Boolean {
    return if (name == null) {
        false
    } else {
        try {
            CordaX500Name.parse(name)
            true
        } catch (ex: Exception) {
            false
        }
    }
}

fun CordaX500Name.Companion.tryParse(name: String?): CordaX500Name? {
    return if (CordaX500Name.isParsable(name)) {
        CordaX500Name.parse(name!!)
    } else {
        null
    }
}