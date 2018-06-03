package com.tradeix.concord.shared.client.components

import net.corda.client.rpc.CordaRPCClient
import net.corda.core.utilities.NetworkHostAndPort
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
class RPCConnectionProvider(
        @Value("\${config.rpc.username}") username: String,
        @Value("\${config.rpc.password}") password: String,
        @Value("\${config.rpc.host}") host: String,
        @Value("\${config.rpc.port}") port: Int) {

    val proxy = CordaRPCClient(NetworkHostAndPort(host, port)).start(username, password).proxy
}