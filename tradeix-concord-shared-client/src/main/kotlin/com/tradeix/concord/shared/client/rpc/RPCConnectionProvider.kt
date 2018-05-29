package com.tradeix.concord.shared.client.rpc

import net.corda.client.rpc.CordaRPCClient
import net.corda.core.utilities.NetworkHostAndPort

abstract class RPCConnectionProvider(username: String, password: String, host: String, port: Int) {
    val proxy = CordaRPCClient(NetworkHostAndPort(host, port)).start(username, password).proxy
}