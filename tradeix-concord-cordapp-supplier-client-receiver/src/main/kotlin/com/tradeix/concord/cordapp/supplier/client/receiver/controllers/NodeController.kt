package com.tradeix.concord.cordapp.supplier.client.receiver.controllers

import com.tradeix.concord.shared.client.components.RPCConnectionProvider
import com.tradeix.concord.shared.client.webapi.ResponseBuilder
import com.tradeix.concord.shared.messages.nodes.NodeResponseMessage
import com.tradeix.concord.shared.messages.nodes.NodesResponseMessage
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.Callable

@RestController
@RequestMapping(path = arrayOf("/nodes"), produces = arrayOf(MediaType.APPLICATION_JSON_VALUE))
class NodeController(private val rpc: RPCConnectionProvider) {

    companion object {
        private val KNOWN_NETWORK_NAMES = listOf<String>()
    }

    @GetMapping(path = arrayOf("/all"))
    fun getAllNodes(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                val nodes = rpc.proxy
                        .networkMapSnapshot()
                        .map { it.legalIdentities.first().name.toString() }
                ResponseBuilder.ok(NodesResponseMessage(nodes))
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @GetMapping(path = arrayOf("/peers"))
    fun getPeerNodes(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                val nodes = rpc.proxy
                        .networkMapSnapshot()
                        .map { it.legalIdentities.first().name }
                        .filter { it != rpc.proxy.nodeInfo().legalIdentities.first().name }
                        .filter { !KNOWN_NETWORK_NAMES.contains(it.organisation) }
                        .map { it.toString() }
                ResponseBuilder.ok(NodesResponseMessage(nodes))
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }

    @GetMapping(path = arrayOf("/local"))
    fun getLocalNode(): Callable<ResponseEntity<*>> {
        return Callable {
            try {
                ResponseBuilder.ok(NodeResponseMessage(rpc.proxy.nodeInfo().legalIdentities.first().name.toString()))
            } catch (ex: Exception) {
                ResponseBuilder.internalServerError(ex.message)
            }
        }
    }
}