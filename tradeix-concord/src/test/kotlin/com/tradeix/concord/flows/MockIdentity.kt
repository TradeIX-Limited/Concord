package com.tradeix.concord.flows

import net.corda.node.internal.StartedNode
import net.corda.testing.chooseIdentity
import net.corda.testing.node.MockNetwork

data class MockIdentity(val node: StartedNode<MockNetwork.MockNode>) {
    val party get() = node.info.chooseIdentity()
    val name get() = party.name
    val publicKey get() = party.owningKey
}