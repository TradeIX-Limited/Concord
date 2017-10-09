package com.tradeix.concord.flow

import com.tradeix.concord.state.PurchaseOrderState
import net.corda.core.contracts.Amount
import net.corda.core.contracts.TransactionVerificationException
import net.corda.node.internal.StartedNode
import net.corda.testing.node.MockNetwork
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.utilities.getOrThrow
import net.corda.finance.POUNDS
import net.corda.testing.*
import java.math.BigDecimal

class PurchaseOrderIssuanceFlowTests {
    lateinit var net: MockNetwork
    lateinit var a: StartedNode<MockNetwork.MockNode>
    lateinit var b: StartedNode<MockNetwork.MockNode>

    @Before
    fun setup() {
        setCordappPackages("com.tradeix.concord.contract")
        net = MockNetwork()
        val nodes = net.createSomeNodes(2)
        a = nodes.partyNodes[0]
        b = nodes.partyNodes[1]
        // For real nodes this happens automatically, but we have to manually register the flow for tests
        nodes.partyNodes.forEach { it.registerInitiatedFlow(PurchaseOrderIssuanceFlow.Acceptor::class.java) }
        net.runNetwork()
    }

    @After
    fun tearDown() {
        unsetCordappPackages()
        net.stopNodes()
    }

    @Test
    fun `SignedTransaction returned by the flow is signed by the initiator`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val amount: Amount<Currency> = 1.POUNDS
        val flow = PurchaseOrderIssuanceFlow.Initiator(linearId, amount, MINI_CORP)
        val future = a.services.startFlow(flow).resultFuture
        net.runNetwork()

        val signedTx = future.getOrThrow()
        signedTx.verifySignaturesExcept(b.info.chooseIdentity().owningKey)
    }

    @Test
    fun `SignedTransaction returned by the flow is signed by the acceptor`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val amount: Amount<Currency> = 1.POUNDS
        val flow = PurchaseOrderIssuanceFlow.Initiator(linearId, amount, MINI_CORP)
        val future = a.services.startFlow(flow).resultFuture
        net.runNetwork()

        val signedTx = future.getOrThrow()
        signedTx.verifySignaturesExcept(a.info.chooseIdentity().owningKey)
    }

    @Test
    fun `flow records a transaction in both parties' vaults`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val amount: Amount<Currency> = 1.POUNDS
        val flow = PurchaseOrderIssuanceFlow.Initiator(linearId, amount, MINI_CORP)
        val future = a.services.startFlow(flow).resultFuture
        net.runNetwork()
        val signedTx = future.getOrThrow()

        // We check the recorded transaction in both vaults.
        for (node in listOf(a, b)) {
            assertEquals(signedTx, node.services.validatedTransactions.getTransaction(signedTx.id))
        }
    }

    @Test
    fun `recorded transaction has no inputs and a single output, the input IOU`() {
        val linearId = UniqueIdentifier(id = UUID.fromString("00000000-0000-4000-0000-000000000000"))
        val amount: Amount<Currency> = 1.POUNDS
        val flow = PurchaseOrderIssuanceFlow.Initiator(linearId, amount, MINI_CORP)
        val future = a.services.startFlow(flow).resultFuture
        net.runNetwork()
        val signedTx = future.getOrThrow()

        // We check the recorded transaction in both vaults.
        for (node in listOf(a, b)) {
            val recordedTx = node.services.validatedTransactions.getTransaction(signedTx.id)
            val txOutputs = recordedTx!!.tx.outputs
            assert(txOutputs.size == 1)

            val recordedState = txOutputs[0].data as PurchaseOrderState
            assertEquals(recordedState.purchaseOrder.amount, amount)
            assertEquals(recordedState.buyer, a.info.chooseIdentity())
            assertEquals(recordedState.supplier, b.info.chooseIdentity())
        }
    }
}