package com.tradeix.concord.client.spring.receiver.controllers

import com.tradeix.concord.shared.services.rpc.RPCProxyProvider
import com.tradeix.concord.shared.services.rpc.RPCProxyProviderConfiguration

abstract class Controller {
    protected val proxy = RPCProxyProvider.getRPCProxy(RPCProxyProviderConfiguration(
            "username",
            "password",
            "hostName",
            1234
    ))
}