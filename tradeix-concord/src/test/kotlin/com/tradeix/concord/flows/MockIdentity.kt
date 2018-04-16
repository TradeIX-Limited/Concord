package com.tradeix.concord.flows

import net.corda.testing.internal.chooseIdentity
import net.corda.testing.node.StartedMockNode

data class MockIdentity(val node: StartedMockNode) {
    val party get() = node.info.chooseIdentity()
    val name get() = party.name
    val publicKey get() = party.owningKey
}