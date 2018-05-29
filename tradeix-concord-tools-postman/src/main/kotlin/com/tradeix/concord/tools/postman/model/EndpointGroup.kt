package com.tradeix.concord.tools.postman.model

data class EndpointGroup(val name: String, val description: String = "") {
    val item = mutableListOf<Endpoint>()
}