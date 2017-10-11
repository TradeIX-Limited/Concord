package com.tradeix.concord.specifications

import com.tradeix.concord.flows.PurchaseOrderIssuance
import com.tradeix.concord.models.PurchaseOrder
import com.tradeix.concord.states.PurchaseOrderState
import net.corda.core.contracts.Amount
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.Party
import net.corda.finance.POUNDS
import net.corda.node.internal.StartedNode
import net.corda.testing.chooseIdentity
import net.corda.testing.node.MockNetwork
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*
import java.util.function.Predicate
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SpecificationTests {

    lateinit var net: MockNetwork
    lateinit var mockBuyerNode: StartedNode<MockNetwork.MockNode>
    lateinit var mockSupplierNode: StartedNode<MockNetwork.MockNode>
    lateinit var mockConductorNode: StartedNode<MockNetwork.MockNode>

    lateinit var mockBuyer: Party
    lateinit var mockSupplier: Party
    lateinit var mockConductor: Party

    lateinit var purchaseOrderState: PurchaseOrderState

    @Before
    fun setup() {
        // setCordappPackages("com.tradeix.concord.contracts")

        net = MockNetwork()

        val nodes = net.createSomeNodes(3)

        mockBuyerNode = nodes.partyNodes[0]
        mockSupplierNode = nodes.partyNodes[1]
        mockConductorNode = nodes.partyNodes[2]

        mockBuyer = mockBuyerNode.info.chooseIdentity()
        mockSupplier = mockSupplierNode.info.chooseIdentity()
        mockConductor = mockConductorNode.info.chooseIdentity()

        nodes.partyNodes.forEach { it.registerInitiatedFlow(PurchaseOrderIssuance.Acceptor::class.java) }

        purchaseOrderState = PurchaseOrderState(
                linearId = UniqueIdentifier(),
                purchaseOrder = PurchaseOrder(100.POUNDS),
                owner = mockSupplier,
                buyer = mockBuyer,
                supplier = mockSupplier,
                conductor = mockConductor)

        net.runNetwork()
    }

    @After
    fun tearDown() {
        // unsetCordappPackages()
        net.stopNodes()
    }

    @Test
    fun `Spec should match by value`() {
        val spec = PurchaseOrderStateByValueSpecification(100.POUNDS)
        val result = spec.isSatisfiedBy(purchaseOrderState)
        assertTrue(result)
    }

    @Test
    fun `Spec should not match by value`() {
        val spec = PurchaseOrderStateByValueSpecification(101.POUNDS)
        val result = spec.isSatisfiedBy(purchaseOrderState)
        assertFalse(result)
    }

    private class PurchaseOrderStateByValueSpecification(
            private val value: Amount<Currency>) : Specification<PurchaseOrderState>() {

        override fun toPredicate(): Predicate<PurchaseOrderState> = Predicate {
            it.purchaseOrder.amount == value
        }
    }
}
