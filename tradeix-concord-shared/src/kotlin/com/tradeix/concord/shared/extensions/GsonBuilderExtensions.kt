package com.tradeix.concord.shared.extensions

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.tradeix.concord.shared.serialization.configuration.AnnotationExclusionStrategy
import com.tradeix.concord.shared.serialization.serializers.CordaX500NameSerializer
import com.tradeix.concord.shared.serialization.serializers.LocalDateTimeSerializer
import net.corda.core.identity.CordaX500Name
import java.time.LocalDateTime

fun GsonBuilder.getConfiguredSerializer(): Gson {
    return this
            .registerTypeAdapter(CordaX500Name::class.java, CordaX500NameSerializer())
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeSerializer())
            .setExclusionStrategies(AnnotationExclusionStrategy())
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .serializeNulls()
            .create()
}