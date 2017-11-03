package com.tradeix.concord.serialization

import com.google.gson.*
import net.corda.core.identity.CordaX500Name
import java.lang.reflect.Type

class CordaX500NameSerializer : JsonSerializer<CordaX500Name> {
    override fun serialize(src: CordaX500Name?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src.toString())
    }
}