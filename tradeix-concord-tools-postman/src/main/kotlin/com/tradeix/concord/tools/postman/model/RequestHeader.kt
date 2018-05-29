package com.tradeix.concord.tools.postman.model

data class RequestHeader(val key: String, val value: String) {
    companion object {
        val EMPTY = emptyArray<RequestHeader>()
        val APPLICATION_JSON = arrayOf(RequestHeader("Content-Type", "application/json"))
    }
}