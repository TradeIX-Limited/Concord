package com.tradeix.concord.tests.unit.flows

import net.corda.testing.node.MockNetwork
import net.corda.testing.node.StartedMockNode
import org.junit.After
import org.junit.Before

abstract class FlowTest {

    protected lateinit var network: MockNetwork

    protected lateinit var buyer: MockIdentity
    protected lateinit var supplier: MockIdentity
    protected lateinit var funder: MockIdentity

    @Before
    fun setup() {
        network = MockNetwork(
                listOf(
                        "com.tradeix.concord.shared.cordapp",
                        "com.tradeix.concord.shared.domain.contracts"
                )
        )

        val nodes = listOf(1, 2, 3).map { network.createPartyNode() }

        nodes.forEach { configureNode(it) }

        buyer = MockIdentity(nodes[0])
        supplier = MockIdentity(nodes[1])
        funder = MockIdentity(nodes[2])

        network.runNetwork()
    }

    @After
    fun tearDown() {
        network.stopNodes()
    }

    protected abstract fun configureNode(node: StartedMockNode)
}