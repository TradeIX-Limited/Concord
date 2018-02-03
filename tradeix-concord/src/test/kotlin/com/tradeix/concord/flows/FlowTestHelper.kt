package com.tradeix.concord.flows

import com.nhaarman.mockito_kotlin.reset
import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderCancellationFlowModel
import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderAmendmentFlowModel
import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderIssuanceFlowModel
import com.tradeix.concord.flowmodels.purchaseorder.PurchaseOrderOwnershipFlowModel
import com.tradeix.concord.flows.tradeasset.TradeAssetAmendment
import com.tradeix.concord.flows.tradeasset.TradeAssetCancellation
import com.tradeix.concord.flows.tradeasset.TradeAssetIssuance
import com.tradeix.concord.flows.tradeasset.TradeAssetOwnership
import com.tradeix.concord.flowmodels.tradeasset.TradeAssetAmendmentFlowModel
import com.tradeix.concord.flowmodels.tradeasset.TradeAssetCancellationFlowModel
import com.tradeix.concord.flowmodels.tradeasset.TradeAssetIssuanceFlowModel
import com.tradeix.concord.flowmodels.tradeasset.TradeAssetOwnershipFlowModel
import com.tradeix.concord.flows.purchaseorder.PurchaseOrderAmendment
import com.tradeix.concord.flows.purchaseorder.PurchaseOrderCancellation
import com.tradeix.concord.flows.purchaseorder.PurchaseOrderIssuance
import com.tradeix.concord.flows.purchaseorder.PurchaseOrderOwnership
import net.corda.core.transactions.SignedTransaction
import net.corda.core.utilities.getOrThrow
import net.corda.node.internal.StartedNode
import net.corda.testing.node.MockNetwork

object FlowTestHelper {
    fun issueTradeAsset(
            network: MockNetwork,
            initiator: StartedNode<MockNetwork.MockNode>,
            model: TradeAssetIssuanceFlowModel): SignedTransaction {

        val future = initiator
                .services
                .startFlow(TradeAssetIssuance.InitiatorFlow(model))
                .resultFuture

        network.runNetwork()

        return future.getOrThrow()
    }

    fun amendTradeAsset(
            network: MockNetwork,
            initiator: StartedNode<MockNetwork.MockNode>,
            model: TradeAssetAmendmentFlowModel): SignedTransaction {

        val future = initiator
                .services
                .startFlow(TradeAssetAmendment.InitiatorFlow(model))
                .resultFuture

        network.runNetwork()

        return future.getOrThrow()
    }

    fun cancelTradeAsset(
            network: MockNetwork,
            initiator: StartedNode<MockNetwork.MockNode>,
            model: TradeAssetCancellationFlowModel) : SignedTransaction {

        val future = initiator
                .services
                .startFlow(TradeAssetCancellation.InitiatorFlow(model))
                .resultFuture

        network.runNetwork()

        return future.getOrThrow()
    }

    fun changeTradeAssetOwner(
            network: MockNetwork,
            initiator: StartedNode<MockNetwork.MockNode>,
            model: TradeAssetOwnershipFlowModel): SignedTransaction {

        val future = initiator
                .services
                .startFlow(TradeAssetOwnership.InitiatorFlow(model))
                .resultFuture

        network.runNetwork()

        return future.getOrThrow()
    }

    fun issuePurchaseOrder(
            network: MockNetwork,
            initiator: StartedNode<MockNetwork.MockNode>,
            model: PurchaseOrderIssuanceFlowModel): SignedTransaction {

        val future = initiator
                .services
                .startFlow(PurchaseOrderIssuance.InitiatorFlow(model))
                .resultFuture

        network.runNetwork()

        return future.getOrThrow()
    }

    fun changePurchaseOrderOwner(
            network: MockNetwork,
            initiator: StartedNode<MockNetwork.MockNode>,
            model: PurchaseOrderOwnershipFlowModel): SignedTransaction {

        val future = initiator
                .services
                .startFlow(PurchaseOrderOwnership.InitiatorFlow(model))
                .resultFuture

        network.runNetwork()

        return future.getOrThrow()
    }

    fun amendPurchaseOrder(
            network: MockNetwork,
            initiator: StartedNode<MockNetwork.MockNode>,
            model: PurchaseOrderAmendmentFlowModel) : SignedTransaction {

        val future = initiator
                .services
                .startFlow(PurchaseOrderAmendment.InitiatorFlow(model))
                .resultFuture

        network.runNetwork()

        return future.getOrThrow()
    }


fun cancelPurchaseOrder(
        network: MockNetwork,
        initiator: StartedNode<MockNetwork.MockNode>,
        model: PurchaseOrderCancellationFlowModel) : SignedTransaction {

    val future = initiator
            .services
            .startFlow(PurchaseOrderCancellation.InitiatorFlow(model))
            .resultFuture

    network.runNetwork()

    return future.getOrThrow()
}
}