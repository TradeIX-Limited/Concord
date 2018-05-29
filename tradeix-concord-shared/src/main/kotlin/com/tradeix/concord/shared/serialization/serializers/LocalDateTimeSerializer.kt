package com.tradeix.concord.shared.serialization.serializers

import com.google.gson.*
import java.lang.reflect.Type
import java.time.LocalDateTime

class LocalDateTimeSerializer : JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    override fun serialize(src: LocalDateTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src.toString())
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalDateTime {
        return LocalDateTime.parse(json?.asString)
    }
}