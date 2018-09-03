package com.tradeix.concord.flows

import com.tradeix.concord.flowmodels.invoice.InvoiceIPUFlowModel
import com.tradeix.concord.flowmodels.invoice.InvoiceIssuanceFlowModel
import com.tradeix.concord.flowmodels.invoice.InvoiceOwnershipFlowModel
import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderAmendmentFlowModel
import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderCancellationFlowModel
import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderIssuanceFlowModel
import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderOwnershipFlowModel
import com.tradeix.concord.flows.invoice.InvoiceIPU
import com.tradeix.concord.flows.invoice.InvoiceIssuance
import com.tradeix.concord.flows.invoice.InvoiceOwnership
import com.tradeix.concord.flows.purchaseorder.PurchaseOrderAmendment
import com.tradeix.concord.flows.purchaseorder.PurchaseOrderCancellation
import com.tradeix.concord.flows.purchaseorder.PurchaseOrderIssuance
import com.tradeix.concord.flows.purchaseorder.PurchaseOrderOwnership
import net.corda.businessnetworks.membership.bno.ActivateMembershipFlow
import net.corda.businessnetworks.membership.bno.service.DatabaseService
import net.corda.businessnetworks.membership.member.RequestMembershipFlow
import net.corda.businessnetworks.membership.states.MembershipMetadata
import net.corda.core.identity.Party
import net.corda.core.transactions.SignedTransaction
import net.corda.core.utilities.getOrThrow
import net.corda.testing.node.MockNetwork
import net.corda.testing.node.StartedMockNode

object FlowTestHelper {
    fun issuePurchaseOrder(
            network: MockNetwork,
            initiator: StartedMockNode,
            model: PurchaseOrderIssuanceFlowModel): SignedTransaction {

        val future = initiator
                .startFlow(PurchaseOrderIssuance.InitiatorFlow(model))

        network.runNetwork()

        return future.getOrThrow()
    }

    fun changePurchaseOrderOwner(
            network: MockNetwork,
            initiator: StartedMockNode,
            model: PurchaseOrderOwnershipFlowModel): SignedTransaction {

        val future = initiator
                .startFlow(PurchaseOrderOwnership.InitiatorFlow(model))

        network.runNetwork()

        return future.getOrThrow()
    }

    fun amendPurchaseOrder(
            network: MockNetwork,
            initiator: StartedMockNode,
            model: PurchaseOrderAmendmentFlowModel): SignedTransaction {

        val future = initiator
                .startFlow(PurchaseOrderAmendment.InitiatorFlow(model))

        network.runNetwork()

        return future.getOrThrow()
    }


    fun cancelPurchaseOrder(
            network: MockNetwork,
            initiator: StartedMockNode,
            model: PurchaseOrderCancellationFlowModel): SignedTransaction {

        val future = initiator
                .startFlow(PurchaseOrderCancellation.InitiatorFlow(model))

        network.runNetwork()

        return future.getOrThrow()
    }

    fun issueInvoice(
            network: MockNetwork,
            initiator: StartedMockNode,
            model: InvoiceIssuanceFlowModel): SignedTransaction {

        val future = initiator
                .startFlow(InvoiceIssuance.InitiatorFlow(model))

        network.runNetwork()

        return future.getOrThrow()
    }

    fun changeInvoiceOwner(
            network: MockNetwork,
            initiator: StartedMockNode,
            model: InvoiceOwnershipFlowModel): SignedTransaction {

        val future = initiator
                .startFlow(InvoiceOwnership.InitiatorFlow(model))

        network.runNetwork()

        return future.getOrThrow()
    }

    fun setInvoiceIPU(
            network: MockNetwork,
            initiator: StartedMockNode,
            model: InvoiceIPUFlowModel): SignedTransaction {


        val future = initiator
                .startFlow(InvoiceIPU.InitiatorFlow(model))

        network.runNetwork()

        return future.getOrThrow()
    }

    fun runRequestMembershipFlow(network: MockNetwork,
                                 initiator: StartedMockNode,
                                 membershipMetadata: MembershipMetadata = MembershipMetadata(role = "DEFAULT")): SignedTransaction {
        val future = initiator.startFlow(RequestMembershipFlow())
        network.runNetwork()
        return future.getOrThrow()
    }

    fun runActivateMembershipFlow(network: MockNetwork,
                                  initiator: StartedMockNode,
                                  party: Party): SignedTransaction {
        val membership = getMembership(initiator, party)
        val future = initiator.startFlow(ActivateMembershipFlow(membership))
        network.runNetwork()
        return future.getOrThrow()
    }

    fun getMembership(node: StartedMockNode, party: Party) = node.transaction {
        val dbService = node.services.cordaService(DatabaseService::class.java)
        dbService.getMembership(party)!!
    }
}