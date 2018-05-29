package com.tradeix.concord.tools.postman.model

data class EndpointCollection(private val name: String, private val description: String) {
    val info = EndpointCollectionInfo(name, description)
    val item = mutableListOf<EndpointGroup>()
}