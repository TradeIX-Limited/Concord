package com.tradeix.concord.flows

import com.tradeix.concord.models.TradeAsset
import com.tradeix.concord.states.TradeAssetState
import net.corda.core.contracts.StateAndRef
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.Party
import net.corda.core.node.services.queryBy
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.utilities.getOrThrow
import net.corda.finance.POUNDS
import net.corda.node.internal.StartedNode
import net.corda.testing.chooseIdentity
import net.corda.testing.node.MockNetwork
import net.corda.testing.setCordappPackages
import net.corda.testing.unsetCordappPackages
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.fail

class TradeAssetOwnershipFlowTests {
    lateinit var network: MockNetwork
    lateinit var mockBuyerNode: StartedNode<MockNetwork.MockNode>
    lateinit var mockSupplierNode: StartedNode<MockNetwork.MockNode>
    lateinit var mockConductorNode: StartedNode<MockNetwork.MockNode>
    lateinit var mockNewOwnerNode: StartedNode<MockNetwork.MockNode>
    lateinit var mockBuyer: Party
    lateinit var mockSupplier: Party
    lateinit var mockConductor: Party
    lateinit var mockNewOwner: Party

    @Before
    fun setup() {
        setCordappPackages("com.tradeix.concord.contracts")
        network = MockNetwork()
        val nodes = network.createSomeNodes(4)
        mockBuyerNode = nodes.partyNodes[0]
        mockSupplierNode = nodes.partyNodes[1]
        mockConductorNode = nodes.partyNodes[2]
        mockNewOwnerNode = nodes.partyNodes[3]
        mockBuyer = mockBuyerNode.info.chooseIdentity()
        mockSupplier = mockSupplierNode.info.chooseIdentity()
        mockConductor = mockConductorNode.info.chooseIdentity()
        mockNewOwner = mockNewOwnerNode.info.chooseIdentity()
        // For real nodes this happens automatically, but we have to manually register the flow for tests.
        nodes.partyNodes.forEach {
            it.registerInitiatedFlow(TradeAssetOwnership.Acceptor::class.java)
            it.registerInitiatedFlow(TradeAssetIssuance.Acceptor::class.java)
        }
    }

    @After
    fun tearDown() {
        unsetCordappPackages()
        network.stopNodes()
    }

    @Test
    fun `issuance flow should work correctly`() {
        val tradeAsset = TradeAsset(assetId = "MockAsset",
                status = TradeAsset.STATE_ISSUED,
                amount = 1.POUNDS)
        assertNotNull(issueTradeAsset(tradeAsset))
    }

   @Ignore
    fun `transfer of ownership flow has a single input and a single output - a trade asset`() {
       val tradeAsset = TradeAsset(assetId = "MockAsset",
               status = TradeAsset.STATE_ISSUED,
               amount = 1.POUNDS)
       val stateAndRef = issueTradeAsset(tradeAsset)
       val flow = TradeAssetOwnership.BuyerFlow(stateAndRef!!, mockNewOwner)
       val future = mockBuyerNode.services.startFlow(flow).resultFuture
       val signedTx = future.getOrThrow()

/*       for (node in listOf(mockNewOwnerNode, mockSupplierNode)) {
           val recordedTx = node.services.validatedTransactions.getTransaction(signedTx.id) ?: fail()
           assert(recordedTx.inputs.size == 1)
           assert(recordedTx.tx.outputs.size == 1)
       }*/
    }

    private fun issueTradeAsset(tradeAsset: TradeAsset) : StateAndRef<TradeAssetState>? {
        var inputStateAndResult: StateAndRef<TradeAssetState>? = null
        val linearId = UniqueIdentifier(id = UUID.randomUUID())
        val tradeAssetIssuanceState = TradeAssetState(linearId = linearId,
                owner = mockSupplier,
                buyer = mockBuyer,
                conductor = mockConductor,
                supplier = mockSupplier,
                tradeAsset = tradeAsset)

        val issuanceFlow = TradeAssetIssuance.BuyerFlow(
                linearId = tradeAssetIssuanceState.linearId,
                supplier = tradeAssetIssuanceState.supplier,
                conductor = tradeAssetIssuanceState.conductor,
                buyer = tradeAssetIssuanceState.buyer,
                amount = tradeAssetIssuanceState.tradeAsset.amount,
                assetId =  tradeAssetIssuanceState.tradeAsset.assetId
        )
        val issuanceFlowFuture = mockBuyerNode.services.startFlow(issuanceFlow).resultFuture
        network.runNetwork()
        val stx = issuanceFlowFuture.getOrThrow()
        // Check issuance transaction is stored in the storage service.
        val bTx = mockSupplierNode.services.validatedTransactions.getTransaction(stx.id)
        assertEquals(bTx, stx)
        // Check tradeasset state is stored in the vault.
        mockSupplierNode.database.transaction {
            // Using a custom criteria directly referencing schema entity attribute.
            val criteria = QueryCriteria.LinearStateQueryCriteria(linearId = listOf(tradeAssetIssuanceState.linearId))
            val vault = mockSupplierNode.services.vaultService.queryBy<TradeAssetState>(criteria)
            val inputStateAndRef = vault.states.single()
            val bTradeAssetStateThruQuery = inputStateAndRef.state.data
            assertEquals(bTradeAssetStateThruQuery.linearId, tradeAssetIssuanceState.linearId)
            inputStateAndResult = inputStateAndRef
        }
        return inputStateAndResult
    }
}
