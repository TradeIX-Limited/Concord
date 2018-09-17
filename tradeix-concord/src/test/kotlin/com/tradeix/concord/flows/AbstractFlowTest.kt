package com.tradeix.concord.flows

import net.corda.core.identity.CordaX500Name
import net.corda.node.services.transactions.SimpleNotaryService
import net.corda.testing.node.MockNetwork
import net.corda.testing.node.MockNetworkNotarySpec
import net.corda.testing.node.StartedMockNode
import org.junit.After
import org.junit.Before
import org.mockito.Mock

abstract class AbstractFlowTest {
    protected lateinit var network: MockNetwork

    protected lateinit var buyer: MockIdentity
    protected lateinit var buyer2: MockIdentity
    protected lateinit var buyer3: MockIdentity
    protected lateinit var supplier: MockIdentity
    protected lateinit var funder: MockIdentity
    protected lateinit var conductor: MockIdentity

    private val bootstrapNotaryx500Name = CordaX500Name(commonName = SimpleNotaryService::class.java.name, organisation = "BootstrapNotary", locality = "Argleton", country = "GB")
    private val meteringNotaryX500Name = CordaX500Name(commonName = SimpleNotaryService::class.java.name, organisation = "MeteringNotary", locality = "Argleton", country = "GB")


    @Before
    open fun setup() {
        network = MockNetwork(listOf("com.tradeix.concord.contracts"),
                    notarySpecs= listOf(MockNetworkNotarySpec(bootstrapNotaryx500Name, true),MockNetworkNotarySpec(meteringNotaryX500Name, true)))
        val nodes = listOf(1, 2, 3, 4, 5, 6).map { network.createPartyNode() }

        nodes.forEach { configureNode(it) }

        buyer = MockIdentity(nodes[0])
        supplier = MockIdentity(nodes[1])
        funder = MockIdentity(nodes[2])
        conductor = MockIdentity(nodes[3])
        buyer2 = MockIdentity(nodes[4])
        buyer3 = MockIdentity(nodes[5])

        network.runNetwork()
    }

    @After
    fun tearDown() {
        network.stopNodes()
    }

    abstract fun configureNode(node: StartedMockNode)
}