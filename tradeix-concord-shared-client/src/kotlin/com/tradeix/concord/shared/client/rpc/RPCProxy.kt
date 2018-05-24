package com.tradeix.concord.shared.client.rpc

import com.tradeix.concord.shared.services.rpc.RPCProxyProvider
import com.tradeix.concord.shared.services.rpc.RPCProxyProviderConfiguration

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