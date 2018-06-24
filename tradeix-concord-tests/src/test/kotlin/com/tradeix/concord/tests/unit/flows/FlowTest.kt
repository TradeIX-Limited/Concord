package com.tradeix.concord.tests.unit.flows

import com.tradeix.concord.cordapp.funder.flows.InvoiceEligibilityIssuanceInitiatorFlow
import com.tradeix.concord.cordapp.supplier.flows.InvoiceAmendmentInitiatorFlow
import com.tradeix.concord.cordapp.supplier.flows.InvoiceCancellationInitiatorFlow
import com.tradeix.concord.cordapp.supplier.flows.InvoiceIssuanceInitiatorFlow
import com.tradeix.concord.cordapp.supplier.flows.InvoiceOwnershipChangeInitiatorFlow
import com.tradeix.concord.shared.messages.CancellationTransactionRequestMessage
import com.tradeix.concord.shared.messages.InvoiceEligibilityTransactionRequestMessage
import com.tradeix.concord.shared.messages.InvoiceTransactionRequestMessage
import com.tradeix.concord.shared.messages.OwnershipTransactionRequestMessage
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.BUYER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.BUYER_2_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.BUYER_3_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.FUNDER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.FUNDER_2_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.FUNDER_3_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.SUPPLIER_1_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.SUPPLIER_2_NAME
import com.tradeix.concord.shared.mockdata.MockCordaX500Names.SUPPLIER_3_NAME
import com.tradeix.concord.shared.mockdata.ParticipantType
import net.corda.core.transactions.SignedTransaction
import net.corda.core.utilities.getOrThrow
import net.corda.testing.node.MockNetwork
import net.corda.testing.node.StartedMockNode
import org.junit.After
import org.junit.Before

abstract class FlowTest {

    companion object {

        object InvoiceFlows {
            fun issue(
                    network: MockNetwork,
                    initiator: StartedMockNode,
                    message: InvoiceTransactionRequestMessage): SignedTransaction {
                val future = initiator.startFlow(InvoiceIssuanceInitiatorFlow(message))
                network.runNetwork()
                return future.getOrThrow()
            }

            fun amend(
                    network: MockNetwork,
                    initiator: StartedMockNode,
                    message: InvoiceTransactionRequestMessage): SignedTransaction {
                val future = initiator.startFlow(InvoiceAmendmentInitiatorFlow(message))
                network.runNetwork()
                return future.getOrThrow()
            }

            fun cancel(
                    network: MockNetwork,
                    initiator: StartedMockNode,
                    message: CancellationTransactionRequestMessage): SignedTransaction {
                val future = initiator.startFlow(InvoiceCancellationInitiatorFlow(message))
                network.runNetwork()
                return future.getOrThrow()
            }

            fun changeOwner(
                    network: MockNetwork,
                    initiator: StartedMockNode,
                    message: OwnershipTransactionRequestMessage): SignedTransaction {
                val future = initiator.startFlow(InvoiceOwnershipChangeInitiatorFlow(message))
                network.runNetwork()
                return future.getOrThrow()
            }
        }

        object InvoiceEligibilityFlows {
            fun issue(
                    network: MockNetwork,
                    initiator: StartedMockNode,
                    message: InvoiceEligibilityTransactionRequestMessage): SignedTransaction {
                val future = initiator.startFlow(InvoiceEligibilityIssuanceInitiatorFlow(message))
                network.runNetwork()
                return future.getOrThrow()
            }
        }
    }

    protected lateinit var network: MockNetwork

    protected lateinit var buyer1: MockIdentity
    protected lateinit var buyer2: MockIdentity
    protected lateinit var buyer3: MockIdentity

    protected lateinit var funder1: MockIdentity
    protected lateinit var funder2: MockIdentity
    protected lateinit var funder3: MockIdentity

    protected lateinit var supplier1: MockIdentity
    protected lateinit var supplier2: MockIdentity
    protected lateinit var supplier3: MockIdentity

    @Before
    fun setup() {
        val cordapps = listOf(
                "com.tradeix.concord.shared.domain",
                "com.tradeix.concord.shared.cordapp",
                "com.tradeix.concord.cordapp.supplier"
        )

        network = MockNetwork(cordapps)

        buyer1 = MockIdentity(network.createPartyNode(BUYER_1_NAME), ParticipantType.BUYER)
        buyer2 = MockIdentity(network.createPartyNode(BUYER_2_NAME), ParticipantType.BUYER)
        buyer3 = MockIdentity(network.createPartyNode(BUYER_3_NAME), ParticipantType.BUYER)

        funder1 = MockIdentity(network.createPartyNode(FUNDER_1_NAME), ParticipantType.FUNDER)
        funder2 = MockIdentity(network.createPartyNode(FUNDER_2_NAME), ParticipantType.FUNDER)
        funder3 = MockIdentity(network.createPartyNode(FUNDER_3_NAME), ParticipantType.FUNDER)

        supplier1 = MockIdentity(network.createPartyNode(SUPPLIER_1_NAME), ParticipantType.SUPPLIER)
        supplier2 = MockIdentity(network.createPartyNode(SUPPLIER_2_NAME), ParticipantType.SUPPLIER)
        supplier3 = MockIdentity(network.createPartyNode(SUPPLIER_3_NAME), ParticipantType.SUPPLIER)

        listOf(
                buyer1,
                buyer2,
                buyer3,

                funder1,
                funder2,
                funder3,

                supplier1,
                supplier2,
                supplier3
        ).forEach { configureNode(it.node, it.type) }

        network.runNetwork()

        initialize()
    }

    @After
    fun tearDown() {
        network.stopNodes()
    }

    protected abstract fun configureNode(node: StartedMockNode, type: ParticipantType)

    protected open fun initialize() {
    }
}