package com.tradeix.concord.client.spring.receiver

import com.tradeix.concord.shared.services.rpc.RPCProxyProvider
import com.tradeix.concord.shared.services.rpc.RPCProxyProviderConfiguration
import net.corda.core.messaging.CordaRPCOps

object RPCProxy {
    val proxy = RPCProxyProvider.getRPCProxy(
            RPCProxyProviderConfiguration(
                    "rpc_supplier",
                    "rpc_password_123",
                    "localhost",
                    10005
            )
    )
}