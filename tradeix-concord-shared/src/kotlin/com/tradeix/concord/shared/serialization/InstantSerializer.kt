package com.tradeix.concord.shared.serialization

import com.google.gson.*
import java.lang.reflect.Type
import java.time.Instant

class InstantSerializer : JsonSerializer<Instant>, JsonDeserializer<Instant> {

    override fun serialize(src: Instant?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src.toString())
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Instant {
        return Instant.parse(json!!.asString)
    }
}