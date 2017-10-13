package com.tradeix.concord.specifications

import com.tradeix.concord.flows.TradeAssetIssuance
import com.tradeix.concord.models.TradeAsset
import com.tradeix.concord.states.TradeAssetState
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

    lateinit var tradeAssetState: TradeAssetState

    @Before
    fun setup() {
        net = MockNetwork()

        val nodes = net.createSomeNodes(3)

        mockBuyerNode = nodes.partyNodes[0]
        mockSupplierNode = nodes.partyNodes[1]
        mockConductorNode = nodes.partyNodes[2]

        mockBuyer = mockBuyerNode.info.chooseIdentity()
        mockSupplier = mockSupplierNode.info.chooseIdentity()
        mockConductor = mockConductorNode.info.chooseIdentity()

        nodes.partyNodes.forEach { it.registerInitiatedFlow(TradeAssetIssuance.Acceptor::class.java) }

        tradeAssetState = TradeAssetState(
                linearId = UniqueIdentifier(),
                tradeAsset = TradeAsset("MOCK_ASSET", TradeAsset.STATE_ISSUED, 100.POUNDS),
                owner = mockSupplier,
                buyer = mockBuyer,
                supplier = mockSupplier,
                conductor = mockConductor)

        net.runNetwork()
    }

    @After
    fun tearDown() {
        net.stopNodes()
    }

    @Test
    fun `Spec should match by value`() {
        val spec = PurchaseOrderStateByValueSpecification(100.POUNDS)
        val result = spec.isSatisfiedBy(tradeAssetState)
        assertTrue(result)
    }

    @Test
    fun `Spec should not match by value`() {
        val spec = PurchaseOrderStateByValueSpecification(101.POUNDS)
        val result = spec.isSatisfiedBy(tradeAssetState)
        assertFalse(result)
    }

    private class PurchaseOrderStateByValueSpecification(
            private val value: Amount<Currency>) : Specification<TradeAssetState>() {

        override fun toPredicate(): Predicate<TradeAssetState> = Predicate {
            it.tradeAsset.amount == value
        }
    }
}
