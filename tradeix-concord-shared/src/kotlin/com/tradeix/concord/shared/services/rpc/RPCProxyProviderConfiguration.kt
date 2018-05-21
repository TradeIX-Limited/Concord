package com.tradeix.concord.shared.services.rpc

data class RPCProxyProviderConfiguration(
        val username: String,
        val password: String,
        val hostName: String,
        val hostPort: Int
)