package com.tradeix.concord.tools.postman.model

import com.google.gson.GsonBuilder
import com.tradeix.concord.shared.extensions.getConfiguredSerializer
import com.tradeix.concord.shared.serialization.configuration.Exclude

class JsonRequestBody(@Exclude private val obj: Any) : RequestBody("raw") {
    val raw = GsonBuilder().getConfiguredSerializer().toJson(obj)
}