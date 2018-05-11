package com.tradeix.concord.serialization

import com.google.gson.*
import org.joda.time.format.ISODateTimeFormat
import java.lang.reflect.Type
import java.time.Instant


class DateInstantSerializer : JsonSerializer<Instant>, JsonDeserializer<Instant> {
    override fun serialize(src: Instant?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src.toString())
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Instant {
        val jsonElement = json!!.asString
        val formatter = ISODateTimeFormat.dateTime()
        val jodaInstant = org.joda.time.Instant.parse(jsonElement, formatter)
        return java.time.Instant.ofEpochMilli(jodaInstant.millis)
    }
}