package com.tradeix.concord.shared.extensions

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tradeix.concord.shared.serialization.CordaX500NameSerializer
import net.corda.core.identity.CordaX500Name

fun GsonBuilder.getConfiguredSerializer(): Gson {
    return this
            .registerTypeAdapter(CordaX500Name::class.java, CordaX500NameSerializer())
            .disableHtmlEscaping()
            .serializeNulls()
            .create()
}