package com.tradeix.concord.client.spring.receiver.controllers

import com.tradeix.concord.shared.messages.ErrorResponseMessage
import com.tradeix.concord.shared.messages.nodes.NodeResponseMessage
import com.tradeix.concord.shared.messages.nodes.NodesResponseMessage
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = arrayOf("/nodes"), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
class NodeController : Controller() {

    companion object {
        private val KNOWN_NETWORK_NAMES = listOf<String>()
    }

    @GetMapping(path = arrayOf("/all"))
    fun getAllNodes(): ResponseEntity<*> {
        return try {
            val nodes = proxy
                    .networkMapSnapshot()
                    .map { it.legalIdentities.first().name.toString() }
            ResponseEntity.ok(NodesResponseMessage(nodes))
        } catch (ex: Exception) {
            ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponseMessage(ex.message ?: "Unknown server error."))
        }
    }

    @GetMapping(path = arrayOf("/peers"))
    fun getPeerNodes(): ResponseEntity<*> {
        return try {
            val nodes = proxy
                    .networkMapSnapshot()
                    .map { it.legalIdentities.first().name }
                    .filter { it != proxy.nodeInfo().legalIdentities.first().name }
                    .filter { !KNOWN_NETWORK_NAMES.contains(it.organisation) }
                    .map { it.toString() }
            ResponseEntity.ok(NodesResponseMessage(nodes))
        } catch (ex: Exception) {
            ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponseMessage(ex.message ?: "Unknown server error."))
        }
    }

    @GetMapping(path = arrayOf("/local"))
    fun getLocalNode(): ResponseEntity<*> {
        return try {
            ResponseEntity.ok(NodeResponseMessage(proxy.nodeInfo().legalIdentities.first().name.toString()))
        } catch (ex: Exception) {
            ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorResponseMessage(ex.message ?: "Unknown server error."))
        }
    }
}