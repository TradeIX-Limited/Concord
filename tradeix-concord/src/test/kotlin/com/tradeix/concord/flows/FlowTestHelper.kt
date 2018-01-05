package com.tradeix.concord.flows

import com.tradeix.concord.flows.tradeasset.TradeAssetAmendment
import com.tradeix.concord.flows.tradeasset.TradeAssetCancellation
import com.tradeix.concord.flows.tradeasset.TradeAssetIssuance
import com.tradeix.concord.flows.tradeasset.TradeAssetOwnership
import com.tradeix.concord.flowmodels.tradeasset.TradeAssetAmendmentFlowModel
import com.tradeix.concord.flowmodels.tradeasset.TradeAssetCancellationFlowModel
import com.tradeix.concord.flowmodels.tradeasset.TradeAssetIssuanceFlowModel
import com.tradeix.concord.flowmodels.tradeasset.TradeAssetOwnershipFlowModel
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
}