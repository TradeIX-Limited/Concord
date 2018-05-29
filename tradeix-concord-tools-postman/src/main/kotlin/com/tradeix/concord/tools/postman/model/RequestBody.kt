package com.tradeix.concord.tools.postman.model

abstract class RequestBody(val mode: String?) {
    companion object {
        val EMPTY = object : RequestBody(null) {
        }
    }
}