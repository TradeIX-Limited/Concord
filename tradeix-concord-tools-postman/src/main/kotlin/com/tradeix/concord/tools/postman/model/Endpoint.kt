package com.tradeix.concord.tools.postman.model

data class Endpoint(val name: String, val request: Request) {
    val response = emptyArray<Any>()
}