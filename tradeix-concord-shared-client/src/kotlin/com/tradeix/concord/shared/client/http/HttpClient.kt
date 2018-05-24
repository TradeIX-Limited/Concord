package com.tradeix.concord.shared.client.http

import org.springframework.web.client.RestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders


class HttpClient(private val server: String) {

    private val rest = RestTemplate()
    private val headers = HttpHeaders()

    init {
        headers.add("Content-Type", "application/json")
        headers.add("Accept", "*/*")
    }

    fun post(uri: String, json: String) {
        val requestEntity = HttpEntity<String>(json, headers)
        rest.postForEntity(server + uri, requestEntity, Unit::class.java)
    }
}