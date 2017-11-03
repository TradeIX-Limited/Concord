package com.tradeix.concord.serialization

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import net.corda.core.identity.CordaX500Name
import java.lang.reflect.Type

class CordaX500NameDeserializer : JsonDeserializer<CordaX500Name> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): CordaX500Name {
        return CordaX500Name.parse(json!!.asString)
    }

}