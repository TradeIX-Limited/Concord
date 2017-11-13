package com.tradeix.concord.flows

import net.corda.node.internal.StartedNode
import net.corda.testing.node.MockNetwork
import net.corda.testing.setCordappPackages
import net.corda.testing.unsetCordappPackages
import org.junit.After
import org.junit.Before

abstract class AbstractFlowTest {
    protected lateinit var network: MockNetwork

    protected lateinit var buyer: MockIdentity
    protected lateinit var supplier: MockIdentity
    protected lateinit var funder: MockIdentity
    protected lateinit var conductor: MockIdentity

    @Before
    open fun setup() {
        setCordappPackages("com.tradeix.concord.contracts")
        network = MockNetwork()
        val nodes = network.createSomeNodes(4)

        nodes.partyNodes.forEach { configureNode(it) }

        buyer = MockIdentity(nodes.partyNodes[0])
        supplier = MockIdentity(nodes.partyNodes[1])
        funder = MockIdentity(nodes.partyNodes[2])
        conductor = MockIdentity(nodes.partyNodes[3])

        network.runNetwork()
    }

    @After
    fun tearDown() {
        unsetCordappPackages()
        network.stopNodes()
    }

    abstract fun configureNode(node: StartedNode<MockNetwork.MockNode>)
}