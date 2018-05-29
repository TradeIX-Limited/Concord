package com.tradeix.concord.tools.postman.configuration

import com.google.gson.GsonBuilder
import com.tradeix.concord.shared.extensions.getConfiguredSerializer
import com.tradeix.concord.tools.postman.model.EndpointCollection
import java.io.File

abstract class PostmanConfiguration(private val name: String, private val description: String) {
    val collection = EndpointCollection(name, description)

    fun build() {
        configureCollection(collection)
        saveConfigurtion()
    }

    protected abstract fun configureCollection(collection: EndpointCollection)

    private fun saveConfigurtion() {
        val json = GsonBuilder().getConfiguredSerializer().toJson(collection)
        File("config/postman/config-postman-${collection.info.name.toLowerCase()}.json").writeText(json)
    }
}