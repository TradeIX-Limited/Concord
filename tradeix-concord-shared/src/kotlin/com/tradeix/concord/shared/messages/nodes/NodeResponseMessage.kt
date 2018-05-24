package com.tradeix.concord.shared.messages.nodes

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class NodeResponseMessage(val node: String)