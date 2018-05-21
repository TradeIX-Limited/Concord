package com.tradeix.concord.shared.services.rpc

import net.corda.client.rpc.CordaRPCClient
import net.corda.core.messaging.CordaRPCOps
import net.corda.core.utilities.NetworkHostAndPort

object RPCProxyProvider {
    fun getRPCProxy(configuration: RPCProxyProviderConfiguration): CordaRPCOps {
        val address = NetworkHostAndPort(configuration.hostName, configuration.hostPort)
        val client = CordaRPCClient(address)
        val connection = client.start(configuration.username, configuration.password)
        return connection.proxy
    }
}