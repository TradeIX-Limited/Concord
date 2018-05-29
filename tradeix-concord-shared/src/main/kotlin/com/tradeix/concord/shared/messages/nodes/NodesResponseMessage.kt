package com.tradeix.concord.shared.messages.nodes

import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class NodesResponseMessage(val nodes: Iterable<String>)