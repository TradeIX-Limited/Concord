package com.tradeix.concord.cordapp.supplier.client.receiver

import com.tradeix.concord.shared.client.rpc.RPCConnectionProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class RPCConnection(
        @Value("\${config.rpc.username}") username: String,
        @Value("\${config.rpc.password}") password: String,
        @Value("\${config.rpc.host}") host: String,
        @Value("\${config.rpc.port}") port: Int
) : RPCConnectionProvider(username, password, host, port)