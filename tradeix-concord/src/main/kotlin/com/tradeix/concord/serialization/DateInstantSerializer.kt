package com.tradeix.concord.serialization

import com.google.gson.*
import com.tradeix.concord.services.messaging.TixMessageSubscriptionStartup
import org.joda.time.format.DateTimeFormatterBuilder
import org.joda.time.format.ISODateTimeFormat
import java.lang.reflect.Type
import java.time.Instant


class DateInstantSerializer : JsonSerializer<Instant>, JsonDeserializer<Instant> {
    override fun serialize(src: Instant?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src.toString())
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Instant {
        val jsonElement = json!!.asString
        val formatter = DateTimeFormatterBuilder()
                .append(ISODateTimeFormat.date())
                .appendLiteral('T')
                .append(ISODateTimeFormat.hourMinuteSecond())
                .appendOptional(DateTimeFormatterBuilder()
                        .appendLiteral('.')
                        .appendFractionOfSecond(1,9).toParser())
                .appendTimeZoneOffset("Z", true, 2, 4)
                .toFormatter()
        val jodaInstant = org.joda.time.Instant.parse(jsonElement, formatter)
        return java.time.Instant.ofEpochMilli(jodaInstant.millis)
    }
}