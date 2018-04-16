package com.tradeix.concord.flows

import net.corda.testing.node.MockNetwork
import net.corda.testing.node.StartedMockNode
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
        network = MockNetwork(listOf("com.tradeix.concord.contracts"))
        val nodes = listOf(1, 2, 3, 4).map { network.createPartyNode() }

        nodes.forEach { configureNode(it) }

        buyer = MockIdentity(nodes[0])
        supplier = MockIdentity(nodes[1])
        funder = MockIdentity(nodes[2])
        conductor = MockIdentity(nodes[3])

        network.runNetwork()
    }

    @After
    fun tearDown() {
        network.stopNodes()
    }

    abstract fun configureNode(node: StartedMockNode)
}