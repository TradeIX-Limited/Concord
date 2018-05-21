package com.tradeix.concord.shared.extensions

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tradeix.concord.shared.serialization.CordaX500NameSerializer
import com.tradeix.concord.shared.serialization.InstantSerializer
import net.corda.core.identity.CordaX500Name
import java.time.Instant

fun GsonBuilder.getConfiguredSerializer(): Gson {
    return this
            .registerTypeAdapter(CordaX500Name::class.java, CordaX500NameSerializer())
            .registerTypeAdapter(Instant::class.java, InstantSerializer())
            .disableHtmlEscaping()
            .create()
}