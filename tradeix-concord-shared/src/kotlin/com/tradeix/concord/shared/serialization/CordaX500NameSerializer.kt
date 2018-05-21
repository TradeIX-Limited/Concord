package com.tradeix.concord.shared.serialization

import com.google.gson.*
import net.corda.core.identity.CordaX500Name
import java.lang.reflect.Type

class CordaX500NameSerializer : JsonSerializer<CordaX500Name>, JsonDeserializer<CordaX500Name> {

    override fun serialize(src: CordaX500Name?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src.toString())
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): CordaX500Name {
        return CordaX500Name.parse(json!!.asString)
    }
}