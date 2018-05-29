package com.tradeix.concord.tools.postman.model

class Request(
        val method: String,
        val description: String,
        val header: Array<RequestHeader>,
        val body: RequestBody,
        val url: RequestUrl
)