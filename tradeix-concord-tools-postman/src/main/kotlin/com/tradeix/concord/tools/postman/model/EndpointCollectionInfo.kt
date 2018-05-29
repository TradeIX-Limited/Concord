package com.tradeix.concord.tools.postman.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class EndpointCollectionInfo(val name: String, val description: String) {
    @SerializedName("_postman_id") val postmanId = UUID.randomUUID()
    val schema = "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
}