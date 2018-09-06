package com.tradeix.concord.flows

import com.tradeix.concord.flows.FlowTestHelper.runActivateMembershipFlow
import com.tradeix.concord.flows.FlowTestHelper.runRequestMembershipFlow
import net.corda.businessnetworks.membership.bno.GetMembershipListFlowResponder
import net.corda.businessnetworks.membership.bno.RequestMembershipFlowResponder
import net.corda.core.identity.CordaX500Name
import net.corda.testing.node.MockNetwork
import net.corda.testing.node.MockNetworkNotarySpec
import net.corda.testing.node.MockNodeParameters
import net.corda.testing.node.StartedMockNode
import org.junit.After
import org.junit.Before

abstract class AbstractFlowTest {
    protected lateinit var network: MockNetwork

    protected lateinit var buyer: MockIdentity
    protected lateinit var buyer2: MockIdentity
    protected lateinit var buyer3: MockIdentity
    protected lateinit var supplier: MockIdentity
    protected lateinit var funder: MockIdentity
    protected lateinit var conductor: MockIdentity
    protected lateinit var bno: StartedMockNode

    val bnoName = CordaX500Name.parse("O=BNO,L=New York,C=US")
    val notaryName = CordaX500Name.parse("O=Notary,L=London,C=GB")

    @Before
    open fun setup() {
        network = MockNetwork(listOf("com.tradeix.concord.contracts",
                "net.corda.businessnetworks.membership.bno.service",
                "net.corda.businessnetworks.membership.member.service",
                "net.corda.businessnetworks.membership.states",
                "net.corda.businessnetworks.membership.member"),
                notarySpecs = listOf(MockNetworkNotarySpec(notaryName)))
        val nodes = listOf(1, 2, 3, 4, 5, 6).map { network.createPartyNode() }

        nodes.forEach { configureNode(it) }

        buyer = MockIdentity(nodes[0])
        supplier = MockIdentity(nodes[1])
        funder = MockIdentity(nodes[2])
        conductor = MockIdentity(nodes[3])
        buyer2 = MockIdentity(nodes[4])
        buyer3 = MockIdentity(nodes[5])

        bno = createNode(bnoName, true)
        bno.registerInitiatedFlow(GetMembershipListFlowResponder::class.java)
        bno.registerInitiatedFlow(RequestMembershipFlowResponder::class.java)

        network.runNetwork()

        runRequestMembershipFlow(network, supplier.node)
        runRequestMembershipFlow(network, buyer.node)
        runRequestMembershipFlow(network, buyer2.node)
        runRequestMembershipFlow(network, buyer3.node)
        runRequestMembershipFlow(network, funder.node)
        runRequestMembershipFlow(network, conductor.node)

        runActivateMembershipFlow(network, bno, supplier.party)
        runActivateMembershipFlow(network, bno, buyer.party)
        runActivateMembershipFlow(network, bno, buyer2.party)
        runActivateMembershipFlow(network, bno, buyer3.party)
        runActivateMembershipFlow(network, bno, funder.party)
        runActivateMembershipFlow(network, bno, conductor.party)

    }

    @After
    fun tearDown() {
        network.stopNodes()
    }

    abstract fun configureNode(node: StartedMockNode)

    fun createNode(name: CordaX500Name, isBno: Boolean = false) =
            network.createNode(MockNodeParameters(legalName = name))
}