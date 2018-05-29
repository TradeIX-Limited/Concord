package com.tradeix.concord.tools.postman.model

data class RequestUrl(val protocol: String, val host: Array<String>, val port: String, val path: Array<String>) {
    companion object {
        fun from(protocol: String, host: String, port: String, path: String): RequestUrl {
            return RequestUrl(protocol, host.split(".").toTypedArray(), port, path.split("/").toTypedArray())
        }
    }

    val raw get() = "$protocol://${host.joinToString(".")}:$port/${path.joinToString("/")}"
}